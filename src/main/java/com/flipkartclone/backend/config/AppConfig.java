package com.flipkartclone.backend.config;

//package com.flipkartclonebackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration   // Spring ko batata hai ki ye ek configuration class hai
public class AppConfig {

    @Bean   // Spring ke ApplicationContext me ek BCryptPasswordEncoder bean register karega
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
