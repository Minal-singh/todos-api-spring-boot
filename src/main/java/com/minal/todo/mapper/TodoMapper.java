package com.minal.todo.mapper;

import com.minal.todo.dto.TodoRequestDTO;
import com.minal.todo.dto.TodoResponseDTO;
import com.minal.todo.models.TodoModel;
import org.springframework.stereotype.Component;

@Component
public class TodoMapper {
    public TodoResponseDTO toDto(TodoModel todo) {
        return new TodoResponseDTO(
                todo.getId(),
                todo.getTitle(),
                todo.getCompleted()
        );
    }

    public TodoModel toEntity(TodoRequestDTO todoDTO) {
        TodoModel todo = new TodoModel();
        todo.setTitle(todoDTO.getTitle());
        todo.setCompleted(todoDTO.getCompleted());
        return todo;
    }
}
