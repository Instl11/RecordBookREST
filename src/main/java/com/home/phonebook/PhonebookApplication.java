package com.home.phonebook;

import com.home.phonebook.domen.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class PhonebookApplication {

    public static void main(String[] args) {
        SpringApplication.run(PhonebookApplication.class, args);
    }
}
