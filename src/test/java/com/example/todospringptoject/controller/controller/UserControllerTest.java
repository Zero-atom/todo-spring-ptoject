package com.example.todospringptoject.controller.controller;

import com.example.todospringptoject.mapper.UserMapper;
import com.example.todospringptoject.model.dto.User;
import com.example.todospringptoject.model.entity.TodoEntity;
import com.example.todospringptoject.model.entity.UserEntity;
import com.example.todospringptoject.repository.TodoRepo;
import com.example.todospringptoject.repository.UserRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

//для реальных запрос вместо MockMvc -> io.restassured
import static io.restassured.RestAssured.given;


@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
@Disabled
class UserControllerTest {


    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private TodoRepo todoRepo;

    @Container
    private static final MySQLContainer<?> mysql = new MySQLContainer<>(DockerImageName.parse("mysql"));

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mysql::getJdbcUrl);
        registry.add("spring.datasource.username", mysql::getUsername);
        registry.add("spring.datasource.password", mysql::getPassword);
    }

    private static final String userResource = "src/test/resources/user.json";

    private static UserEntity readFromResource() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(new File(userResource), UserEntity.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Test
    void registration_whenValidUserProvided_thenUserSuccessfullyRegistered() throws Exception {
        //чистка бд
        userRepo.deleteAll();

        RestAssured.port = 8080; // Установка порта

        //Given
        UserEntity user = readFromResource();
        //userRepo.save(user);

        //When
        User actual = given()
                .port(RestAssured.port)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .body(user)
                .when()
                .post("/users")
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .body()
                .as(User.class);

        //Then
        User expected = userMapper.userEntityToUser(user); // Получение ожидаемого пользователя
        //assertEquals(expected, actual); // Сравнение ожидаемого и фактического пользователей

        // Сравнение каждого поля отдельно
        assertEquals(expected.getUsername(), actual.getUsername());

        //чистка бд
        userRepo.deleteAll();
    }

    @Test
    void getOneUser_whenExistingUserIdProvided_thenRetrieveUserSuccessfully() throws IOException {
        //чистка бд
        userRepo.deleteAll();

        RestAssured.port = 8080; // Установка порта

        //Given
        UserEntity user = readFromResource();
        // Сохраняем пользователя перед тем, как получить его
        Long userId = userRepo.save(user).getId();

        //When
        User actual = given()
                .port(RestAssured.port)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/users/{userId}", userId) // Используем userId для получения пользователя
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .body()
                .as(User.class);

        //Then
        User expected = userMapper.userEntityToUser(user); // Получаем ожидаемого пользователя
        //assertEquals(expected, actual); // Сравниваем ожидаемого и фактического пользователя (по ссылке)

        // Сравнение каждого поля отдельно
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getUsername(), actual.getUsername());

        //чистка бд
        userRepo.deleteAll();
    }
}