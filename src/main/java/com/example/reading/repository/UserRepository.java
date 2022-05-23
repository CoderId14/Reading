package com.example.reading.repository;

import com.example.reading.entity.RoleEntity;
import com.example.reading.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity,Long> {
    Optional<UserEntity> findByUserName(String userName);
    boolean existsByUserName(String userName);
    boolean existsByEmail(String email);

}
