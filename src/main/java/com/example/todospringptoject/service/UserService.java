package com.example.todospringptoject.service;


import com.example.todospringptoject.entity.UserEntity;
import com.example.todospringptoject.exception.UserAlreadyExistException;
import com.example.todospringptoject.exception.UserNotFoundException;
import com.example.todospringptoject.model.User;
import com.example.todospringptoject.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

//сервис работает с конкретной бизнес логикой - второй слой абстракции
@Service// что бы потом использовать Autowired
public class UserService {

    @Autowired
    private UserRepo userRepo;//внедрение репо

    public UserEntity registration(UserEntity user) throws UserAlreadyExistException {//registerUser - убратть

        //userService.registration(user);
        //userRepo.find ... - > findAl, findById, findAllById,,, важно название для методов созданных по дефолту

        if (userRepo.findByUsername(user.getUsername()) != null) {
            throw  new UserAlreadyExistException("Пользователь с таким именем существует");//Лучше не писать что "пользователь такой уже есть"
            //пользователь должен получать как можно меньше информации о структуре проекта
        }
        return userRepo.save(user);
    }

    public User getOne (Long id) throws UserNotFoundException {
//        UserEntity user = userRepo.findById(id).get();
//        if (user == null) {
//            throw new UserNotFoundException("Пользователь не найден");
//        }
//        return User.toModel(user);

        Optional<UserEntity> optionalUser = userRepo.findById(id);
        if (optionalUser.isPresent()) {
            UserEntity user = optionalUser.get();
            return User.toModel(user);
        } else {
            throw new UserNotFoundException("Пользователь не найден");
        }
    }

    public Long delete(Long id) throws UserNotFoundException {
        userRepo.deleteById(id);
        return id;
    }
}
