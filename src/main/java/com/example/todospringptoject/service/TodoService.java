package com.example.todospringptoject.service;

import com.example.todospringptoject.exception.TodoNotFoundException;
import com.example.todospringptoject.exception.UserNotFoundException;
import com.example.todospringptoject.model.dto.Todo;
import com.example.todospringptoject.model.dto.User;
import com.example.todospringptoject.model.entity.TodoEntity;
import com.example.todospringptoject.model.entity.UserEntity;
import com.example.todospringptoject.repository.TodoRepo;
import com.example.todospringptoject.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TodoService {

    @Autowired
    private TodoRepo todoRepo;

    @Autowired
    private UserRepo userRepo;

    public Todo createTodo(TodoEntity todo, Long userId) throws UserNotFoundException {
        Optional<UserEntity> optionalUser = userRepo.findById(userId);
        if (optionalUser.isPresent()) {
            todo.setUser(optionalUser.get());
            return Todo.toModel(todoRepo.save(todo));
        } else {
            throw new UserNotFoundException("Пользователь не найден");
        }
    }

    public Todo completeTodo( Long todoId) throws TodoNotFoundException {
        Optional<TodoEntity> optionalTodo = todoRepo.findById(todoId);
        if (optionalTodo.isPresent()) {
            TodoEntity todo = optionalTodo.get();
            todo.setCompleted(!todo.getCompleted());
            return Todo.toModel(todoRepo.save(todo));
        } else {
            throw new TodoNotFoundException("Задача не найдена");
        }
    }

}
