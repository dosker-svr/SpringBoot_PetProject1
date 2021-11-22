package com.example.SpringBoot_PetProject1_WedApplication.config;

import com.example.SpringBoot_PetProject1_WedApplication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import javax.sql.DataSource;

/* Это класс, который при старте приложения конфигурирует WebSecurity, далее приложение заходит в метод configure*/
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) /* чтобы в UserController работало вот это '@PreAuthorize("hasAuthority('ADMIN')")'*/
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    /*@Autowired
    private DataSource dataSource; // dataSource нужен чтобы менеджер мог входит в базу данных и искать User +Role */
    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

/* создаем Бин т.к. Енкодер понадобится не только при процессе логина польз. */
    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder(8);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests() //включаем авторизацию
                    .antMatchers("/", "/registration", "/static/**", "/activate/*").permitAll() /* указываем, что для этого пути "/" и
                     "/registration" для клиента разрешаем полный доступ*/
                    .anyRequest().authenticated() // для всех остальных запросов - требуем авторизацию
                .and()
                    .formLogin() // включаем форму "login"
                    .loginPage("/login")  // указываем где находится 'loginPage'
                    .permitAll()
                .and()
                    .rememberMe() /* авто-авторизация - Spring будет искать где-то(полученным от пользоват. идентификатрам,куках),
                     настройки польз. и попытает его авторизовать,
                     НО если выполняется перезапуск нашего App или запущено несколько серверов с нашим App,
                     то информац. о сессии будет потеряна.
                     значит её нужо где-то хранить (в нашем случае будем хранить в db - SpringSession)*/
                .and()
                    .logout()
                    .permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService)
                /* используем вместо dataSource, чтобы входить в DB и искать User +Role, и получать эту инфу в контроллере ("/base" @AuthenticationPrincipal)*/
                .passwordEncoder(passwordEncoder); /* тут используется при проверки пароля пользователя при логине*/
    }









    /*
    // этот метод приложение берёт по требованию
    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        UserDetails user = User.withDefaultPasswordEncoder() // помечен как 'deprecated', т.к. нужен только для отладки
                .username("u")
                .password("1")
                .roles("USER")
                .build();
        // ничего ни шифрует, ничего не хранит, при каждом перезапуске приложения, он создаёт пользователя

        return new InMemoryUserDetailsManager(user); /* этот метод создаёт менеджер, который обслуживает учётные записи */
    /*}*/

/*
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource) // dataSource нужен чтобы менеджер мог входит в базу данных и искать User +Role
                .passwordEncoder(NoOpPasswordEncoder.getInstance()) // шифрование паролей
                .usersByUsernameQuery(
                        "select username, password, active from usr where username=?"
                ) // необходимо, чтобы приложение нашло User по его имени
                .authoritiesByUsernameQuery(
                        "select u.username, ur.roles from usr u inner join user_role ur on u.id = ur.user_id where u.username=?"
                ); /* помогает Spring получить список User's с их Role's
                 "выбрать 'username+roles'
                         из таблицы 'usr' и присоеденной к ней таблице 'user_role' через поля 'id' и 'user_id'"
    }
*/
}
