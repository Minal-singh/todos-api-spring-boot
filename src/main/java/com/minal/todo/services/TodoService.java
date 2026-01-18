package com.minal.todo.services;

import com.minal.todo.models.TodoModel;
import com.minal.todo.models.UserModel;
import com.minal.todo.repositories.TodoRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TodoService {

    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository){
        this.todoRepository = todoRepository;
    }

    public Iterable<TodoModel> getAllTodos(UserModel user){
        return todoRepository.findAllByUser(user);
    }

    public Optional<TodoModel> getTodoById(Long id, UserModel user){
        return todoRepository.findByIdAndUser(id, user);
    }

    public TodoModel createTodo(TodoModel newTodo, UserModel user){
        newTodo.setUser(user);
        return todoRepository.save(newTodo);
    }

    public TodoModel updateTodo(TodoModel oldTodo, TodoModel newTodo){
        if (newTodo.getTitle() != null && !newTodo.getTitle().isEmpty()) {
            oldTodo.setTitle(newTodo.getTitle());
        }
        if (newTodo.getCompleted() != null) {
            oldTodo.setCompleted(newTodo.getCompleted());
        }
        return todoRepository.save(oldTodo);
    }

    public void deleteTodo(Long id){
        todoRepository.deleteById(id);
    }
}
