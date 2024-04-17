package com.example.todospringptoject.controller;

import com.example.todospringptoject.exception.TodoNotFoundException;
import com.example.todospringptoject.exception.UserAlreadyExistException;
import com.example.todospringptoject.exception.UserNotFoundException;
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
    public ResponseEntity createTodo(@RequestBody TodoEntity todo,
                             @RequestParam Long userId) {
        try {
            return ResponseEntity.ok(todoService.createTodo(todo,userId));
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @PutMapping
    public ResponseEntity completedTodo(@RequestParam Long todoId) {
        try {
            return ResponseEntity.ok(todoService.completeTodo(todoId));
        } catch (TodoNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }
}
