package com.example.reading.service.impl;

import com.example.reading.dto.RoleDTO;
import com.example.reading.entity.RoleEntity;
import com.example.reading.repository.RoleRepository;
import com.example.reading.repository.converter.RoleConverter;
import com.example.reading.repository.converter.UserConverter;
import com.example.reading.dto.UserDTO;
import com.example.reading.entity.UserEntity;
import com.example.reading.entity.supports.UserRole;
import com.example.reading.repository.UserRepository;
import com.example.reading.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class UserService implements IUserService,UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserConverter userConverter;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private RoleConverter roleConverter;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private final static String  USER_NOT_FOUND_MSG =
            "user with email %s not found";

    @Override
    public UserDTO save(UserDTO userDTO) {
        log.info("Saving new user {} to the database",userDTO.getUserName());
        userDTO.setPassWord(passwordEncoder.encode(userDTO.getPassWord()));
        UserEntity userEntity = userConverter.toEntity(userDTO);
        userRepository.save(userEntity);
        return userConverter.toDTO(userEntity);
    }

    @Override
    public UserDTO findByUserName(String userName) {
        return null;
    }


    @Override
    public RoleDTO saveRole(RoleDTO role) {
        log.info("Saving new role {} to the database",role.getName());
        RoleEntity roleEntity = roleConverter.toEntity(role);
        roleRepository.save(roleEntity);
        return role;
    }

    @Override
    public void addRoleToUser(String userName, String roleName) {
        log.info("Adding role {} to user {}",roleName,userName);
        Optional<UserEntity> user = userRepository.findByUserName(userName);
        RoleEntity role = roleRepository.findByName(roleName);
        if(user.isPresent()){
            user.get().getRoles().add(role);
        }
        else{
            log.error("User not found in the database");
            throw new UsernameNotFoundException("User not found in the database");
        }
    }

    @Override
    public UserDTO getUser(String userName) {
        Optional<UserEntity> user = userRepository.findByUserName(userName);
        if (user.isPresent()){
            log.info("User found in the database: {}",userName);
            return userConverter.toDTO(user.get());
        }
        else{
            log.error("User not found in the database");
            throw new UsernameNotFoundException("User not found in the database");
        }
    }

    @Override
    public List<UserDTO> getUsers() {
        log.info("Fetching user");
        List<UserDTO> result = new ArrayList<>();
        List<UserEntity> users = userRepository.findAll();
        for (UserEntity user:users
             ) {
            result.add(userConverter.toDTO(user));
        }
        return result;

    }


    @Override
    public UserDetails loadUserByUsername(String userName)
            throws UsernameNotFoundException {
        Optional<UserEntity> user = userRepository.findByUserName(userName);
        if (user.isPresent()){
            log.info("User found in the database: {}",userName);
        }
        else{
            log.error("User not found in the database");
            throw new UsernameNotFoundException("User not found in the database");

        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.get().getRoles().forEach(role ->
                authorities.add(new SimpleGrantedAuthority(role.getName())));
        return new org.springframework.security.core.userdetails.User(user.get().getUserName(),user.get().getPassWord(),authorities);
    }
}
