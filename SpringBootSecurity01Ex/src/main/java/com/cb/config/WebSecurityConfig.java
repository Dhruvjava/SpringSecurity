package com.cb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    // 1. Authentication

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user =
                User.withDefaultPasswordEncoder()
                        .username("dhruvjava")
                        .password("admin")
                        .roles("ADMIN")
                        .build();
        UserDetails user1 =
                User.withDefaultPasswordEncoder()
                        .username("sam")
                        .password("sam")
                        .roles("ADMIN")
                        .build();

        return new InMemoryUserDetailsManager(user, user1);
    }

    // 2. Authorizarion

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(
                        request -> request.requestMatchers("/", "home").permitAll().anyRequest()
                                .authenticated()

                ).formLogin(form -> form.loginPage("/login").permitAll())
                .logout(LogoutConfigurer::permitAll);
        return http.build();
    }

}
