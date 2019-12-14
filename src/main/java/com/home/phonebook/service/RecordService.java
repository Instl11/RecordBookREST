package com.home.phonebook.service;

import com.home.phonebook.domen.Record;
import com.home.phonebook.domen.User;
import com.home.phonebook.exceptions.BookNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecordService {

    private List<User> userList;
    private UserService userService;

    public RecordService(List<User> userList, UserService userService) {
        this.userList = userList;
        this.userService = userService;
    }

    public Record getRecordById(Long recordId, Long userId) {
        User user = userService.findUserById(userId);
        List<Record> phoneBook = user.getPhoneBook();
        return findRecordById(recordId, phoneBook);
    }

    public void deleteRecordById(Long recordId, Long userId) {
        User user = userService.findUserById(userId);
        List<Record> phoneBook = user.getPhoneBook();
        Record record = findRecordById(recordId, phoneBook);
        phoneBook.remove(record);
    }

    private Record findRecordById(Long recordId, List<Record> phoneBook) {
        return phoneBook.stream()
                .filter(record -> recordId.equals(record.getId()))
                .findFirst()
                .orElseThrow(() -> new BookNotFoundException("Record with " + recordId + " id not found"));
    }

    public Record findRecordByNumber(Long userId, String number) {
        User user = userService.findUserById(userId);
        List<Record> phoneBook = user.getPhoneBook();
        return phoneBook.stream()
                .filter(r -> number.equals(r.getPhoneNumber()))
                .findFirst()
                .orElseThrow(() -> new BookNotFoundException("Record with " + number + " number not found"));
    }
}
