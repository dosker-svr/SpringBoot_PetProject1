package com.example.SpringBoot_PetProject1_WedApplication.repository;

import com.example.SpringBoot_PetProject1_WedApplication.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

// этот репозиторий находит User по 'username'
public interface UserRepository extends JpaRepository<User, Long> {
// прочитать инструкцию перед использованием :
//https://docs.spring.io/spring-data/jpa/docs/1.5.0.RELEASE/reference/html/jpa.repositories.html#jpa.query-methods.query-creation
    User findByUsername(String username);

    User findByActivationCode(String code);
}
