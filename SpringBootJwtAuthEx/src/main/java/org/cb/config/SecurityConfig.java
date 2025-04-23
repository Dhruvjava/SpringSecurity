package org.cb.config;

import lombok.extern.slf4j.Slf4j;
import org.cb.filter.SecurityFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Collections;

@Configuration
@EnableWebSecurity
@Slf4j
@EnableMethodSecurity
public class SecurityConfig {

    //    @Autowired
    //    FilterChainExceptionHandler exceptionHandler;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private InvalidUserAuthenticationEntryPoint entryPoint;

    @Autowired
    private SecurityFilter filter;

    @Autowired
    private CorsConfigure corsConfig;

    @Bean
    public AuthenticationManager manager(AuthenticationConfiguration builder) throws Exception {
        return builder.getAuthenticationManager();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(encoder);
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }

    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        corsConfig.configure(http);
        http.cors(cors -> {
            CorsConfigurationSource source = s -> {
                CorsConfiguration config = new CorsConfiguration();
                config.setAllowCredentials(true);
                //                config.setAllowedOrigins(Collections.singletonList("http://localhost:5012"));
                config.setAllowedOrigins(Collections.singletonList("*"));
                config.setAllowedHeaders(Collections.singletonList("*"));
                config.setAllowedMethods(Collections.singletonList("*"));
                return config;
            };
            cors.configurationSource(source);
        });
        http.csrf(AbstractHttpConfigurer::disable)/*.anonymous(AbstractHttpConfigurer::disable)*/.authorizeHttpRequests(
                                        request -> request.requestMatchers("/api/user/**")
                                                        .permitAll()/*.requestMatchers("/api/user/save").permitAll()*/)
                        .authorizeHttpRequests(request -> request.anyRequest().authenticated())
                        .exceptionHandling(
                                        exception -> exception.authenticationEntryPoint(entryPoint))
                        .sessionManagement(session -> session.sessionCreationPolicy(
                                        SessionCreationPolicy.STATELESS))
                        .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
        //                        .addFilterAfter(exceptionHandler, LogoutFilter.class);
        return http.build();
    }

    @Bean
    WebSecurityCustomizer webSecurityCustomizer() {
        log.info("ingnoring the security");
        return web -> web.ignoring()
                        .requestMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**",
                                        "/actuator/**").requestMatchers("/error/**");
    }

}
