package com.minal.todo.services;

import com.minal.todo.dto.TodoRequestDTO;
import com.minal.todo.dto.TodoResponseDTO;
import com.minal.todo.dto.TodoUpdateDTO;
import com.minal.todo.exceptions.TodoNotFoundException;
import com.minal.todo.exceptions.UserNotFoundException;
import com.minal.todo.mapper.TodoMapper;
import com.minal.todo.models.TodoModel;
import com.minal.todo.models.UserModel;
import com.minal.todo.repositories.TodoRepository;
import com.minal.todo.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class TodoService {

    private final TodoRepository todoRepository;
    private final TodoMapper todoMapper;
    private final UserRepository userRepository;

    public TodoService(
            TodoRepository todoRepository,
            TodoMapper todoMapper,
            UserRepository userRepository
    ) {
        this.todoRepository = todoRepository;
        this.todoMapper = todoMapper;
        this.userRepository = userRepository;
    }

    public TodoResponseDTO getTodoById(Long id, String userName) {
        UserModel user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        TodoModel todo = todoRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new TodoNotFoundException("Todo not found"));

        return todoMapper.toDto(todo);
    }

    public TodoResponseDTO createTodo(TodoRequestDTO newTodo, String userName) {
        UserModel user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        TodoModel todo = todoMapper.toEntity(newTodo);
        todo.setUser(user);
        TodoModel saved = todoRepository.save(todo);
        return todoMapper.toDto(saved);
    }

    public TodoResponseDTO updateTodo(Long id, String userName, TodoUpdateDTO newTodo) {
        UserModel user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        TodoModel oldTodo = todoRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new TodoNotFoundException("Todo not found"));

        if (newTodo.getTitle() != null && !newTodo.getTitle().isEmpty()) {
            oldTodo.setTitle(newTodo.getTitle());
        }
        if (newTodo.getCompleted() != null) {
            oldTodo.setCompleted(newTodo.getCompleted());
        }
        TodoModel saved = todoRepository.save(oldTodo);
        return todoMapper.toDto(saved);
    }

    public void deleteTodo(Long id, String userName) {
        UserModel user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        TodoModel oldTodo = todoRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new TodoNotFoundException("Todo not found"));

        todoRepository.deleteById(oldTodo.getId());
    }
}
