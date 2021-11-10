package com.example.SpringBoot_PetProject1_WedApplication.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// это класс содержащий конфигурацию веб-слоя
@Configuration
public class MvcConfig implements WebMvcConfigurer {
    public void addViewControllers(ViewControllerRegistry registry) {
/*Нам не нужно создавать свой контроллер, т.к. раздаются страницы, у которых шаблоны описаны и нет логики.
  Spring уже предоставляет систему авторизации, нам остается её только активизировать*/
        registry.addViewController("/login").setViewName("login");
    }
}
