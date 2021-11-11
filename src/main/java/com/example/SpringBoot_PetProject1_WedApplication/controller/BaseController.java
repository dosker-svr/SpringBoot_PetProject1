package com.example.SpringBoot_PetProject1_WedApplication.controller;

import com.example.SpringBoot_PetProject1_WedApplication.domain.Message;
import com.example.SpringBoot_PetProject1_WedApplication.domain.User;
import com.example.SpringBoot_PetProject1_WedApplication.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class BaseController {
    @Autowired
    private MessageRepository messageRepository;

    @GetMapping("/")
    public String getGreeting(Map<String, Object> model) {
        //model - это то, куда складываем данные, возвращаемые пользователю
        return "greeting";
    }

    @GetMapping("/base")
    public String baseBase(@RequestParam(required=false, defaultValue="") String filter,
                           Model model) { // узнать что такое 'model' ???
        // разобраться в MessageRepository
        Iterable<Message> messages = messageRepository.findAll();
        if (filter != null && !filter.isEmpty()) {
            messages = messageRepository.findByTag(filter);
        } else {
            messages = messageRepository.findAll();
        }
        model.addAttribute("messages", messages);
        model.addAttribute("filter", filter);
        return "base";
    }

/* Обрабатываем отправку формы в db :*/
    @PostMapping("/base")
    public String addMessage(@AuthenticationPrincipal User author,
                             @RequestParam String text,
                             @RequestParam String tag,
                             Map<String, Object> model) {
        Message message = new Message(text, tag, author);
        messageRepository.save(message);

        Iterable<Message> messages = messageRepository.findAll();
        model.put("messages", messages);

        return "base";
    }

/* Обрабатываем фильтрацию данных в db : */
    @PostMapping("filter")
    public String filter(@RequestParam String filter, Map<String, Object> model) {
        Iterable<Message> messages;
        if (filter != null && !filter.isEmpty()) {
            messages = messageRepository.findByTag(filter);
        } else {
            messages = messageRepository.findAll();
        }
        model.put("messages", messages);
        return "base";
    }
}
