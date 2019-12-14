package com.home.phonebook;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.home.phonebook.domen.Record;
import com.home.phonebook.domen.User;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(PER_METHOD)
@DisplayName("Testing RecordController")
public class RecordControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper om;
    @Autowired
    private List<User> userList;

    @BeforeEach
    public void init() {
        Record record = new Record(5L, "Ivan", "555");
        Record record2 = new Record(50L, "Misha", "999");
        ArrayList<Record> records = new ArrayList<>(Arrays.asList(record, record2));
        User user = new User(10L, "Petr", records);
        userList.add(user);
    }

    @Test
    void getRecordById() throws Exception {
        mockMvc.perform(get("/api/users/10/records/5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(5)))
                .andExpect(jsonPath("$.name", Matchers.is("Ivan")))
                .andExpect(jsonPath("$.phoneNumber", Matchers.is("555")));
    }

    @Test
    void findRecordByNumber() throws Exception {
        mockMvc.perform(get("/api/users/10/records/find/555"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(5)))
                .andExpect(jsonPath("$.name", Matchers.is("Ivan")))
                .andExpect(jsonPath("$.phoneNumber", Matchers.is("555")));
    }

    @Test
    void createRecord() throws Exception {
        Record record = new Record();
        record.setName("Bob");
        record.setPhoneNumber("777");
        mockMvc.perform(post("/api/users/10/records")
                .content(om.writeValueAsString(record))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", Matchers.is(1)))
                .andExpect(jsonPath("$.name", Matchers.is("Bob")))
                .andExpect(jsonPath("$.phoneNumber", Matchers.is("777")));
    }

    @Test
    void editRecord() throws Exception {
        Record record = new Record();
        record.setName("Andrew");
        record.setPhoneNumber("111");
        mockMvc.perform(put("/api/users/10/records/50")
                .content(om.writeValueAsString(record))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", Matchers.is(50)))
                .andExpect(jsonPath("$.name", Matchers.is("Andrew")))
                .andExpect(jsonPath("$.phoneNumber", Matchers.is("111")));
    }

}
