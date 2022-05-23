package com.example.reading.service;

import com.example.reading.entity.RoleEntity;

import javax.management.relation.RoleNotFoundException;
import java.util.Optional;

public interface IRoleService {
    Optional<RoleEntity> findByName(String name) throws RoleNotFoundException;
}
