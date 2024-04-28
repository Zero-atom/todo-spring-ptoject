package com.example.todospringptoject.controller.controller;

import com.example.todospringptoject.model.dto.Todo;
import com.example.todospringptoject.model.dto.User;
import com.example.todospringptoject.model.entity.TodoEntity;
import com.example.todospringptoject.model.entity.UserEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import com.example.todospringptoject.service.UserService;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//для реальных запрос вместо MockMvc -> io.restassured
import static io.restassured.RestAssured.given;


@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
class UserControllerTest {


    @Autowired
    private ObjectMapper objectMapper;

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
    void registration() throws Exception {
        RestAssured.port = 8080; // Установка порта

        UserEntity user = readFromResource();
//        UserEntity user = UserEntity.builder()
//                .username("user123")
//                .password("password123")
//                .todos(Arrays.asList(
//                        TodoEntity.builder()
//                                .title("Task 1")
//                                .completed(false)
//                                .description("Description for Task 1")
//                                .build(),
//                        TodoEntity.builder()
//                                .title("Task 2")
//                                .completed(true)
//                                .description("Description for Task 2")
//                                .build()
//                ))
//                .build();

        given()
                .port(RestAssured.port)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .body(user)
                .when()
                .post("/users")
                .then()
                .assertThat()
                .statusCode(200);
    }
}