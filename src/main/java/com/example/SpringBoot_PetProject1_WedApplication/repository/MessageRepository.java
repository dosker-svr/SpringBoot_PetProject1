package com.example.SpringBoot_PetProject1_WedApplication.repository;

import com.example.SpringBoot_PetProject1_WedApplication.domain.Message;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/* это будет автоматически имплементированно Spring'ом в Bean
   с названием 'messageRepository', естественно если использовать в контроллере @Autowired */
// этот репозиторий позволяет найти: полный список полей или по идентификатору
public interface MessageRepository extends CrudRepository<Message, Long> {
// прочитать инструкцию перед использованием :
//https://docs.spring.io/spring-data/jpa/docs/1.5.0.RELEASE/reference/html/jpa.repositories.html#jpa.query-methods.query-creation
    List<Message> findByTag(String tag);
}
