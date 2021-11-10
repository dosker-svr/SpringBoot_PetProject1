package com.example.SpringBoot_PetProject1_WedApplication.domain;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "usr")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String username;
    private String password;
    private boolean active;

/* @ElementCollection - Позволяет не хранить доп.таблицу для хранения enum
   fetch - параметр определяет, как значения enum будут подгружаться относительно остновной сущности (User)
   @CollectionTable - описывает, что данное поле будет храниться в отдельной таблице, для которой мы не описывали Mapping
   Она позволяет создать таблицу для набора Role, соединяющаяся с текущей таблицей с помощью 'joinColumns'*/
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
