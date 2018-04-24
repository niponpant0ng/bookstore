package com.service.bookstore.services;

import com.service.bookstore.models.User;
import com.service.bookstore.models.UserClaim;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;

/**
 * Created by nipon on 4/24/18.
 */
@Service
public class UserJwtService {
    public String createUserJwt(User user) {
        UserClaim claim = new UserClaim();
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary("xxxx");
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        return Jwts.builder()
                .setClaims(claim.createClaim(user))
                .signWith(signatureAlgorithm, signingKey)
                .compact();
    }
}
