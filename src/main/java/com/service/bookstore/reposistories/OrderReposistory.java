package com.service.bookstore.reposistories;

import com.service.bookstore.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Created by nipon on 4/23/18.
 */
public interface OrderReposistory extends JpaRepository<Order, UUID> {
}
