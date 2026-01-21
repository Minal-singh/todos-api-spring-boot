package com.minal.todo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TodoResponseDTO {
    private Long id;
    private String title;
    private Boolean completed;
}
