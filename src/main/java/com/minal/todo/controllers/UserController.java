package com.minal.todo.controllers;

import com.minal.todo.dto.UserRequestDTO;
import com.minal.todo.dto.UserResponseDTO;
import com.minal.todo.dto.UserUpdateDTO;
import com.minal.todo.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        UserResponseDTO user = userService.getUserByUserName(userName);
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserRequestDTO userDto){
        UserResponseDTO user = userService.createUser(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PutMapping("/{userName}")
    public ResponseEntity<?> updateUser(@PathVariable String userName, @RequestBody UserUpdateDTO userDto){
        UserResponseDTO user = userService.updateUser(userName, userDto);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{userName}")
    public ResponseEntity<?> deleteTodo(@PathVariable String userName){
        userService.deleteUser(userName);
        return ResponseEntity.noContent().build();
    }
}
