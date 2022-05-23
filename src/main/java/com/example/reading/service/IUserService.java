package com.example.reading.service;

import com.example.reading.api.input.SignUpRequest;
import com.example.reading.dto.RoleDTO;
import com.example.reading.dto.UserDTO;
import com.example.reading.entity.RoleEntity;
import com.example.reading.entity.UserEntity;
import org.springframework.security.core.userdetails.User;

import javax.management.relation.RoleNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface IUserService {
    UserDTO save(UserDTO userDTO);
    UserDTO findByUserName(String userName);
    RoleDTO saveRole(RoleDTO role);
    void addRoleToUser(String userName,String roleName);
    void addRoleToUser(String userName, Set<String> roleName);
    UserDTO getUser(String userName);
    List<UserDTO> getUsers();
    boolean existsByUserName(String userName);
    boolean existsByEmail(String email);
    UserDTO registerUser(SignUpRequest signUpRequest);


}
