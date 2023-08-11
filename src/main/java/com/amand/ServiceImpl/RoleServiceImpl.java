package com.amand.ServiceImpl;

import com.amand.converter.RoleConverter;
import com.amand.dto.RoleDto;
import com.amand.entity.RoleEntity;
import com.amand.repository.RoleRepository;
import com.amand.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl implements IRoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RoleConverter roleConverter;

    @Override
    public List<RoleDto> findAll() {
        List<RoleDto> roleDtos = new ArrayList<>();
        List<RoleEntity> roleEntities = roleRepository.findAll();
         for (RoleEntity roleEntity : roleEntities) {
             RoleDto roleDto = roleConverter.toDto(roleEntity);
             roleDtos.add(roleDto);
         }
        return roleDtos;
    }
}
