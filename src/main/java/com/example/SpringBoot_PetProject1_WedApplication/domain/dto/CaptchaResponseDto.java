package com.example.SpringBoot_PetProject1_WedApplication.domain.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Set;

/* этот класс - ответ сервера google на post-запрос с каптчей . ответ в формате json*/
@JsonIgnoreProperties(ignoreUnknown = true) /* говорим, что будем игнорировать неизвестные нам поля*/
public class CaptchaResponseDto {
    private boolean success;

    @JsonAlias("error-codes") /* т.к. java не поддерживает название переменных с дефисом ("error-codes")*/
    private Set<String> errorCodes;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Set<String> getErrorCodes() {
        return errorCodes;
    }

    public void setErrorCodes(Set<String> errorCodes) {
        this.errorCodes = errorCodes;
    }
}
