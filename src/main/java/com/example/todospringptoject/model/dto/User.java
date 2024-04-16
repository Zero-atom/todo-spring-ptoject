package com.example.todospringptoject.model.dto;

import com.example.todospringptoject.model.entity.UserEntity;

//entity сущности связанные с БД
//model для обмена между сервером и клиентом
//здесь не указываем password
public class User {
    private Long id;
    private String username;

    public static User toModel(UserEntity entity) {//конвертор сущности в модель
        User model = new User();
        model.setId(entity.getId());
        model.setUsername(entity.getUsername());
        return model;
    }

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
