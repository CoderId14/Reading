package com.example.reading.service;

import com.example.reading.dto.RoleDTO;
import com.example.reading.dto.UserDTO;
import com.example.reading.entity.RoleEntity;
import com.example.reading.entity.UserEntity;

import java.util.List;

public interface IUserService {
    UserDTO save(UserDTO userDTO);
    UserDTO findByUserName(String userName);
    RoleDTO saveRole(RoleDTO role);
    void addRoleToUser(String userName,String roleName);
    UserDTO getUser(String userName);
    List<UserDTO> getUsers();


}
