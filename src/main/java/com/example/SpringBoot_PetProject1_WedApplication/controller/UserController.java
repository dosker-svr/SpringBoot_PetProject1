package com.example.SpringBoot_PetProject1_WedApplication.controller;

import com.example.SpringBoot_PetProject1_WedApplication.domain.Role;
import com.example.SpringBoot_PetProject1_WedApplication.domain.User;
import com.example.SpringBoot_PetProject1_WedApplication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

/* Маппинг на список пользователей (страница со списком зарегистрированных User)*/
    @PreAuthorize("hasAuthority('ADMIN')") /* эта аннотац. будет проверять наличие у залогиненного пользователя
его Role (на ADMIN)*/
    @GetMapping
    public String userList(Model model) {
        model.addAttribute("users", userService.findAllUsers());
        return "userList";
    }

/* Форма редактирования пользователя*/
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("{user}") // {user} - это id User'а
    public String userEditForm(@PathVariable User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "userEdit";
    }

/* Метод для сохранения изменений в userEdit*/
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public String userSave(/* чтобы сохранить новые данные пользователя, нам необходимо получить некоторые данные с сервера:
Map<String, String> form - это мапа из полей в форме userEditor.ftlh (<form action="/user" method="post">),
т.е. там username, #list roles, userId, _csrf.
у User переменное кол-во Role (т.е. чекбокс Role можно выбрать у неск. полей)*/
            @RequestParam String username,
            @RequestParam Map<String, String> form,
            @RequestParam("userId") User user) {

        userService.saveUser(user, username, form);
        return "redirect:/user";
    }

/* для просмотра данных пользователя: */
    @GetMapping("profile")
    public String getProfile(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("username", user.getUsername());
        model.addAttribute("email", user.getEmail());
        return "profile";
    }

/* для изменения данных пользователя: */
    @PostMapping("profile")
    public String updateProfile(@AuthenticationPrincipal User user,
                                @RequestParam String password,
                                @RequestParam String email) {
        userService.updateProfile(user, password, email);
        return "redirect:/user/profile";
    }
}
