package com.example.todospringptoject.service;


import com.example.todospringptoject.exception.UsersNotFoundException;
import com.example.todospringptoject.mapper.UserMapper;
import com.example.todospringptoject.model.entity.UserEntity;
import com.example.todospringptoject.exception.UserAlreadyExistException;
import com.example.todospringptoject.exception.UserNotFoundException;
import com.example.todospringptoject.model.dto.User;
import com.example.todospringptoject.repository.UserRepo;
import com.example.todospringptoject.specification.UserSpecification;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//для теста
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


//сервис работает с конкретной бизнес логикой - второй слой абстракции
@Service// что бы потом использовать Autowired
@AllArgsConstructor
@Slf4j
public class UserService {

    private UserRepo userRepo;//внедрение репо
    private UserMapper userMapper; // Внедряем маппер

    public User create(UserEntity user){

        if (userRepo.findByUsername(user.getUsername()) == null) {
            userRepo.save(user);
            log.info("Успешно зарегистрирован пользователь c Id: {}", user.getId());
            return userMapper.userEntityToUser(user);
        } else {
            throw  new UserAlreadyExistException("Пользователь уже существует");
        }
    }

    public User getOne (Long id)  {
        log.info("Метод getOne вызван с параметром: id={}", id);
        Optional<UserEntity> optionalUser = userRepo.findById(id);
        if (optionalUser.isPresent()) {
            UserEntity user = optionalUser.get();
            log.info("Найден пользователь с id={}", id);
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
            log.info("Успешно найдено {} пользователей", users.size());
            return users;
        } else {
            throw new UsersNotFoundException("Пользователи не найдены");
        }

    }

    //спецификация
    public List<User> getAll (String usernamePrefix, String titlePrefix) {
        // Создаем спецификацию для фильтрации пользователей
        Specification<UserEntity> spec = UserSpecification.usersWithTodosStartingWith(usernamePrefix, titlePrefix);

        // Получаем список пользователей, удовлетворяющих спецификации
        List<UserEntity> userEntities = userRepo.findAll(spec);

        List<User> users = new ArrayList<>();
        for (UserEntity userEntity : userEntities) {
            users.add(userMapper.userEntityToUser(userEntity)); // Преобразование сущности в DTO
        }

        // Если список пользователей пустой, выбрасываем исключение
        if (!users.isEmpty()) {
            log.info("Успешно найдено {} пользователей", users.size());
            return users;
        } else {
            throw new UsersNotFoundException("Пользователи не найдены");
        }
    }

    public User delete(Long id){
        log.info("Метод delete вызван с параметром: id={}", id);
        Optional<UserEntity> optionalUser = userRepo.findById(id);
        if (optionalUser.isPresent()) {
            UserEntity user = optionalUser.get();
            userRepo.deleteById(id);
            log.info("Пользователь с id={} удален", id);
            return userMapper.userEntityToUser(user);
        } else {
            throw new UserNotFoundException("Пользователь не найден");
        }

    }

    public void saveRandomUsers() {
        List<UserEntity> users = generateRandomUsers(10000);
        userRepo.saveAll(users);
        log.info("Сохранено {} случайных пользователей", users.size());
    }

    private List<UserEntity> generateRandomUsers(int count) {
        Random random = new Random();
        return IntStream.range(0, count)
                .mapToObj(i -> {
                    UserEntity user = new UserEntity();
                    user.setUsername("user" + i);
                    user.setPassword("password" + random.nextInt(1000));
                    return user;
                })
                .collect(Collectors.toList());
    }
}
