package com.example.todospringptoject.model.dto;

import com.example.todospringptoject.model.entity.UserEntity;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

//entity сущности связанные с БД
//dto(model) для обмена между сервером и клиентом
//здесь не указываем password
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    private Long id;
    private String username;
    private List<Todo> todos;

    public static User toModel(UserEntity entity) {//конвертор сущности в модель
        User model = new User();
        model.setId(entity.getId());
        model.setUsername(entity.getUsername());
        model.setTodos(entity.getTodos().stream().map(Todo::toModel).collect(Collectors.toList()));
        return model;
    }

}
