package com.home.phonebook.controller;

import com.home.phonebook.domen.Record;
import com.home.phonebook.domen.User;
import com.home.phonebook.service.RecordService;
import com.home.phonebook.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/users/{userId}/records")
public class RecordController {

    private RecordService recordService;
    private UserService userService;

    public RecordController(RecordService recordService, UserService userService) {
        this.recordService = recordService;
        this.userService = userService;
    }

    @GetMapping("/{recordId}")
    public Record getRecordById(@PathVariable("recordId") Long recordId,
                                @PathVariable("userId") Long userId) {
        return recordService.getRecordById(recordId, userId);
    }

    @GetMapping("/find/{number}")
    public Record findRecordByNumber(@PathVariable("userId") Long userId,
                                     @PathVariable("number") String number) {
        return recordService.findRecordByNumber(userId, number);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Record createRecord(@RequestBody Record record,
                               @PathVariable("userId") Long userId) {
        User user = userService.findUserById(userId);
        record.setId();
        user.getPhoneBook().add(record);
        return record;
    }

    @PutMapping("/{recordId}")
    public Record editRecord(@RequestBody Record record,
                             @PathVariable("userId") Long userId,
                             @PathVariable("recordId") Long recordId) {

        Record recFromBook = recordService.getRecordById(recordId, userId);
        BeanUtils.copyProperties(record, recFromBook, "id");
        return recFromBook;
    }

    @DeleteMapping("/{recordId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRecord(@PathVariable("recordId") Long recordId,
                             @PathVariable("userId") Long userId) {
        recordService.deleteRecordById(recordId, userId);
    }
}
