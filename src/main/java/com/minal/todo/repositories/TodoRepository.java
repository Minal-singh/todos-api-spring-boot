package com.minal.todo.repositories;

import com.minal.todo.models.TodoModel;
import com.minal.todo.models.UserModel;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TodoRepository extends CrudRepository<TodoModel, Long> {
    Optional<TodoModel> findByIdAndUser(Long id, UserModel user);
}
