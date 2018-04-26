package com.service.bookstore.controllers;

import com.service.bookstore.exceptions.BadRequestException;
import com.service.bookstore.exceptions.ServerInternalErrorException;
import com.service.bookstore.models.User;
import com.service.bookstore.models.UserPrincipal;
import com.service.bookstore.payloads.UserOrderResponse;
import com.service.bookstore.services.UserOrderService;
import com.service.bookstore.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public UserOrderResponse getUserOrder(Authentication authentication) {
        try {
            return userOrderService.getUserAndOrders(getUserFrom(authentication));
        } catch (BadRequestException badEx) {
            throw badEx;
        } catch (Exception ex) {
            throw new ServerInternalErrorException();
        }
    }

    @DeleteMapping
    public void deleteUserOrder(Authentication authentication) {
        try {
            userOrderService.deleteUserAndOrders(getUserFrom(authentication));
        } catch (TransactionSystemException txEx) {
            throw new BadRequestException("Can't delete user");
        } catch (Exception ex) {
            throw new ServerInternalErrorException();
        }
    }

    @PostMapping("/orders")
    public Double orderUser(Authentication authentication, @RequestBody List<Long> bookIds) {
        try {
            return userOrderService.orderUserBook(getUserFrom(authentication), bookIds);
        } catch (BadRequestException badEx) {
            throw badEx;
        } catch (Exception ex) {
            throw new ServerInternalErrorException();
        }
    }

    private User getUserFrom(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        return userPrincipal.toUser();
    }
}
