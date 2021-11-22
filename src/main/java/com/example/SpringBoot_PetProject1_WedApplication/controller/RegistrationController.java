package com.example.SpringBoot_PetProject1_WedApplication.controller;

import com.example.SpringBoot_PetProject1_WedApplication.domain.User;
import com.example.SpringBoot_PetProject1_WedApplication.domain.dto.CaptchaResponseDto;
import com.example.SpringBoot_PetProject1_WedApplication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Map;

@Controller
public class RegistrationController {
    private final static String CAPTCHA_URL = "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s";

    @Autowired
    UserService userService;

    @Autowired
    private RestTemplate restTemplate;
    @Value("${recaptcha.secret}")
    private String secret; /* т.к. имеем ключ +овтет пользователя("g-recaptcha-response"),
    нам надо запросить сервер google (rest-запрос) с вопросом, прошёл ли валидацию польз.:
        -создать:
            --DTO-класс, получающий ответ от сервера google;
            --использовать RestTemplate (создаём в MvcConfig.java);
        -подготавливаем запрос для сервера google - 'urlForCaptchaResponse';
        -отправляем на сервер и получаем ответ в 'restTemplate.postForObject';
        -если каптча не пройдена(!isSuccess), добавляем в модель атриб. 'captchaError'
        -по желанию можно добавить Logger, чтобы сообщить что на сервере ошибка */

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addNewUser(
            @RequestParam("password2") String passwordConfirmation,
            @RequestParam("g-recaptcha-response") String captchaResp, /* клик польз. по чекбоксу каптчи */
            @Valid User user,
            BindingResult bindingResult,
            Model model) {
        String urlForCaptchaResponse = String.format(CAPTCHA_URL, secret, captchaResp);
        CaptchaResponseDto captchaRespFromGoogle = restTemplate.postForObject(urlForCaptchaResponse, Collections.emptyList(), CaptchaResponseDto.class);

        if (!captchaRespFromGoogle.isSuccess()) {
            model.addAttribute("captchaError", "FillCaptcha");
        }

        boolean isPassConfirm = StringUtils.isEmpty(passwordConfirmation);

        if (isPassConfirm) {
            model.addAttribute("password2Error", "Password confirmation cannot be empty");
        }

        if (user.getPassword() != null && !user.getPassword().equals(passwordConfirmation)) {
            model.addAttribute("passwordError", "Passwords are different");
        }

        if (isPassConfirm || bindingResult.hasErrors() || !captchaRespFromGoogle.isSuccess()) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errors);

            return "registration";
        }

        if(!userService.addNewUser(user)) {
            model.addAttribute("usernameError", "User exists!");
            return "registration";
        }

        return "redirect:/login";
    }

    @GetMapping("/activate/{code}")
    public String activate(Model model, @PathVariable String code) {
        boolean isActivated = userService.activateUser(code);
        if (isActivated) {
            model.addAttribute("messageType", "success");
            model.addAttribute("message", "User successfully activated");
        } else {
            model.addAttribute("messageType", "danger");
            model.addAttribute("message", "Activation code is not found");
        }
        return "login";
    }
}
