package com.example.todospringptoject.mapper;

import com.example.todospringptoject.model.dto.User;
import com.example.todospringptoject.model.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface UserMapper {

    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "username", source = "entity.username")
    @Mapping(target = "todos", source = "entity.todos")
    User userEntityToUser(UserEntity entity);

    List<User> userEntityListToUserList(List<UserEntity> entities);


}
