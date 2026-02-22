package com.minal.todo.controllers;

import com.minal.todo.dto.UserRequestDTO;
import com.minal.todo.dto.UserResponseDTO;
import com.minal.todo.services.UserDetailsServiceImpl;
import com.minal.todo.services.UserService;
import com.minal.todo.utils.JWTUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Public APIs", description = "For unauthenticated users")
public class PublicEndpointsController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceImpl userDetailsService;
    private final UserService userService;
    private final JWTUtils jwtUtils;

    public PublicEndpointsController(AuthenticationManager authenticationManager, UserDetailsServiceImpl userDetailsService, UserService userService, JWTUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.userService = userService;
        this.jwtUtils = jwtUtils;
    }

    @GetMapping("/health-check")
    public String check() {
        return "Ok. Working fine!";
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody UserRequestDTO userDto) {
        UserResponseDTO user = userService.createUser(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserRequestDTO userDto) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userDto.getUserName(), userDto.getPassword())
            );
            UserDetails userDetails = userDetailsService.loadUserByUsername(userDto.getUserName());
            String jwt = jwtUtils.generateToken(userDetails.getUsername());
            return ResponseEntity.ok(jwt);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect UserName or Password");
        }
    }
}
