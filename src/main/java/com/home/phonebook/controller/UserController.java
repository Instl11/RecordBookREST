package com.home.phonebook.controller;

import com.home.phonebook.domen.Record;
import com.home.phonebook.domen.User;
import com.home.phonebook.exceptions.BookNotFoundException;
import com.home.phonebook.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/users")
public class UserController {

    private UserService userService;
    private List<User> userList;

    public UserController(UserService userService, List<User> userList) {
        this.userService = userService;
        this.userList = userList;
    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        if (userList.size() == 0) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(userList);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") Long id) {
        User foundUser = userService.findUserById(id);
        return ResponseEntity.ok(foundUser);
    }

    @GetMapping("/find/{name}")
    public List<User> getUsersByName(@PathVariable("name") String name) {
        return userService.findUsersByName(name);
    }

    @GetMapping("{userId}/phonebook")
    public ResponseEntity<List<Record>> getUserPhonebook(@PathVariable("userId") Long userId) {
        User user = userService.findUserById(userId);
        List<Record> phoneBook = user.getPhoneBook();
        if (phoneBook != null) {
            return ResponseEntity.ok(phoneBook);
        } else {
            throw new BookNotFoundException("User's phone book is empty");
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@RequestBody User user) {
        user.setIdAndIncrement();
        userList.add(user);
        return user;
    }

    @PutMapping("/{id}")
    public User editUser(@PathVariable("id") Long id, @RequestBody User editedUser) {
        User userFromList = userService.findUserById(id);
        BeanUtils.copyProperties(editedUser, userFromList, "id", "phoneBook");
        return userFromList;
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable("id") Long id) {
        User user = userService.findUserById(id);
        userList.remove(user);
    }


}
