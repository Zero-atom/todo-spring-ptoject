package com.example.todospringptoject;

import com.example.todospringptoject.controller.UserController;
import com.example.todospringptoject.model.dto.Todo;
import com.example.todospringptoject.model.dto.User;
import com.example.todospringptoject.model.entity.UserEntity;
import com.example.todospringptoject.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


//@ExtendWith(MockitoExtension.class)//нужно из за UserService в контролере
@WebMvcTest(UserController.class)
class UserControllerTest {

    @MockBean
    private UserService userService;//создаем мок объект

//    @InjectMocks  //закоменчен т к сипользуется @WebMvcTest(UserController.class)
//    private UserController userController;//внедряет моки в тестируемый класс (UserController)

    @Autowired
    private MockMvc mockMvc;//позволяет эмулировать http запросы к контроллеру

    @Autowired
    private ObjectMapper objectMapper;//для серилизации объекта в Json

//    @BeforeEach
////активируется перед каждым тестом (инициализиурет mockMvc)
//    void setUp() {
//        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
//        //MockMvcBuilders.standaloneSetup позволяет протестировать определенный контроллер на затаргивая другие модули
//
//        objectMapper = new ObjectMapper();
//    } //закоменчен т к сипользуется @WebMvcTest и @Autowired к MockMvc , ObjectMapper

    @Test
    void getUser() throws Exception {
        User user = User.builder()
                .id(1L)
                .username("user")
                .todos(Arrays.asList(
                        Todo.builder()
                                .id(1L)
                                .title("Todo title 1")
                                .completed(false)
                                .description("Todo description 1")
                                .build(),
                        Todo.builder()
                                .id(2L)
                                .title("Todo title 2")
                                .completed(true)
                                .description("Todo description 2")
                                .build(),
                        Todo.builder()
                                .id(3L)
                                .title("Todo title 3")
                                .completed(false)
                                .description("Todo description 3")
                                .build()
                ))
                .build();
        when(userService.getOne(anyLong())).thenReturn(user);
        MvcResult result = mockMvc.perform(get("/users")//создаем запрос
                        .param("id", "1")//добавляем параметр к запросу
                        .contentType(MediaType.APPLICATION_JSON))//ожидает что вернеися json
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.username").exists())
                .andExpect(jsonPath("$.todos").isArray())
                .andExpect(jsonPath("$.todos[*].id").isNotEmpty())
                .andReturn();//для присвоения result значения которое вернется пользователю
        verify(userService, times(1)).getOne(anyLong());//проверка что метод вызывался токо один раз c любым парметром типа Long
        String jsonResponse = result.getResponse().getContentAsString();
        System.out.println("JSON response: " + jsonResponse);
    }

    @Test
    void postUser() throws Exception {
        UserEntity user = UserEntity.builder().id(1L).username("user11").password("pass").build();
        String userJson = objectMapper.writeValueAsString(user);
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isOk());
        verify(userService, times(1)).registration(any(UserEntity.class));
    }

    @Test
    void deleteUser() throws Exception {
        when(userService.delete(anyLong())).thenReturn(anyLong());
        mockMvc.perform(delete("/users/{id}", 1L))
                .andExpect(status().isOk());
        verify(userService, times(1)).delete(anyLong());
    }
}


