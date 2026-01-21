package com.minal.todo.controllers;

import com.minal.todo.dto.TodoRequestDTO;
import com.minal.todo.dto.TodoResponseDTO;
import com.minal.todo.dto.TodoUpdateDTO;
import com.minal.todo.dto.UserResponseDTO;
import com.minal.todo.exceptions.TodoNotFoundException;
import com.minal.todo.exceptions.UserNotFoundException;
import com.minal.todo.services.TodoService;
import com.minal.todo.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/{userName}/todos")
public class TodoController {

    private final TodoService todoService;
    private final UserService userService;

    public TodoController(TodoService todoService, UserService userService) {
        this.todoService = todoService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<?> getAll(@PathVariable String userName) {
        try {
            UserResponseDTO user = userService.getUserByUserName(userName);
            return ResponseEntity.ok(user.getTodos());
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id, @PathVariable String userName){
        try {
            TodoResponseDTO todo = todoService.getTodoById(id, userName);
            return ResponseEntity.ok(todo);
        } catch (UserNotFoundException | TodoNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<?> createTodo(@PathVariable String userName, @RequestBody TodoRequestDTO newTodo){
        try {
            TodoResponseDTO todo = todoService.createTodo(newTodo, userName);
            return ResponseEntity.status(HttpStatus.CREATED).body(todo);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTodo(@PathVariable String userName, @PathVariable Long id, @RequestBody TodoUpdateDTO updatedTodo){
        try {
            TodoResponseDTO todo = todoService.updateTodo(id, userName, updatedTodo);
            return ResponseEntity.ok(todo);
        } catch (UserNotFoundException | TodoNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTodo(@PathVariable Long id, @PathVariable String userName){
        try {
            todoService.deleteTodo(id, userName);
            return ResponseEntity.noContent().build();
        } catch (UserNotFoundException | TodoNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }
}
