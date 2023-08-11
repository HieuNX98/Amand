package com.amand.service;

import com.amand.dto.UserDto;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface IUserService {

    UserDto save(UserDto userDto);

    Map<String, String> validate(UserDto userDto, boolean isAdmin);

    List<UserDto> findAll(Pageable pageable);

    int getTotalItem();
}
