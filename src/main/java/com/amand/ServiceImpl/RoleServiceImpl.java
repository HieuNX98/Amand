package com.amand.ServiceImpl;

import com.amand.entity.RoleEntity;
import com.amand.repository.RoleRepository;
import com.amand.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<RoleEntity> findAll() {
        List<RoleEntity> roleEntities = roleRepository.findAll();
        return roleEntities;
    }
}
