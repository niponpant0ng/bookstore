package com.service.bookstore.reposistories;

import com.service.bookstore.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Created by nipon on 4/23/18.
 */
public interface UserReposistory extends JpaRepository<User, UUID> {
    User findByUsernameAndPassword(String username, String password);
}
