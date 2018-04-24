package com.service.bookstore.models;

import org.springframework.beans.BeanUtils;

import java.util.*;

/**
 * Created by nipon on 10/29/16.
 */
public class UserClaim {
    private final int EXPIRE_TOKEN_TIME = 1;

    private String jti;
    private String iss = "book store";
    private Date exp;

    public UserClaim() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR, EXPIRE_TOKEN_TIME);

        jti = UUID.randomUUID().toString();
        exp = calendar.getTime();
        iss = "book store";
    }

    public Map<String, Object> createClaim(User user) {
        Map<String, Object> claims = new HashMap<>();

        claims.put("jti", jti);
        claims.put("iss", iss);
        claims.put("sub", createSubject(user));
        claims.put("exp", exp);

        return claims;
    }

    private User createSubject(User user) {
        User claimUser = new User();
        BeanUtils.copyProperties(user, claimUser);

        claimUser.setId(null);
        claimUser.setPassword("");

        return claimUser;
    }
}
