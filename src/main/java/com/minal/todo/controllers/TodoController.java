package com.minal.todo.controllers;

import com.minal.todo.dto.TodoRequestDTO;
import com.minal.todo.dto.TodoResponseDTO;
import com.minal.todo.dto.TodoUpdateDTO;
import com.minal.todo.dto.UserResponseDTO;
import com.minal.todo.services.TodoService;
import com.minal.todo.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/todos")
public class TodoController {

    private final TodoService todoService;
    private final UserService userService;

    public TodoController(TodoService todoService, UserService userService) {
        this.todoService = todoService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<?> getAll(@AuthenticationPrincipal UserDetails userDetails) {
        UserResponseDTO user = userService.getUserByUserName(userDetails.getUsername());
        return ResponseEntity.ok(user.getTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        TodoResponseDTO todo = todoService.getTodoById(id, userDetails.getUsername());
        return ResponseEntity.ok(todo);
    }

    @PostMapping
    public ResponseEntity<?> createTodo(@AuthenticationPrincipal UserDetails userDetails, @RequestBody TodoRequestDTO newTodo) {
        TodoResponseDTO todo = todoService.createTodo(newTodo, userDetails.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED).body(todo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTodo(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long id, @RequestBody TodoUpdateDTO updatedTodo) {
        TodoResponseDTO todo = todoService.updateTodo(id, userDetails.getUsername(), updatedTodo);
        return ResponseEntity.ok(todo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTodo(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        todoService.deleteTodo(id, userDetails.getUsername());
        return ResponseEntity.noContent().build();
    }
}
