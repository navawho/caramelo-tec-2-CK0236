package com.api.caramelo.controllers;

import com.api.caramelo.JwtConstants;
import com.api.caramelo.controllers.dtos.SessionDTO;
import com.api.caramelo.exceptions.BusinessRuleException;
import com.api.caramelo.models.User;
import com.api.caramelo.services.SessionService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequiredArgsConstructor
public class SessionController {

    private final SessionService service;

    @PostMapping("/sign-in")
    public ResponseEntity store(@RequestBody SessionDTO sessionDTO) {
        try {
            Long userId = service.validateCredentials(sessionDTO.getUsername(), sessionDTO.getPassword());
            User user = service.getLoggedUser(userId);

            Map<String, Object> map = new HashMap<>();
            map.put("user", user);
            map.put("token", this.generateJWTToken(userId));

            return ok(map);
        } catch (BusinessRuleException e) {
            Map<String, Object> map = new HashMap<>();
            map.put("message", e.getMessage());

            return badRequest().body(map);
        }
    }

    private String generateJWTToken(Long userId) {
        long timestamp = System.currentTimeMillis();
        String token = Jwts.builder().signWith(SignatureAlgorithm.HS256, JwtConstants.API_SECRET_KEY)
                .setIssuedAt(new Date(timestamp))
                .setExpiration(new Date(timestamp + JwtConstants.TOKEN_VALIDITY))
                .claim("userId", userId)
                .compact();
        return token;
    }
}
