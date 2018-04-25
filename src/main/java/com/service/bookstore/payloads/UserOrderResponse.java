package com.service.bookstore.payloads;

import com.service.bookstore.models.Order;
import com.service.bookstore.models.User;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by nipon on 4/25/18.
 */
public class UserOrderResponse {
    private String name;
    private String surname;
    private LocalDate dateOfBirth;
    private List<Long> books;

    public void setUserDetail(User user) {
        name = user.getName();
        surname = user.getSurname();
        dateOfBirth = user.getBirthDate();
    }

    public void setBooksFrom(List<Order> orders) {
        books = orders.stream()
            .map(order -> order.getBook().getBookId())
            .collect(Collectors.toList());
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public List<Long> getBooks() {
        return books;
    }
}
