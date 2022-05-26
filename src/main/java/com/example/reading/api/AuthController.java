package com.example.reading.api;


import com.example.reading.api.input.LoginRequest;
import com.example.reading.api.input.SignUpRequest;
import com.example.reading.api.output.JwtAuthenticationResponse;
import com.example.reading.api.output.MessageResponse;
import com.example.reading.api.output.ResponseObject;
import com.example.reading.dto.UserDTO;
import com.example.reading.entity.RoleEntity;
import com.example.reading.jwt.JwtTokenProvider;
import com.example.reading.repository.RoleRepository;
import com.example.reading.repository.UserRepository;
import com.example.reading.service.impl.RoleService;
import com.example.reading.service.impl.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.management.relation.RoleNotFoundException;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;


    private final PasswordEncoder passwordEncoder;


    private final UserService userService;


    private final RoleService roleService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponse> authenticateUser(@Validated @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsernameOrEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtTokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Validated @RequestBody SignUpRequest signUpRequest) throws RoleNotFoundException {
        if(userService.existsByUserName(signUpRequest.getUserName())){
            ResponseEntity.badRequest()
                    .body(new MessageResponse("Error: Username already exists"));
        }
        if(userService.existsByEmail(signUpRequest.getEmail())){
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Error: Email already exists"));
        }
        UserDTO user = new UserDTO(signUpRequest.getUserName(),signUpRequest.getPassWord(),
                null,true,signUpRequest.getEmail(),null);
        Set<String> strRoles = signUpRequest.getRoles();
        Set<RoleEntity> roles = new HashSet<>();
        if (strRoles == null) {
            RoleEntity userRole = roleService.findByName("ROLE_USER")
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "ROLE_ADMIN":
                        RoleEntity adminRole = roleService.findByName("ROLE_ADMIN")
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);
                        break;
                    default:
                        RoleEntity userRole = roleService.findByName("ROLE_USER")
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }
        user.setRoles(roles);
        userService.save(user);
        return ResponseEntity.ok(new MessageResponse("User register successfully!"));
    }

}
