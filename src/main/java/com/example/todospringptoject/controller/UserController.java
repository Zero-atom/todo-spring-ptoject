package com.example.todospringptoject.controller;

import com.example.todospringptoject.model.entity.UserEntity;
import com.example.todospringptoject.exception.UserAlreadyExistException;
import com.example.todospringptoject.exception.UserNotFoundException;
import com.example.todospringptoject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//контроллер работает с запросами и ответами  - третий слой абстракции
@RestController
@RequestMapping("/users")
public class UserController { // alt + enter - создать тест

    @Autowired
    private UserService userService;//внедрение репо

    @PostMapping
    public ResponseEntity registration(@RequestBody UserEntity user) {
        try {
            userService.registration(user);
            return ResponseEntity.ok("Пользователь успешно сохранен");
        } catch (UserAlreadyExistException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }  catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @GetMapping()
    @ResponseBody
    public ResponseEntity getOneUser(@RequestParam Long id) {
        try {
            return ResponseEntity.ok(userService.getOne(id));
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    //пример получениме параметра из строки запроса
    @DeleteMapping("/{id}")
    public ResponseEntity deleteOneUser(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(userService.delete(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }
}
