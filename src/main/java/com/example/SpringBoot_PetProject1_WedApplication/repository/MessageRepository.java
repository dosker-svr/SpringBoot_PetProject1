package com.example.SpringBoot_PetProject1_WedApplication.repository;

import com.example.SpringBoot_PetProject1_WedApplication.domain.Message;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MessageRepository extends CrudRepository<Message, Long> {
    List<Message> findByTag(String tag);
}
