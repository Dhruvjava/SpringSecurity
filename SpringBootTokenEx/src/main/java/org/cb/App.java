package org.cb;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * JWT Token api implementation
 */
public class App {
    public static void main(String[] args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String secret = "D#r^v";
        UserRs user = new UserRs(101,"Dhruvjava", "Dhruv@gmail.com", "DT11184", "9149175183");
        String userstr = mapper.writeValueAsString(user);
        String token = Jwts.builder().setId("iwuryrwi638").setSubject("Dhruv")
                        .setIssuedAt(new Date(System.currentTimeMillis())).setExpiration(new Date(
                                        System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(2)))
                        .setIssuer("DT").signWith(SignatureAlgorithm.HS256, secret.getBytes())
                        .claim("user", userstr)
                        .compact();

        System.out.println("Token : " + token);
        Claims claims = Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token)
                        .getBody();
        String userdt = claims.get("user", String.class);

        System.out.println(userdt);

        UserRs rs = mapper.readValue(userdt, UserRs.class);


        System.out.println("ID : "+claims.getId());
        System.out.println("Issuer : "+claims.getIssuer());
        System.out.println("Subject : "+claims.getSubject());
        System.out.println("Issue At : "+claims.getIssuedAt());
        System.out.println("Expiration : "+claims.getExpiration());
        System.out.println("user id : "+rs.getId());
        System.out.println("user name : "+rs.getUsername());
        System.out.println("user email : "+rs.getEmail());
        System.out.println("user emp id : "+rs.getEmpId());
        System.out.println("user mobile : "+rs.getMobile());
    }
}
