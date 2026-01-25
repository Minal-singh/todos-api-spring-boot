package com.minal.todo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TodoRequestDTO {

    @NonNull
    private String title;
    private Boolean completed;
}
