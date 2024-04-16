package com.example.todospringptoject.repository;

import com.example.todospringptoject.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

//репозиторий работает токо с бд - один слой авбстракции
public interface UserRepo extends CrudRepository<UserEntity, Long> {
    //создаем методы если не подходят дефолтные
    UserEntity findByUsername(String username); //также важно название findBy...
}
