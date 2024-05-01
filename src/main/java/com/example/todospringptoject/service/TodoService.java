package com.example.todospringptoject.service;

import com.example.todospringptoject.exception.TodoNotFoundException;
import com.example.todospringptoject.exception.UserNotFoundException;
import com.example.todospringptoject.mapper.TodoMapper;
import com.example.todospringptoject.model.dto.Todo;
import com.example.todospringptoject.model.entity.TodoEntity;
import com.example.todospringptoject.model.entity.UserEntity;
import com.example.todospringptoject.repository.TodoRepo;
import com.example.todospringptoject.repository.UserRepo;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class TodoService {

    private TodoRepo todoRepo;
    private UserRepo userRepo;
    private TodoMapper todoMapper;

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

}
