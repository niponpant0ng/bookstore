package com.service.bookstore.services;

import com.service.bookstore.exceptions.CredentialWrongException;
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

    private UserJwtService userJwtService;

    @Autowired
    public UserService(UserReposistory userReposistory, UserJwtService userJwtService) {
        this.userReposistory = userReposistory;
        this.userJwtService = userJwtService;
    }

    public User create(User user) {
        return userReposistory.save(user);
    }

    public String login(User loginUser) {
        User user = userReposistory.findByUsernameAndPassword(loginUser.getUsername(), loginUser.getPassword());
        if(user == null) {
            throw new CredentialWrongException();
        } else {
            return userJwtService.createUserJwt(user);
        }
    }
}
