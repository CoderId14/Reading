package com.example.reading.service.impl;

import com.example.reading.entity.RoleEntity;
import com.example.reading.repository.RoleRepository;
import com.example.reading.service.IRoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.relation.RoleNotFoundException;
import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class RoleService implements IRoleService {

    private final RoleRepository roleRepository;

    @Override
    public Optional<RoleEntity> findByName(String name) {
        Optional<RoleEntity> roles = roleRepository.findByName(name);
        if(roles.isPresent()){
            log.info("Roles found in the database: {}",name);
            return roles;
        }
        else {
            log.error("Role not found in the database");
            throw new RuntimeException("Role not found in the database");
        }
    }
}
