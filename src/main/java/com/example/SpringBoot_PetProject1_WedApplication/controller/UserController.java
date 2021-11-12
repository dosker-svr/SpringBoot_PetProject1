package com.example.SpringBoot_PetProject1_WedApplication.controller;

import com.example.SpringBoot_PetProject1_WedApplication.domain.Role;
import com.example.SpringBoot_PetProject1_WedApplication.domain.User;
import com.example.SpringBoot_PetProject1_WedApplication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
@PreAuthorize("hasAuthority('ADMIN')") /* эта аннотац. для каждого метода в контроллере
будет проверять наличие у залогиненного пользователя его Role (на ADMIN)*/
public class UserController {
    @Autowired
    private UserRepository userRepository;

/* Маппинг на список пользователей (страница со списком зарегистрированных User)*/
    @GetMapping
    public String userList(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "userList";
    }

/* Форма редактирования пользователя*/
    @GetMapping("{user}") // {user} - это id User'а
    public String userEditForm(@PathVariable User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "userEdit";
    }

/* Метод для сохранения изменений в userEdit*/
    @PostMapping
    public String userSave(/* чтобы сохранить новые данные пользователя, нам необходимо получить некоторые данные с сервера:
Map<String, String> form - это мапа из полей в форме userEditor.ftlh (<form action="/user" method="post">),
т.е. там username, #list roles, userId, _csrf.
у User переменное кол-во Role (т.е. чекбокс Role можно выбрать у неск. полей)*/
            @RequestParam String username,
            @RequestParam Map<String, String> form,
            @RequestParam("userId") User user,
            Model model) {
        user.setUsername(username);

/* Перед тем как обновлять роли, нам нужно получить список Role'й, чтобы проверить, что они установлены данному пользователю:*/
        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());
/* Очищаем у пользователя Set с Roles, чтобы положить туда добавленные вновь*/
        user.getRoles().clear();

/* Теперь првоеряем, какие у 'Map form' содержатся Role пользователя в #list roles */
        for (String key: form.keySet()) {
            if (roles.contains(key)) {
                user.getRoles().add(Role.valueOf(key));
            }
        }


        userRepository.save(user);
        return "redirect:/user";
    }
}
