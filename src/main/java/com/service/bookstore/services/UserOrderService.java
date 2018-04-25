package com.service.bookstore.services;

import com.service.bookstore.exceptions.BadRequestException;
import com.service.bookstore.models.Book;
import com.service.bookstore.models.Order;
import com.service.bookstore.models.User;
import com.service.bookstore.payloads.UserOrderResponse;
import com.service.bookstore.reposistories.BookRepository;
import com.service.bookstore.reposistories.OrderReposistory;
import com.service.bookstore.reposistories.UserReposistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by nipon on 4/26/18.
 */
@Service
public class UserOrderService {
    private UserReposistory userReposistory;
    private OrderReposistory orderReposistory;
    private BookRepository bookRepository;

    @Autowired
    public UserOrderService(UserReposistory userReposistory, OrderReposistory orderReposistory, BookRepository bookRepository) {
        this.userReposistory = userReposistory;
        this.orderReposistory = orderReposistory;
        this.bookRepository = bookRepository;
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

    @Transactional
    public void deleteUserAndOrders(User user) {
        orderReposistory.deleteByUser(user);
        userReposistory.delete(user);
    }

    @Transactional
    public Double orderUserBook(User user, List<Long> bookIds) {
        if(bookIds.size() == 0) {
            throw new BadRequestException("Books are empty");
        }

        List<Book> books = bookIds.stream()
            .map(bookId -> bookRepository.findByBookId(bookId))
            .filter(book -> book != null)
            .collect(Collectors.toList());

        if(books.size() != bookIds.size()) {
            throw new BadRequestException("Some Book is wrong");
        }

        books.stream()
            .map(book -> {
                Order order = new Order();
                order.setUser(user);
                order.setBook(book);

                return order;
            })
            .forEach(order -> orderReposistory.save(order));

        return books.stream()
                .mapToDouble(Book::getPrice)
                .sum();
    }
}
