package com.cb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Date;

@SpringBootApplication
public class SpringBootSecurityJdbcAuthExApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootSecurityJdbcAuthExApplication.class, args);
//		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//		String code = encoder.encode("ram");
//		System.out.println(code);
	}

}
