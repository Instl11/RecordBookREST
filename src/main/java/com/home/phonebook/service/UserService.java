package com.home.phonebook.service;

import com.home.phonebook.domen.User;
import com.home.phonebook.exceptions.UserNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private List<User> userList;

    public UserService(List<User> userList) {
        this.userList = userList;
    }

    public User findUserById(Long id) {
        return userList
                .stream()
                .filter(user -> id.equals(user.getId()))
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found"));
    }

    public List<User> findUsersByName(String name) {
        List<User> users = userList
                .stream()
                .filter(user -> user.getName().startsWith(name))
                .collect(Collectors.toList());
        if (users.size() == 0) {
            throw new UserNotFoundException("User with name " + name + " not found");
        } else {
            return users;
        }
    }
}
