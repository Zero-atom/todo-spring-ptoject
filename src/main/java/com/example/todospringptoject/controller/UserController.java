package com.example.todospringptoject.controller;

import com.example.todospringptoject.model.dto.User;
import com.example.todospringptoject.model.entity.UserEntity;
import com.example.todospringptoject.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//контроллер работает с запросами и ответами  - третий слой абстракции
@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController { // alt + enter - создать тест

    private UserService userService;//внедрение репо

    @PostMapping
    @ResponseBody
    public User registration(@RequestBody UserEntity user) {
        return userService.create(user);
    }

    @GetMapping("/{userId}")
    @ResponseBody
    public User getOneUser(@PathVariable Long userId) {
        //userService.saveRandomUsers();
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
