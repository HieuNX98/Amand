package com.amand.ServiceImpl;

import com.amand.constant.SystemConstant;
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

import java.util.*;
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
        UserEntity userEntity;
        if (userForm.getId() != null) {
            UserEntity oldUserEntity = userRepository.findOneById(userForm.getId());
            userEntity = userConverter.toEntity(oldUserEntity, userForm);
            extractRoleToUserEntity(userForm, userEntity);
        } else {
            userEntity = userConverter.toEntity(userForm);
            extractRoleToUserEntity(userForm, userEntity);
            userEntity.setStatus(SystemConstant.ACTIVE_STATUS);

        }
        userEntity = userRepository.save(userEntity);
        return userConverter.toDto(userEntity);
    }


    public Map<String, String> validate(UserForm userForm, boolean isAdmin) {
        Map<String, String> result = new HashMap<>();
        if (Strings.isBlank(userForm.getFullName())) {
            result.put("messageFullName", "Bạn không được để trống thông tin họ và tên ");
        }

        if (isAdmin && CollectionUtils.isEmpty(userForm.getRoleCodes())) {
            result.put("messageRole", "Bạn không được để trống thông tin vai trò");
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

        String errorPassword = getErrorPassword(userForm.getPassword());
        if (Strings.isNotBlank(errorPassword)) {
            result.put("messagePassword", errorPassword);
        }

        if (!userForm.getPassword().equals(userForm.getRepeatPassword())) {
            result.put("MessageRepeatPassword", "Mật khẩu không giống nhau");
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
    public Map<String, String> validateUpdateAccount(UserForm userForm) {
        Map<String, String> result = new HashMap<>();
        if (Strings.isBlank(userForm.getFullName())) {
            result.put("messageFullName", "Bạn không được để trống thông tin họ và tên ");
        }

        if (CollectionUtils.isEmpty(userForm.getRoleCodes())) {
            result.put("messageRole", "Bạn không được để trống thông tin vai trò");
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
    public List<UserDto> findAllByRoleCodeAndStatus(String roleCode, Pageable pageable, Integer status) {
        List<UserDto> userDtos = new ArrayList<>();
        List<UserEntity> userEntities = userRepository.findAllByRoleCodeAndStatus(roleCode, status, pageable);
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
    public int getTotalItem(Integer status, String roleCode) {
        return userRepository.countByStatusAndRoleCode(status, roleCode);
    }

    @Override
    public UserDto findOneById(Integer id) {
        UserEntity userEntity = userRepository.findOneById(id);
        return userConverter.toDto(userEntity);
    }

    @Override
    @Transactional
    public void updateStatus(List<Integer> ids, int status) {
        userRepository.updateStatusByIds(status, ids);
    }

    @Override
    public void deleteUser(List<Integer> ids) {
        List<UserEntity> userEntities = userRepository.findAllByIds(ids);
        for (UserEntity user : userEntities) {
            user.setRoles(Collections.emptyList());
        }
        userRepository.deleteAllById(ids);
    }

    private void extractRoleToUserEntity(UserForm userForm, UserEntity userEntity) {
        List<RoleEntity> roleEntities = new ArrayList<>();
        if (CollectionUtils.isEmpty(userForm.getRoleCodes())) {
            RoleEntity roleEntity = roleRepository.findOneByCode("ROLE_USER");
            roleEntities.add(roleEntity);
        } else {
            roleEntities = roleRepository.findAllByCode(userForm.getRoleCodes());
        }
        userEntity.setRoles(roleEntities);
    }

    private String getErrorPassword(String password) {
        if (Strings.isBlank(password)) {
            return "Bạn không được để trống thông tin Password";
        }
        if (password.length() < 6 || password.length() > 10) {
            return "Mật khẩu phải có độ dài từ 7 đến 10 ký tự";
        }
        if (!Pattern.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)[A-Za-z\\d]+$", password)) {
            return "Ký tự đầu tiên của mật khẩu phải viết hoa và các ký tự tiếp theo phải có ít nhất một ký tự viết thường và" +
                    " một số. VD: Amand123";
        }

        return "";
    }

}

