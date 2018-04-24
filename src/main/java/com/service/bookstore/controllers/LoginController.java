package com.service.bookstore.controllers;

import com.service.bookstore.exceptions.CredentialWrongException;
import com.service.bookstore.models.User;
import com.service.bookstore.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by nipon on 4/24/18.
 */
@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private UserService userService;

    @PostMapping
    public Map<String, String> login(@RequestBody User user) {
        Map<String, String> jwt = new HashMap<>();
        jwt.put("token", userService.login(user));

        return jwt;
    }
}
