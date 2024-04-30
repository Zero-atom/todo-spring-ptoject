package com.example.todospringptoject.service;


import com.example.todospringptoject.exception.UsersNotFoundException;
import com.example.todospringptoject.mapper.UserMapper;
import com.example.todospringptoject.model.entity.UserEntity;
import com.example.todospringptoject.exception.UserAlreadyExistException;
import com.example.todospringptoject.exception.UserNotFoundException;
import com.example.todospringptoject.model.dto.User;
import com.example.todospringptoject.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//сервис работает с конкретной бизнес логикой - второй слой абстракции
@Service// что бы потом использовать Autowired
public class UserService {

    @Autowired
    private UserRepo userRepo;//внедрение репо

    @Autowired
    private UserMapper userMapper; // Внедряем маппер

    public User registration(UserEntity user){

        //userService.registration(user);
        //userRepo.find ... - > findAl, findById, findAllById,,, важно название для методов созданных по дефолту

        System.out.println(userRepo.findByUsername(user.getUsername()));
        if (userRepo.findByUsername(user.getUsername()) == null) {

            userRepo.save(user);
            return userMapper.userEntityToUser(user);
        } else {
            throw  new UserAlreadyExistException();
        }
    }

    public User getOne (Long id)  {
//        UserEntity user = userRepo.findById(id).get();
//        if (user == null) {
//            throw new UserNotFoundException("Пользователь не найден");
//        }
//        return User.toModel(user);

        Optional<UserEntity> optionalUser = userRepo.findById(id);
        if (optionalUser.isPresent()) {
            UserEntity user = optionalUser.get();
            return userMapper.userEntityToUser(user);
        } else {
            throw new UserNotFoundException("Пользователь не найден");
        }
    }

    public List<User> getAll () {
        Iterable<UserEntity> userEntities = userRepo.findAll();
        List<User> users = new ArrayList<>();

        // Перебираем все элементы Iterable и конвертируем их в модели User
        for (UserEntity userEntity : userEntities) {
            users.add(userMapper.userEntityToUser(userEntity)); // Преобразование сущности в DTO
        }

        // Если список пользователей пустой, выбрасываем исключение
        if (!users.isEmpty()) {
            return users;
        } else {
            throw new UsersNotFoundException("Пользователи не найдены");
        }

    }

    public User delete(Long id){
        Optional<UserEntity> optionalUser = userRepo.findById(id);
        if (optionalUser.isPresent()) {
            UserEntity user = optionalUser.get();
            userRepo.deleteById(id);
            return userMapper.userEntityToUser(user);
        } else {
            throw new UserNotFoundException("Пользователь не найден");
        }
    }
}
