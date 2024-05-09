package com.example.todospringptoject.mapper;

import com.example.todospringptoject.model.dto.Todo;
import com.example.todospringptoject.model.entity.TodoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface TodoMapper {

    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "title", source = "entity.title")
    @Mapping(target = "completed", source = "entity.completed")
    @Mapping(target = "description", source = "entity.description")
    Todo todoEntityToTodo(TodoEntity entity);

    List<Todo> todoEntityListToTodoList(List<TodoEntity> entities);
}
