package com.example.todospringptoject.repository;

import com.example.todospringptoject.model.entity.TodoEntity;
import org.springframework.data.repository.CrudRepository;

public interface TodoRepo extends CrudRepository<TodoEntity, Long> {
}
