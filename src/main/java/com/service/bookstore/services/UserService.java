package com.service.bookstore.services;

import com.service.bookstore.models.User;
import com.service.bookstore.reposistories.UserReposistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by nipon on 4/23/18.
 */
@Service
public class UserService {
    private UserReposistory userReposistory;

    @Autowired
    public UserService(UserReposistory userReposistory) {
        this.userReposistory = userReposistory;
    }

    public User create(User user) {
        return userReposistory.save(user);
    }
}
