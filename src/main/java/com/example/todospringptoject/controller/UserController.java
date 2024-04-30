package com.example.todospringptoject.controller;

import com.example.todospringptoject.exception.UsersNotFoundException;
import com.example.todospringptoject.model.dto.User;
import com.example.todospringptoject.model.entity.UserEntity;
import com.example.todospringptoject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//контроллер работает с запросами и ответами  - третий слой абстракции
@RestController
@RequestMapping("/users")
public class UserController { // alt + enter - создать тест

    @Autowired
    private UserService userService;//внедрение репо

    @PostMapping
    @ResponseBody
    public User registration(@RequestBody UserEntity user) {
        return userService.registration(user);
    }

    @GetMapping("/{userId}")
    @ResponseBody
    public User getOneUser(@PathVariable Long userId) {
        return userService.getOne(userId);
    }

    @GetMapping()
    @ResponseBody
    public List<User> getAllUser() {
        return userService.getAll();
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public User deleteOneUser(@PathVariable Long id) {
        return userService.delete(id);
    }
}
