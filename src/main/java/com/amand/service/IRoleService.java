package com.amand.service;

import com.amand.dto.RoleDto;

import java.util.List;

public interface IRoleService {
    List<RoleDto> findAll();
}
