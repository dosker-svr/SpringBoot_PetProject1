package com.example.SpringBoot_PetProject1_WedApplication;

import com.example.SpringBoot_PetProject1_WedApplication.domain.Message;
import com.example.SpringBoot_PetProject1_WedApplication.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
public class GreetingController {
    @Autowired
    private MessageRepository messageRepository;

    @GetMapping("/greeting")
    public String getGreeting(@RequestParam(required=false, defaultValue="WRLD") String name,
                              Map<String, Object> model) { //model - это то, куда складываем данные, возвращаемые пользователю
        model.put("name", name);
        return "greeting";
    }

    @GetMapping
    public String baseBase(Map<String, Object> model) {
        // разобраться в этом мапинге:
        Iterable<Message> messages = messageRepository.findAll();
        model.put("messages", messages);
        return "base";
    }

    @PostMapping
    public String addMessage(@RequestParam String text,
                             @RequestParam String tag,
                             Map<String, Object> model) {
        Message message = new Message(text, tag);
        messageRepository.save(message);

        Iterable<Message> messages = messageRepository.findAll();
        model.put("messages", messages);

        return "base";
    }

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
