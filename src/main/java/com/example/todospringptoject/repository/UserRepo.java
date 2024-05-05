package com.example.todospringptoject.repository;

import com.example.todospringptoject.model.entity.TodoEntity;
import com.example.todospringptoject.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

//репозиторий работает токо с бд - один слой авбстракции
public interface UserRepo extends CrudRepository<UserEntity, Long>, JpaSpecificationExecutor<UserEntity> {
    //создаем методы если не подходят дефолтные
    UserEntity findByUsername(String username); //также важно название findBy...

}
