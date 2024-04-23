package com.example.todospringptoject.container;

import com.example.todospringptoject.model.entity.TodoEntity;
import com.example.todospringptoject.model.entity.UserEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

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


    @Order(1)
    @Test
    void registration() throws Exception {

        UserEntity user = UserEntity.builder()
                .username("user123")
                .password("password123")
                .todos(Arrays.asList(
                        TodoEntity.builder()
                                .title("Task 1")
                                .completed(false)
                                .description("Description for Task 1")
                                .build(),
                        TodoEntity.builder()
                                .title("Task 2")
                                .completed(true)
                                .description("Description for Task 2")
                                .build()
                ))
                .build();
        String userJson = objectMapper.writeValueAsString(user);
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isOk());
    }

    @Order(2)
    @Test
    void getOneUser() throws Exception {

        MvcResult result = mockMvc.perform(get("/users")//создаем запрос
                        .param("id", "1")//добавляем параметр к запросу
                        .contentType(MediaType.APPLICATION_JSON))//ожидает что вернеися json
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.username").exists())
                .andExpect(jsonPath("$.todos").isArray())
                .andReturn();//для присвоения result значения которое вернется пользователю
        //verify(userService, times(1)).getOne(anyLong());//проверка что метод вызывался токо один раз c любым парметром типа Long
        String jsonResponse = result.getResponse().getContentAsString();
        System.out.println("getOneUser test JSON response: " + jsonResponse);
    }

    @Order(3)
    @Test
    void deleteOneUser() throws Exception {

        MvcResult result = mockMvc.perform(delete("/users/{id}", 1L))
                .andExpect(status().isOk())
                .andReturn();
        String jsonResponse = result.getResponse().getContentAsString();
        System.out.println("deleteOneUser test JSON response id: " + jsonResponse);
    }
}