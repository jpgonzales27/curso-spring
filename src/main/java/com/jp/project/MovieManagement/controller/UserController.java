package com.jp.project.MovieManagement.controller;

import com.jp.project.MovieManagement.persistence.entity.User;
import com.jp.project.MovieManagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> findAllUsers(@RequestParam(required = false) String name) {

        List<User> users = null;

        if(StringUtils.hasText(name)) {
            users = userService.findAllByName(name);
        } else {
            users =userService.findAll();
        }
        return users;
    }

    @GetMapping("/{username}")
    public User findUserById(@PathVariable String username) {
        return userService.findOneByUsername(username);
    }
}
