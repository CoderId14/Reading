package com.example.reading.service.impl;

import com.example.reading.api.input.SignUpRequest;
import com.example.reading.api.output.UserSummary;
import com.example.reading.dto.RoleDTO;
import com.example.reading.dto.UserDTO;
import com.example.reading.entity.RoleEntity;
import com.example.reading.entity.UserEntity;
import com.example.reading.jwt.UserPrincipal;
import com.example.reading.repository.RoleRepository;
import com.example.reading.repository.UserRepository;
import com.example.reading.repository.converter.RoleConverter;
import com.example.reading.repository.converter.UserConverter;
import com.example.reading.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

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


    private final static String  USER_NOT_FOUND_MSG =
            "user with email %s not found";
    @Override
    public UserSummary getCurrentUser(UserPrincipal currentUser) {
        return new UserSummary(currentUser.getId(), currentUser.getUsername(), currentUser.getFullName()
                );
    }
    @Override
    public UserDTO save(UserDTO userDTO) {
        log.info("Saving new user {} to the database",userDTO.getUsername());
        UserEntity userEntity = userConverter.toEntity(userDTO);
        userEntity.setRoles(userDTO.getRoles());
        userRepository.save(userEntity);
        return userConverter.toDTO(userEntity);
    }

    @Override
    public UserDTO findByUsername(String username) {
        return userConverter.toDTO(userRepository.findByUsername(username).get());
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
        Optional<UserEntity> user = userRepository.findByUsername(userName);
        Optional<RoleEntity> role = roleRepository.findByName(roleName);
        if(user.isPresent()){
            user.get().getRoles().add(role.get());
        }
        else{
            log.error("User not found in the database");
            throw new UsernameNotFoundException("User not found in the database");
        }
    }

    @Override
    public void addRoleToUser(String userName, Set<String> roleName) {
        log.info("Adding role {} to user {}",roleName,userName);
        Optional<UserEntity> user = userRepository.findByUsername(userName);
        Set<RoleEntity> roles = new HashSet<>();
        roleName.forEach(role ->{
            roles.add(roleRepository.findByName(role).get());
        });
        if(user.isPresent()){
            roles.forEach(role ->{
                user.get().getRoles().add(role);
            });
        }
        else{
            log.error("User not found in the database");
            throw new UsernameNotFoundException("User not found in the database");
        }
    }

    @Override
    public UserDTO getUser(String userName) {
        Optional<UserEntity> user = userRepository.findByUsername(userName);
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
    public boolean existsByUsername(String userName) {
        return userRepository.existsByUsername(userName);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public UserDTO registerUser(SignUpRequest signUpRequest) {
        return null;
    }




    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail)
            throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsernameOrEmail(usernameOrEmail,usernameOrEmail)
                .orElseThrow(() -> new UsernameNotFoundException(
                        String.format("User not found with this username or email: %s", usernameOrEmail)));;

        return UserPrincipal.create(user);
    }
}
