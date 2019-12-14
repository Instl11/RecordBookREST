package com.home.phonebook.domen;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Getter @Setter
public class User implements Serializable {

    private static AtomicLong idCounter = new AtomicLong();

    private Long id;
    private String name;
    private List<Record> phoneBook = new ArrayList<>();

    public void setIdAndIncrement() {
        this.id = idCounter.incrementAndGet();
    }

    public User() {
    }

    public User(Long id, String name, List<Record> phoneBook) {
        this.id = id;
        this.name = name;
        this.phoneBook = phoneBook;
    }
}
