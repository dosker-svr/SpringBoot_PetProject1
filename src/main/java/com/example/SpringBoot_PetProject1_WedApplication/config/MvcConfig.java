package com.example.SpringBoot_PetProject1_WedApplication.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// это класс содержащий конфигурацию веб-слоя
@Configuration
public class MvcConfig implements WebMvcConfigurer {
/* раздавать/отображать загруженные файлы (MultipartFile file): */
    @Value("${upload.path}")
    private String uploadPath;

    @Bean /* для валидации каптчи*/
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    public void addViewControllers(ViewControllerRegistry registry) {
/*Нам не нужно создавать свой контроллер, т.к. раздаются страницы, у которых шаблоны описаны и нет логики.
  Spring уже предоставляет систему авторизации, нам остается её только активизировать*/
        registry.addViewController("/login").setViewName("login");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/img/**")/*каждое обращение к серверу по этому пути
        с данными типа icon и тд будет перенаправлять все запросы сюда :::::          */
                .addResourceLocations("file:/" + uploadPath + "/"); // "file://" - указывает, что протокол файл(место в файловой системе)
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/"); /* classpath - означает что ресурсы будут искаться не в файловой системе(file:/),
                                    а в дереве проекта */
    }
}
