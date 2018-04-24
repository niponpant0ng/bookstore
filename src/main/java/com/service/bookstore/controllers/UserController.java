package com.service.bookstore.controllers;

import com.service.bookstore.exceptions.CreateException;
import com.service.bookstore.exceptions.ServerInternalErrorException;
import com.service.bookstore.models.User;
import com.service.bookstore.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by nipon on 4/23/18.
 */
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public User createUser(@RequestBody User user) {
        try {
            return userService.create(user);
        } catch (TransactionSystemException txEx) {
            throw new CreateException("user");
        } catch (Exception ex) {
            throw new ServerInternalErrorException();
        }
    }
}
