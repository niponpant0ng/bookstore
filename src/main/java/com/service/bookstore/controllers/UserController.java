package com.service.bookstore.controllers;

import com.service.bookstore.exceptions.BadRequestException;
import com.service.bookstore.exceptions.ServerInternalErrorException;
import com.service.bookstore.models.User;
import com.service.bookstore.payloads.UserOrderResponse;
import com.service.bookstore.services.UserOrderService;
import com.service.bookstore.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.*;

/**
 * Created by nipon on 4/23/18.
 */
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserOrderService userOrderService;

    @PostMapping
    public User createUser(@RequestBody User user) {
        try {
            return userService.create(user);
        } catch (TransactionSystemException txEx) {
            throw new BadRequestException("Can't create user");
        } catch (Exception ex) {
            throw new ServerInternalErrorException();
        }
    }

    @GetMapping
    public UserOrderResponse getUserOrder() {
        return userOrderService.getUserAndOrders(new User());
    }

    @DeleteMapping
    public void deleteUserOrder() {
        try {
            userOrderService.deleteUserAndOrders(new User());
        } catch (TransactionSystemException txEx) {
            throw new BadRequestException("Can't delete user");
        } catch (Exception ex) {
            throw new ServerInternalErrorException();
        }
    }
}
