package com.example.todospringptoject.specification;

import com.example.todospringptoject.model.dto.Todo;
import com.example.todospringptoject.model.entity.TodoEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class TodoSpecification {

    public Specification<TodoEntity> byCompleted(boolean completed) {
        return (root, query, cb) -> cb.equal(root.get("completed"), completed);
    }
}
