package com.service.bookstore.reposistories;

import com.service.bookstore.models.Order;
import com.service.bookstore.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

/**
 * Created by nipon on 4/23/18.
 */
public interface OrderReposistory extends JpaRepository<Order, UUID> {
    List<Order> findByUser(User user);
}
