package com.service.bookstore.reposistories;

import com.service.bookstore.models.Order;
import com.service.bookstore.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

/**
 * Created by nipon on 4/23/18.
 */
public interface OrderReposistory extends JpaRepository<Order, UUID> {
    List<Order> findByUser(User user);

    @Modifying
    @Query("DELETE FROM Order o WHERE o.user = :user")
    void deleteByUser(@Param("user") User user);
}
