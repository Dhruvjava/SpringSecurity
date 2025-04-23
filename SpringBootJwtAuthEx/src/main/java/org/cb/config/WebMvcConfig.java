//package org.cb.config;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//@EnableWebMvc
//public class WebMvcConfig implements WebMvcConfigurer {
//
//    @Value("${dt.allowed.origins:*}")
//    private String allowedOrigins;
//
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        if (allowedOrigins.equals("'*'")) {
//            registry.addMapping("/**").allowedOrigins("*").allowedMethods("GET", "POST", "DELETE",
//                            "OPTIONS");
//            // .allowedMethods("GET");
//        } else {
//            registry.addMapping("/**").allowedOrigins(allowedOrigins.split(","))
//                            .allowedMethods("GET", "POST", "DELETE", "OPTIONS");
//        }
//    }
//}
