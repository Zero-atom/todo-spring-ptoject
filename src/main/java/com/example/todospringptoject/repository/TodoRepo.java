package com.example.todospringptoject.repository;

import com.example.todospringptoject.model.entity.TodoEntity;
import com.example.todospringptoject.model.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface TodoRepo extends CrudRepository<TodoEntity, Long> {

    //пагинация - разделение результата (большого объема данных) на страницы  (части)
    Page<TodoEntity> findAll(Pageable pageable);
}
