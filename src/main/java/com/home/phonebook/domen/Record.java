package com.home.phonebook.domen;

import lombok.Data;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicLong;

@Data
public class Record implements Serializable {

    private static AtomicLong idCounter = new AtomicLong();

    private Long id;
    private String name;
    private String phoneNumber;

    public Record() {
    }

    public Record(Long id, String name, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public void setId() {
        this.id = idCounter.incrementAndGet();
    }
}
