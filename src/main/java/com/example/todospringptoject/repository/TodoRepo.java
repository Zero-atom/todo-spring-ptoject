package com.example.todospringptoject.repository;

import com.example.todospringptoject.model.dto.TodoProjection;
import com.example.todospringptoject.model.entity.TodoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface TodoRepo extends CrudRepository<TodoEntity, Long>, JpaSpecificationExecutor<TodoEntity> {

    //пагинация - разделение результата (большого объема данных) на страницы  (части)
    Page<TodoEntity> findAll(Pageable pageable);

    //projections
    Page<TodoProjection> findAllProjectedBy(Pageable pageable);
}
