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

    public Specification<TodoEntity> byTitle(String title) {
        if (title == null ) {
            return (root, query, cb) -> cb.isNull(root.get("title"));
        }
        return (root, query, cb) -> cb.equal(root.get("title"), title);
    }

    public Specification<TodoEntity> byDescription(String description) {
        return (root, query, cb) -> cb.equal(root.get("description"), description);
    }

//    private Long id;
//    private String title;
//    private Boolean completed;
//    private String description;
}
