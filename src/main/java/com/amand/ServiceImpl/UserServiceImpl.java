package com.amand.ServiceImpl;

import com.amand.converter.RoleConverter;
import com.amand.converter.UserConverter;
import com.amand.dto.RoleDto;
import com.amand.dto.UserDto;
import com.amand.entity.RoleEntity;
import com.amand.entity.UserEntity;
import com.amand.form.UserForm;
import com.amand.repository.RoleRepository;
import com.amand.repository.UserRepository;
import com.amand.service.IUserService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserConverter userConverter;

    @Autowired
    private RoleConverter roleConverter;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    @Transactional
    public UserDto save(UserForm userForm) {
        List<RoleEntity> roles = new ArrayList<>();
        UserEntity userEntity = userConverter.toEntity(userForm);
        if (CollectionUtils.isEmpty(userEntity.getRoles())) {
            RoleEntity roleEntity = roleRepository.findOneByCode("ROLE_USER");
            roles.add(roleEntity);
            userEntity.setRoles(roles);
        } else {
            RoleEntity roleEntity = roleRepository.findOneByCode(userForm.getRoleCode());
            roles.add(roleEntity);
            userEntity.setRoles(roles);
        }
        userEntity.setStatus(1);
        userEntity = userRepository.save(userEntity);
        return userConverter.toDto(userEntity);
    }


    public Map<String, String> validate(UserForm userForm, boolean isAdmin) {
        Map<String, String> result = new HashMap<>();
        if (Strings.isBlank(userForm.getFullName())) {
            result.put("messageFullName", "Bạn không được để trống thông tin họ và tên ");
        }

        if (isAdmin && Strings.isBlank(userForm.getRoleCode())) {
            result.put("messageRole", "Bạn không được để trống thông tin vai tro");
        }

        if (Strings.isNotBlank(userForm.getUserName())) {
            if (userForm.getUserName().length() < 5 || userForm.getUserName().length() > 20) {
                result.put("messageUserName", "Tên người dùng phải có độ dài từ 6 đến 20 ký tự VD: Amand123");
            } else if (StringUtils.hasLength(userRepository.findOneByUserName(userForm.getUserName()))) {
                result.put("messageUserName", "Tên người dùng đã được sử dụng");
            }
        } else {
            result.put("messageUserName", "Bạn không được để trống thông tin Username");
        }


        if (Strings.isNotBlank(userForm.getPassword())) {
            if (userForm.getPassword().length() < 6 || userForm.getPassword().length() > 10) {
                result.put("messagePassword", "Mật khẩu phải có độ dài từ 7 đến 10 ký tự");
            } else if (!Pattern.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)[A-Za-z\\d]+$", userForm.getPassword())) {
                result.put("messagePassword", "Ký tự đầu tiên của mật khẩu phải viết hoa và các ký tự tiếp theo phải có ít nhất một ký tự viết thường và 1 kí tự số " +
                        "và một số. VD: Amand123");
            } else if (!userForm.getPassword().equals(userForm.getRepeatPassword())) {
                result.put("messagePassword", "Mật khẩu không giống nhau");
            }
        } else {
            result.put("messagePassword", "Bạn không được để trống thông tin Password");
        }

        if (Strings.isNotBlank(userForm.getPhone())) {
            if (!userForm.getPhone().matches("^(03|05|07|08|09)\\d{8}$")) {
                result.put("messagePhone", "Số điện thoại bắt buộc phải có 10 số và chỉ sử dụng các đầu số nhà mạng tại Việt Nam");
            }
        } else {
            result.put("messagePhone", "Bạn không được để trống thông tin số điện thoại");
        }

        if(Strings.isBlank(userForm.getEmail())){
            result.put("messageEmail", "Bạn không được để trống thông tin email");
        }

        return result;
    }

    @Override
    public List<UserDto> findAll(Pageable pageable) {
        List<UserDto> userDtos = new ArrayList<>();
        List<UserEntity> userEntities = userRepository.findAll(pageable).getContent();
        for (UserEntity userEntity : userEntities) {
            List<RoleDto> roleDtos = new ArrayList<>();
            for (RoleEntity roleEntity : userEntity.getRoles()) {
                RoleDto roleDto = roleConverter.toDto(roleEntity);
                roleDtos.add(roleDto);
            }
            UserDto userDto = userConverter.toDto(userEntity);
            userDto.setRoleDtos(roleDtos);
            userDtos.add(userDto);
        }
        return userDtos;
    }

    @Override
    public int getTotalItem() {
        return (int) userRepository.count();
    }

}

