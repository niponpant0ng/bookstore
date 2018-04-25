package com.service.bookstore.services;

import com.service.bookstore.exceptions.CredentialException;
import com.service.bookstore.models.User;
import com.service.bookstore.reposistories.UserReposistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Created by nipon on 4/23/18.
 */
@Service
public class UserService {
    private UserReposistory userReposistory;
    private JwtService jwtService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserReposistory userReposistory, JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.userReposistory = userReposistory;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public User create(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userReposistory.save(user);
    }

    public String login(User loginUser) {
        User user = userReposistory.findByUsername(loginUser.getUsername());

        if(user == null || !passwordEncoder.matches(loginUser.getPassword(), user.getPassword()) ) {
            throw new CredentialException();
        } else {
            return jwtService.createToken(user);
        }
    }

    public User getUserById(UUID userId) {
        return userReposistory.findById(userId).orElseThrow(CredentialException::new);
    }
}
