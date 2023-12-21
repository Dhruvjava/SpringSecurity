package org.cb;

import io.jsonwebtoken.Claims;
import org.cb.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class SpringBootJwtAuthExApplication implements CommandLineRunner {

    @Autowired
    JwtUtils jwt;

    public static void main(String[] args) {
        SpringApplication.run(SpringBootJwtAuthExApplication.class, args);

    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    public void run(String... args) throws Exception {
        String token = jwt.generateToken("Dhruv Kumar");
        System.out.println(token);
        Claims claims = jwt.getClaims(token);
        System.out.println(claims.getIssuedAt());
        System.out.println(claims.get("subject"));
        System.out.println(claims.get("issuer"));
        System.out.println(claims.getSubject());
    }
}
