package com.example.todospringptoject.controller;

import com.example.todospringptoject.exception.TodoNotFoundException;
import com.example.todospringptoject.exception.UserAlreadyExistException;
import com.example.todospringptoject.exception.UserNotFoundException;
import com.example.todospringptoject.model.dto.Todo;
import com.example.todospringptoject.model.entity.TodoEntity;
import com.example.todospringptoject.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/todos")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @PostMapping
    public Todo createTodo(@RequestBody TodoEntity todo, @RequestParam Long userId) {
        return todoService.createTodo(todo,userId);
    }

    @PutMapping
    public Todo completedTodo(@RequestParam Long todoId) {
        return todoService.completeTodo(todoId);
    }
}
