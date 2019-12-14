package com.home.phonebook;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.home.phonebook.controller.UserController;
import com.home.phonebook.domen.Record;
import com.home.phonebook.domen.User;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.TestInstance.Lifecycle.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(PER_METHOD)
@DisplayName("Testing UserController")
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper om;
    @Autowired
    private List<User> userList;

    @BeforeEach
    public void init() {
        Record record = new Record(5L, "Ivan", "555");
        ArrayList<Record> records = new ArrayList<>(Arrays.asList(record));
        User user = new User(10L, "Petr", records);
        userList.add(user);
    }

    @Test
    void getAllUsers() throws Exception {
        mockMvc.perform(get("/api/users/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", Matchers.is(10)))
                .andExpect(jsonPath("$[0].name", Matchers.is("Petr")));
    }

    @Test
    void getUserById() throws Exception {
        mockMvc.perform(get("/api/users/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(10)))
                .andExpect(jsonPath("$.name", Matchers.is("Oleg")));
    }

    @Test
    void findUserByName() throws Exception {
        mockMvc.perform(get("/api/users/find/Pe"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", Matchers.is(10)))
                .andExpect(jsonPath("$[0].name", Matchers.is("Petr")));
    }

    @Test
    void getuserPhonebook() throws Exception {
        mockMvc.perform(get("/api/users/10/phonebook"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", Matchers.is(5)))
                .andExpect(jsonPath("$[0].name", Matchers.is("Ivan")))
                .andExpect(jsonPath("$[0].phoneNumber", Matchers.is("555")));
    }

    @Test
    void createUser() throws Exception {
        User user = new User();
        user.setName("Petr");
        mockMvc.perform(post("/api/users")
                .content(om.writeValueAsString(user))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", Matchers.is(1)))
                .andExpect(jsonPath("$.name", Matchers.is("Petr")));
    }

    @Test
    void editUser() throws Exception {
        User user = new User();
        user.setName("Oleg");
        mockMvc.perform(put("/api/users/10")
                .content(om.writeValueAsString(user))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(10)))
                .andExpect(jsonPath("$.name", Matchers.is("Oleg")))
                .andExpect(jsonPath("$.phoneBook", Matchers.notNullValue()));
    }

    @Test
    @DisplayName("Delete test: positive")
    void deleteUserPos() throws Exception {
        mockMvc.perform(delete("/api/users/10"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Delete test: negative")
    void deleteUserNeg() throws Exception {
        mockMvc.perform(delete("/api/users/100"))
                .andExpect(status().isNotFound());
    }


}
