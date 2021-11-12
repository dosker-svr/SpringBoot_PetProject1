package com.example.SpringBoot_PetProject1_WedApplication.domain;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    USER, ADMIN;

    @Override
    public String getAuthority() {
        return name(); // name() - строковое представления значения "USER"
    }
}
