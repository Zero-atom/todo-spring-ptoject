package com.example.todospringptoject.controller;

import com.example.todospringptoject.model.dto.Todo;
import com.example.todospringptoject.model.entity.TodoEntity;
import com.example.todospringptoject.service.TodoService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;

@RestController
@RequestMapping("/todos")
@AllArgsConstructor
public class TodoController {

    private TodoService todoService;

    @PostMapping
    public Todo createTodo(@RequestBody TodoEntity todo, @RequestParam Long userId) {
        return todoService.createTodo(todo,userId);
    }

    @PutMapping
    public Todo completedTodo(@RequestParam Long todoId) {
        return todoService.completeTodo(todoId);
    }

    //пагинация

    //реализация 1
    @GetMapping
    public Page<Todo> getAllTodos(@PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        return todoService.getAllTodos(pageable);
    }

    //реализация 2
//    @GetMapping
//    public Page<Todo> getAllTodos(@RequestParam(defaultValue = "0") int page,
//                                  @RequestParam(defaultValue = "10") int size)
//    {
//        Pageable pageable = PageRequest.of(page, size);
//        return todoService.getAllTodos(pageable);
//    }
}
