package com.cb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(DataSource dataSource) {
        var user1 = User.withUsername("sam").password("$2a$10$ngPdbt2EgbGZCQLqZWotSuLnG2Gpay.DrOlyifkM3PLW1y7JpVGUq").authorities("ADMIN").build();
        var user2 = User.withUsername("ram").password("$2a$10$sv8fVUy7uzFqXYQs6n822u6yZGa/FxSJSdryPiQUGBpPJyTgCJlk.").authorities("CUSTOMER").build();
        JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
        users.createUser(user1);
        users.createUser(user2);
        return users;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(request -> request.requestMatchers("/home", "/").permitAll()
                        .requestMatchers("/admin").hasAuthority("ADMIN")
                        .requestMatchers("/customer").hasAuthority("CUSTOMER")
                        .requestMatchers("/hello").authenticated()
                        .anyRequest().authenticated()

                )
                .formLogin(form -> form.loginPage("/login")
                        .defaultSuccessUrl("/hello", true).permitAll())
                .logout(LogoutConfigurer::permitAll);
        return http.build();
    }
}
