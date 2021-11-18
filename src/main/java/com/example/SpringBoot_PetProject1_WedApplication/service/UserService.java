package com.example.SpringBoot_PetProject1_WedApplication.service;

import com.example.SpringBoot_PetProject1_WedApplication.domain.Role;
import com.example.SpringBoot_PetProject1_WedApplication.domain.User;
import com.example.SpringBoot_PetProject1_WedApplication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.UUID;

// узнать как работает и для чего нужен ???
@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MailSenderService mailSenderService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    public boolean addNewUser(User user) {
        User userFromDb = userRepository.findByUsername(user.getUsername());
        if (userFromDb != null) {
            return false;
        }

        user.setActive(false);
        user.setRoles(Collections.singleton(Role.USER));
        user.setActivationCode(UUID.randomUUID().toString());
        userRepository.save(user);

/* если у user есть почта, отправляем ему оповещение для подтверждения почты:*/
        if (!StringUtils.isEmpty(user.getEmail())) {
            String message = String.format("Hi, %s! \n" +
                    "Welcome to WebApp, Please, visit next link: http://localhost:8080/activate/%s",
/* при реальной разработке линк выносим в .properties*/
                    user.getUsername(),
                    user.getActivationCode()
            );

            mailSenderService.send(user.getEmail(), "Activation code", message);
        }
        return true;
    }

    public boolean activateUser(String code) {
        User user = userRepository.findByActivationCode(code);
        if (user == null) {
            return false;
        }
        user.setActive(true);
        user.setActivationCode(null);
        userRepository.save(user);
        return true;
    }
}
