package com.example.todospringptoject.model.dto;

import com.example.todospringptoject.model.entity.TodoEntity;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Todo {

    private Long id;
    private String title;
    private Boolean completed;
    private String description;

}
