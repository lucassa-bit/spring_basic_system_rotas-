package com.AEDsistema.relatorioMVC.error.login;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN)
public class LoginAlreadyExistsException extends Exception {
    public static final String LOGIN_EXISTENTE = "Login %s Já existe";

    public LoginAlreadyExistsException(String string) {
        super(String.format(LOGIN_EXISTENTE, string));
    }
}
