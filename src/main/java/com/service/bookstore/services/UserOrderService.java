package com.service.bookstore.services;

import com.service.bookstore.exceptions.BadRequestException;
import com.service.bookstore.models.Order;
import com.service.bookstore.models.User;
import com.service.bookstore.payloads.UserOrderResponse;
import com.service.bookstore.reposistories.OrderReposistory;
import com.service.bookstore.reposistories.UserReposistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by nipon on 4/26/18.
 */
@Service
public class UserOrderService {
    private UserReposistory userReposistory;
    private OrderReposistory orderReposistory;

    @Autowired
    public UserOrderService(UserReposistory userReposistory, OrderReposistory orderReposistory) {
        this.userReposistory = userReposistory;
        this.orderReposistory = orderReposistory;
    }

    public UserOrderResponse getUserAndOrders(User user) {
        User userDetail = userReposistory.findById(user.getId())
                .orElseThrow(() -> new BadRequestException("Wrong user ID"));
        List<Order> userOrders = orderReposistory.findByUser(user);

        UserOrderResponse userOrderResponse = new UserOrderResponse();
        userOrderResponse.setUserDetail(userDetail);
        userOrderResponse.setBooksFrom(userOrders);

        return userOrderResponse;
    }
}
