package com.example.todospringptoject.service;

import com.example.todospringptoject.exception.TodoNotFoundException;
import com.example.todospringptoject.exception.UserNotFoundException;
import com.example.todospringptoject.mapper.TodoMapper;
import com.example.todospringptoject.model.dto.Todo;
import com.example.todospringptoject.model.projection.TodoProjection;
import com.example.todospringptoject.model.entity.TodoEntity;
import com.example.todospringptoject.model.entity.UserEntity;
import com.example.todospringptoject.repository.TodoRepo;
import com.example.todospringptoject.repository.UserRepo;
import com.example.todospringptoject.specification.TodoSpecification;

import lombok.AllArgsConstructor;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
//import jakarta.transaction.Transactional;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class TodoService {

    private TodoRepo todoRepo;
    private UserRepo userRepo;
    private TodoMapper todoMapper;

    private final TodoSpecification todoSpecification;

    @Transactional
    public Todo createTodo(TodoEntity todo, Long userId)  {
        log.info("Метод createTodo вызван с параметрами: todo={}, userId={}", todo, userId);

        Optional<UserEntity> optionalUser = userRepo.findById(userId);
        if (optionalUser.isPresent()) {
            todo.setUser(optionalUser.get());

            Todo createdTodo = todoMapper.todoEntityToTodo(todoRepo.save(todo));
            log.info("todo успешно создано: {}", createdTodo);

            return createdTodo;
        } else {
            throw new UserNotFoundException("Пользователь не найден");
        }
    }

    @Transactional
    public Todo completeTodo( Long todoId)  {
        log.info("Метод completeTodo вызван с параметром: todoId={}", todoId);
        Optional<TodoEntity> optionalTodo = todoRepo.findById(todoId);
        if (optionalTodo.isPresent()) {
            TodoEntity todo = optionalTodo.get();
            todo.setCompleted(!todo.getCompleted());

            Todo completedTodo = todoMapper.todoEntityToTodo(todoRepo.save(todo));
            log.info("todo изменило статус на: {}", !todo.getCompleted());

            return completedTodo;
        } else {
            throw new TodoNotFoundException("Задача не найдена");
        }
    }

    //пагинация
//    public Page<Todo> getAllTodos(Pageable pageable) {
//        log.info("Метод getAllTodos вызван с параметрами пагинации: Размер страницы: {}, Номер страницы: {}, Сортировка: {}",
//                pageable.getPageSize(), pageable.getPageNumber(), pageable.getSort());
//
//        Page<TodoEntity> todoEntities = todoRepo.findAll(pageable);
//        Page<Todo> todos = todoEntities.map(todoMapper::todoEntityToTodo); // Преобразование сущностей в DTO
//
//        if (!todos.isEmpty()) {
//            log.info("Успешно найдено {} задач", todos.getTotalElements());
//            return todos;
//        } else {
//            throw new TodoNotFoundException("Задачи не найдены");
//        }
//    }

    //пагинация и спецификация
    public Page<Todo> getAllTodosWithPaginationAndFilter(Pageable pageable, boolean completed) {
        log.info("Метод getAllTodosWithPaginationAndFilter вызван с параметрами пагинации: Размер страницы: {}, Номер страницы: {}, Сортировка: {}, Фильтр: {}",
                pageable.getPageSize(), pageable.getPageNumber(), pageable.getSort(), completed);

        Specification<TodoEntity> spec = todoSpecification.byCompleted(completed);
        Page<TodoEntity> todoEntities = todoRepo.findAll(spec, pageable);
        Page<Todo> todos = todoEntities.map(todoMapper::todoEntityToTodo);

        if (!todos.isEmpty()) {
            log.info("Успешно найдено {} задач", todos.getTotalElements());
            return todos;
        } else {
            throw new TodoNotFoundException("Задачи не найдены");
        }
    }

    //projections
    @Transactional
    public Page<TodoProjection> getAllTodosProjection(Pageable pageable, boolean completed) {
        log.info("Метод getAllTodosProjection вызван с параметрами пагинации: Размер страницы: {}, Номер страницы: {}, Сортировка: {}, Фильтр: {}",
                pageable.getPageSize(), pageable.getPageNumber(), pageable.getSort(), completed);

        Specification<TodoProjection> spec = todoSpecification.byCompletedProjection(completed);

        Page<TodoProjection> todosProjections = todoRepo.getTodoEntityBy(pageable);

        if (!todosProjections.isEmpty()) {
            log.info("Успешно найдено {} задач", todosProjections.getTotalElements());
            return todosProjections;
        } else {
            throw new TodoNotFoundException("Задачи не найдены");
        }

    }

    //native запрос и jpql запрос
    public Page<Todo> findCompletedTodosNative(Pageable pageable) {
        log.info("Метод findCompletedTodosNative вызван с параметрами пагинации: Размер страницы: {}, Номер страницы: {}, Сортировка: {}",
                pageable.getPageSize(), pageable.getPageNumber(), pageable.getSort());

        //Page<TodoEntity> todoEntities = todoRepo.findCompletedTodosNative(pageable); //native
        Page<TodoEntity> todoEntities = todoRepo.findCompletedTodosJPQL(pageable); //jpql

        Page<Todo> todos = todoEntities.map(todoMapper::todoEntityToTodo);

        if (!todos.isEmpty()) {
            log.info("Успешно найдено {} задач", todos.getTotalElements());
            return todos;
        } else {
            throw new TodoNotFoundException("Задачи не найдены");
        }
    }
}
