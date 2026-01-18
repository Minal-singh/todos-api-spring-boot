package com.minal.todo.controllers;

import com.minal.todo.models.TodoModel;
import com.minal.todo.models.UserModel;
import com.minal.todo.services.TodoService;
import com.minal.todo.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

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
    public ResponseEntity<?> getAll(@PathVariable String userName){
        Optional<UserModel> user = userService.getUserByUserName(userName);
        if (user.isPresent())
            return new ResponseEntity<>(todoService.getAllTodos(user.get()), HttpStatus.OK);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "User not found"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id, @PathVariable String userName){
        Optional<UserModel> user = userService.getUserByUserName(userName);
        if (user.isPresent()) {
            Optional<TodoModel> todo = todoService.getTodoById(id, user.get());
            if (todo.isPresent())
                return new ResponseEntity<>(todo.get(), HttpStatus.OK);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "User not found"));
    }

    @PostMapping
    public ResponseEntity<?> createTodo(@PathVariable String userName,@RequestBody TodoModel newTodo){
        Optional<UserModel> user = userService.getUserByUserName(userName);
        if (user.isPresent()) {
            try {
                TodoModel todo = todoService.createTodo(newTodo, user.get());
                return new ResponseEntity<>(todo, HttpStatus.CREATED);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "User not found"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTodo(@PathVariable String userName, @PathVariable Long id, @RequestBody TodoModel updatedTodo){
        Optional<UserModel> user = userService.getUserByUserName(userName);
        if (user.isPresent()) {
            Optional<TodoModel> oldTodo = todoService.getTodoById(id, user.get());
            if (oldTodo.isPresent()){
                try {
                    TodoModel todo = todoService.updateTodo(oldTodo.get(), updatedTodo);
                    return new ResponseEntity<>(todo, HttpStatus.CREATED);
                } catch (Exception e) {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Todo not found"));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "User not found"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTodo(@PathVariable Long id, @PathVariable String userName){
        Optional<UserModel> user = userService.getUserByUserName(userName);
        if (user.isPresent()) {
            todoService.deleteTodo(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "User not found"));
    }
}
