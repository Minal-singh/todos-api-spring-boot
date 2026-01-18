package com.minal.todo.controllers;

import com.minal.todo.models.UserModel;
import com.minal.todo.services.UserService;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<Iterable<UserModel>> getAll(){
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/{userName}")
    public ResponseEntity<UserModel> getByUserName(@PathVariable String userName){
        Optional<UserModel> user = userService.getUserByUserName(userName);
        if (user.isPresent())
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserModel newUser, HttpMethod httpMethod){
        try {
            userService.createUser(newUser);
            return new ResponseEntity<>(newUser, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e));
        }
    }

    @PutMapping("/{userName}")
    public ResponseEntity<UserModel> updateUser(@PathVariable String userName, @RequestBody UserModel updateUser){
        Optional<UserModel> user = userService.updateUser(userName, updateUser);
        if (user.isPresent())
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{userName}")
    public ResponseEntity<?> deleteTodo(@PathVariable String userName){
        userService.deleteUser(userName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
