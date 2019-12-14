package com.home.phonebook.config;

import com.home.phonebook.domen.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class UserConfig {

    @Bean
    public List<User> userList() {
        return new ArrayList<>();
    }
}
