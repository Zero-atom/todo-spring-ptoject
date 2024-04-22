package com.example.todospringptoject.model.dto;

import com.example.todospringptoject.exception.UsersNotFoundException;
import com.example.todospringptoject.model.entity.UserEntity;
import lombok.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
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

}
