package com.jwt.auth.demo.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jwt.auth.demo.model.Person;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class AuthService {

    @Autowired
    private PersonService personService;

    private final String SECRET_KEY = "your_secret_key";

    // Gera o token JWT para o usuário
    public String generateToken(Person person) {
        return Jwts.builder()
            .setSubject(person.getUsername())
            .claim("role", person.getPersonType().name())
            .setIssuedAt(new Date()) // Data de criação
            .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // Expira em 10 horas
            .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
            .compact();
    }

    // Valida o token JWT e retorna a pessoa correspondente
    public Person validateToken(String token) {
        Claims claims = Jwts.parser()
            .setSigningKey(SECRET_KEY)
            .parseClaimsJws(token)
            .getBody();

        String username = claims.getSubject();
        return personService.findByUsername(username);
    }

    // Realiza o login verificando username e senha
    public Person login(String username, String password) {
        return personService.findByUsernameAndPassword(username, password);
    }
}
