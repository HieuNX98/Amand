package com.amand.service;

import com.amand.dto.UserDto;

import java.util.Map;

public interface IUserService {

    UserDto save(UserDto userDto);

    Map<String, String> validate(UserDto userDto, boolean isAdmin);
}
