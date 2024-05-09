package com.example.todospringptoject.repository;

import com.example.todospringptoject.model.projection.TodoProjection;
import com.example.todospringptoject.model.entity.TodoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface TodoRepo extends CrudRepository<TodoEntity, Long>, JpaSpecificationExecutor<TodoEntity> {

    //Page<TodoEntity> findAll(Pageable pageable);

    Page<TodoProjection> getTodoEntityBy(Pageable pageable);

    //native запрос
    @Query(value = "SELECT * FROM todo WHERE completed = true", nativeQuery = true)
    Page<TodoEntity> findCompletedTodosNative(Pageable pageable);

    //jpql запрос
    @Query("SELECT t FROM TodoEntity t WHERE t.completed = true")
    Page<TodoEntity> findCompletedTodosJPQL(Pageable pageable);
}
