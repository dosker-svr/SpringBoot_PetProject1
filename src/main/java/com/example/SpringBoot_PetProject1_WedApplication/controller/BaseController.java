package com.example.SpringBoot_PetProject1_WedApplication.controller;

import com.example.SpringBoot_PetProject1_WedApplication.domain.Message;
import com.example.SpringBoot_PetProject1_WedApplication.domain.User;
import com.example.SpringBoot_PetProject1_WedApplication.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Controller
public class BaseController {
    @Autowired
    private MessageRepository messageRepository;

    @Value("${upload.path}")
    private String uploadPath;

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

/* Обрабатываем фильтрацию данных в db : */
        if (filter != null && !filter.isEmpty()) {
            messages = messageRepository.findByTag(filter);
        } else {
            messages = messageRepository.findAll();
        }

        model.addAttribute("messages", messages);
        model.addAttribute("filter", filter);
        return "base";
    }

/* Обрабатываем отправку формы Добавления сообщения в db :*/
    @PostMapping("/base")
    public String addMessage(@AuthenticationPrincipal User author, /* получение user из контекста (чтобы не получать его из db)
                                                                      WebSecurityConfig */
                             @Valid Message message,
                             BindingResult bindingResult, /*список аргументов и ошибок валидации
                             (обязательно должен идти перед Model, иначе все ошибки будут во view)*/
                             Model model,
                             @RequestParam("file")MultipartFile file) throws IOException {
        message.setAuthor(author);

        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errorMap); /* при ошибках они попадут во view. нужно отоброзать во view ("/base")*/
            model.addAttribute("message", message);
        } else {
/* проверка существования файла, если он существет, то добавляем его в экземпляр сообщения +
   + проверка существования имени файла */
            if (file != null && !file.getOriginalFilename().isEmpty()) {
                File uploadFolder = new File(uploadPath);
                if (!uploadFolder.exists()) {
                    uploadFolder.mkdir(); // создаём директорию если её нет
                }

                String uuidFile = UUID.randomUUID().toString(); // обезапасим себя от кализий и создаём уникальное имя файла
                String resultFileName = uuidFile + "." + file.getOriginalFilename();

                file.transferTo(new File(uploadFolder + "/" + resultFileName)); // загружаем файл
                message.setFilename(resultFileName);
            }

            model.addAttribute("message", null); /* для того чтобы в веденных полях
            после добавления сообщения эти поля очищались  */

            messageRepository.save(message);
        }
        Iterable<Message> messages = messageRepository.findAll();
        model.addAttribute("messages", messages);

        return "base";
    }
}
