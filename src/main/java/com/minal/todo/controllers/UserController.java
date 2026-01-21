package com.minal.todo.controllers;

import com.minal.todo.dto.UserRequestDTO;
import com.minal.todo.dto.UserResponseDTO;
import com.minal.todo.dto.UserUpdateDTO;
import com.minal.todo.exceptions.UserAlreadyExistsException;
import com.minal.todo.exceptions.UserNotFoundException;
import com.minal.todo.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAll(){
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/{userName}")
    public ResponseEntity<?> getByUserName(@PathVariable String userName){
        try {
            UserResponseDTO user = userService.getUserByUserName(userName);
            return ResponseEntity.ok(user);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserRequestDTO userDto){
        try {
            UserResponseDTO user = userService.createUser(userDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(user);
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", e.getMessage()));
        }
    }

    @PutMapping("/{userName}")
    public ResponseEntity<?> updateUser(@PathVariable String userName, @RequestBody UserUpdateDTO userDto){
        try {
            UserResponseDTO user = userService.updateUser(userName, userDto);
            return ResponseEntity.ok(user);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/{userName}")
    public ResponseEntity<?> deleteTodo(@PathVariable String userName){
        try {
            userService.deleteUser(userName);
            return ResponseEntity.noContent().build();
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }
}
