package com.minal.todo.controllers;

import com.minal.todo.dto.UserResponseDTO;
import com.minal.todo.dto.UserUpdateDTO;
import com.minal.todo.services.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/users")
@Tag(name = "User APIs")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping
    public ResponseEntity<?> getByUserName(@AuthenticationPrincipal UserDetails userDetails) {
        UserResponseDTO user = userService.getUserByUserName(userDetails.getUsername());
        return ResponseEntity.ok(user);
    }

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody UserUpdateDTO userDto, @AuthenticationPrincipal UserDetails userDetails) {
        UserResponseDTO user = userService.updateUser(userDetails.getUsername(), userDto);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteTodo(@AuthenticationPrincipal UserDetails userDetails) {
        userService.deleteUser(userDetails.getUsername());
        return ResponseEntity.noContent().build();
    }
}
