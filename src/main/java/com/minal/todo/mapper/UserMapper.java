package com.minal.todo.mapper;

import com.minal.todo.dto.TodoResponseDTO;
import com.minal.todo.dto.UserRequestDTO;
import com.minal.todo.dto.UserResponseDTO;
import com.minal.todo.models.TodoModel;
import com.minal.todo.models.UserModel;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UserMapper {
    public UserModel toEntity(UserRequestDTO userDto) {
        UserModel user = new UserModel();
        user.setUserName(userDto.getUserName());
        user.setPassword(userDto.getPassword());
        return user;
    }

    public UserResponseDTO toDto(UserModel user) {
        return new UserResponseDTO(
                user.getId(),
                user.getUserName(),
                user.getTodos().stream().map(this::todoToDto).collect(Collectors.toList())
        );
    }

    public TodoResponseDTO todoToDto(TodoModel todo) {
        return new TodoResponseDTO(
                todo.getId(),
                todo.getTitle(),
                todo.getCompleted()
        );
    }
}
