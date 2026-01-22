package com.minal.todo.controllers;

import com.minal.todo.dto.TodoRequestDTO;
import com.minal.todo.dto.TodoResponseDTO;
import com.minal.todo.dto.TodoUpdateDTO;
import com.minal.todo.dto.UserResponseDTO;
import com.minal.todo.services.TodoService;
import com.minal.todo.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


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
        UserResponseDTO user = userService.getUserByUserName(userName);
        return ResponseEntity.ok(user.getTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id, @PathVariable String userName){
        TodoResponseDTO todo = todoService.getTodoById(id, userName);
        return ResponseEntity.ok(todo);
    }

    @PostMapping
    public ResponseEntity<?> createTodo(@PathVariable String userName, @RequestBody TodoRequestDTO newTodo){
        TodoResponseDTO todo = todoService.createTodo(newTodo, userName);
        return ResponseEntity.status(HttpStatus.CREATED).body(todo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTodo(@PathVariable String userName, @PathVariable Long id, @RequestBody TodoUpdateDTO updatedTodo){
        TodoResponseDTO todo = todoService.updateTodo(id, userName, updatedTodo);
        return ResponseEntity.ok(todo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTodo(@PathVariable Long id, @PathVariable String userName){
        todoService.deleteTodo(id, userName);
        return ResponseEntity.noContent().build();
    }
}
