package com.example.todospringptoject.controller;

import com.example.todospringptoject.model.dto.Todo;
import com.example.todospringptoject.model.dto.TodoProjection;
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
        return todoService.createTodo(todo, userId);
    }

    @PutMapping
    public Todo completedTodo(@RequestParam Long todoId) {
        return todoService.completeTodo(todoId);
    }

    //реализация пагинации 1 с Pageable pageable
    @GetMapping
    public Page<Todo> getAllTodos(@PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable)
    {
        return todoService.getAllTodos(pageable); // запрос с пагинацией
    }

    //реализация токо пагинации 2 с int page, int size
//    @GetMapping
//    public Page<Todo> getAllTodos(@RequestParam(defaultValue = "0") int page,
//                                  @RequestParam(defaultValue = "10") int size)
//    {
//        Pageable pageable = PageRequest.of(page, size);
//        return todoService.getAllTodos(pageable);
//    }


//    // запрос с пагинацией,спецификацией
//    @GetMapping("/search")
//    public Page<Todo> getAllTodos(@PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable,
//                                            @RequestParam(required = false) Boolean completed
//    ) {
//        return todoService.getAllTodosWithPaginationAndFilter(pageable,completed);
//
//        //return todoService.getAllTodosProjection(pageable, completed); // запрос с пагинацией, спецификацией, projections
//    }

    // запрос с пагинацией, спецификацией, projections
    @GetMapping("/search")
    public Page<TodoProjection> getAllTodos(@PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable,
                                  @RequestParam(required = false) Boolean completed
    ) {
        return todoService.getAllTodosProjection(pageable, completed); // запрос с пагинацией, спецификацией, projections
    }

}