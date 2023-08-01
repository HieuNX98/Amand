package com.amand.ServiceImpl;

import com.amand.converter.UserConverter;
import com.amand.dto.UserDto;
import com.amand.entity.RoleEntity;
import com.amand.entity.UserEntity;
import com.amand.repository.RoleRepository;
import com.amand.repository.UserRepository;
import com.amand.service.IUserService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserDto save(UserDto userDto) {
        List<RoleEntity> roles = new ArrayList<>();
        UserEntity userEntity = userConverter.toEntity(userDto);
        RoleEntity roleEntity = roleRepository.findOneByCode("ROLE_USER");
        roles.add(roleEntity);
        userEntity.setRoles(roles);
        userEntity.setStatus(1);
        userEntity = userRepository.save(userEntity);
        return userConverter.toDto(userEntity);
    }


    public Map<String, String> validate(UserDto userDto) {
        Map<String, String> result = new HashMap<>();
        if (Strings.isBlank(userDto.getFullName())) {
            result.put("messageFullName", "Bạn không được để trống thông tin họ và tên ");
        }

        if (Strings.isNotBlank(userDto.getUserName())) {
            if (userDto.getUserName().length() < 5 || userDto.getUserName().length() > 20) {
                result.put("messageUserName", "Tên người dùng phải có độ dài từ 6 đến 20 ký tự VD: Amand123");
            } else if (StringUtils.hasLength(userRepository.findOneByUserName(userDto.getUserName()))) {
                result.put("messageUserName", "Tên người dùng đã được sử dụng");
            }
        } else {
            result.put("messageUserName", "Bạn không được để trống thông tin Username");
        }


        if (Strings.isNotBlank(userDto.getPassword())) {
            if (userDto.getPassword().length() < 6 || userDto.getPassword().length() > 10) {
                result.put("messagePassword", "Mật khẩu phải có độ dài từ 7 đến 10 ký tự");
            } else if (!Pattern.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)[A-Za-z\\d]+$", userDto.getPassword())) {
                result.put("messagePassword", "Ký tự đầu tiên của mật khẩu phải viết hoa và các ký tự tiếp theo phải có ít nhất một ký tự viết thường và 1 kí tự số " +
                        "và một số. VD: Amand123");
            } else if (!userDto.getPassword().equals(userDto.getRepeatPassword())) {
                result.put("messagePassword", "Mật khẩu không giống nhau");
            }
        } else {
            result.put("messagePassword", "Bạn không được để trống thông tin Password");
        }

        if (Strings.isNotBlank(userDto.getPhone())) {
            if (!userDto.getPhone().matches("^(03|05|07|08|09)\\d{8}$")) {
                result.put("messagePhone", "Số điện thoại bắt buộc phải có 10 số và chỉ sử dụng các đầu số nhà mạng tại Việt Nam");
            }
        } else {
            result.put("messagePhone", "Bạn không được để trống thông tin số điện thoại");
        }

        if(Strings.isBlank(userDto.getEmail())){
            result.put("messageEmail", "Bạn không được để trống thông tin email");
        }

        return result;
    }
}

