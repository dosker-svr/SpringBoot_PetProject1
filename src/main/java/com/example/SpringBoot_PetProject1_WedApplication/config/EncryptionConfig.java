package com.example.SpringBoot_PetProject1_WedApplication.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/* вынесли в отдельный класс из WebSecurityConfig.java*/
@Configuration
public class EncryptionConfig {
    /* создаем Бин т.к. Енкодер понадобится не только при процессе логина польз. */
    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder(8);
    }

}
