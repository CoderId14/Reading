package com.example.reading.api;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.reading.api.input.RoleToUserForm;
import com.example.reading.api.output.UserOutPut;
import com.example.reading.dto.RoleDTO;
import com.example.reading.dto.UserDTO;
import com.example.reading.entity.RoleEntity;
import com.example.reading.jwt.JwtUtils;
import com.example.reading.api.input.LoginRequest;
import com.example.reading.service.impl.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    @Value("${reading.app.jwtSecret}")
    private  String jwtSecret;
    @Value("${reading.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Validated @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassWord()));

        String jwt = jwtUtils.generateJwtToken(authentication);
        String reJwt = jwtUtils.generateRefreshJwtToken(authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User userDetails = (User) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new UserOutPut(jwt,
                reJwt,
                "Bearer",
                userDetails.getUsername(),
                roles));

    }


    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getUser(){
        return ResponseEntity.ok().body(userService.getUsers());
    }
    @PostMapping("/user/save")
    public ResponseEntity<UserDTO> saveUser(@RequestBody UserDTO userDTO){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/save").toUriString());
        return ResponseEntity.created(uri).body(userService.save(userDTO));
    }
    @PostMapping("/role/save")
    public ResponseEntity<RoleDTO> saveRole(@RequestBody RoleDTO roleDTO){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/role/save").toUriString());

        return ResponseEntity.created(uri).body(userService.saveRole(roleDTO));
    }
    @PostMapping("/role/addtouser")
    public void addRoleToUser(@RequestBody RoleToUserForm form){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/role/save").toUriString());
        userService.addRoleToUser(form.getUserName(), form.getRole());
    }
    @PostMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if(authorizationHeader !=null && authorizationHeader.startsWith("Bearer ")){
            try {
                String refresh_token = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256(jwtSecret.getBytes(StandardCharsets.UTF_8));
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);
                String username = decodedJWT.getSubject();
                UserDTO user = userService.getUser(username);
                String access_token = JWT.create()
                        .withSubject(user.getUserName())
                        .withExpiresAt(new Date(System.currentTimeMillis()+ jwtExpirationMs))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles",user.getRoles().stream().map(RoleEntity::getName).collect(Collectors.toList()))
                        .sign(algorithm);
                Map<String,String> tokens = new HashMap<>();
                tokens.put("access_token", access_token);
                tokens.put("refresh_token",refresh_token);
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(),tokens);

            }
            catch (Exception exception){

                response.setHeader("Error",exception.getMessage());
                response.setStatus(FORBIDDEN.value());
//                  response.sendError(FORBIDDEN.value());
                Map<String,String> error = new HashMap<>();
                error.put("error_message",exception.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(),error);
            }


        }
        else {
            throw new RuntimeException("Refresh token is missing");
        }
    }

}