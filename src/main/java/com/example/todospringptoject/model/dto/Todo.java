package com.example.todospringptoject.model.dto;

import com.example.todospringptoject.model.entity.TodoEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Todo {

    private Long id;
    private String title;
    private Boolean completed;
    private String description;

    public static Todo toModel(TodoEntity entity) {//конвертор сущности в модель
        Todo model = new Todo();
        model.setId(entity.getId());
        model.setTitle(entity.getTitle());
        model.setCompleted(entity.getCompleted());
        model.setDescription(entity.getDescription());
        return model;
    }

}
