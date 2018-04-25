package com.service.bookstore.services;

import com.service.bookstore.models.User;
import com.service.bookstore.models.UserClaim;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.UUID;

/**
 * Created by nipon on 4/24/18.
 */
@Service
public class JwtService {
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    @Value("${jwt.secret_key}")
    private String jwtSecret;

    @Value("${jwt.expire_in_ms}")
    private int jwtExpireInMs;

    public String createToken(User user) {
        UserClaim claim = new UserClaim(jwtExpireInMs);

        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(jwtSecret);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        return Jwts.builder()
                .setClaims(claim.createClaim(user.getId()))
                .signWith(signatureAlgorithm, signingKey)
                .compact();
    }


    public UUID getUserIdFromToken(String jwt) {
        Claims claim = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(jwtSecret))
                .parseClaimsJws(jwt)
                .getBody();

        return UUID.fromString(claim.getSubject());
    }
}
