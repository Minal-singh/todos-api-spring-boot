package com.minal.todo.controllers;

import com.minal.todo.dto.UserResponseDTO;
import com.minal.todo.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@Tag(name = "Admin APIs")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/get-all")
    @Operation(summary = "Get all users")
    public ResponseEntity<List<UserResponseDTO>> getAll() {
        return ResponseEntity.ok(userService.getAllUsers());
    }
}
