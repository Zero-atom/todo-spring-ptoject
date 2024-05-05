package com.example.todospringptoject.specification;

import com.example.todospringptoject.model.entity.UserEntity;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecification {
    public static Specification<UserEntity> usersWithTodosStartingWith(String usernamePrefix, String titlePrefix) {
        return (root, query, cb) ->
                cb.and(
                        cb.like(root.get("username"), usernamePrefix + "%"),
                        cb.like(root.join("todos").get("title"), titlePrefix + "%")
                );
    }
}
