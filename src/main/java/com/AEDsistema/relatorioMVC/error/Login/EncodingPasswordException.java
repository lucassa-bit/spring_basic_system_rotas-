package com.AEDsistema.relatorioMVC.error.login;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class EncodingPasswordException extends Exception {

    public EncodingPasswordException(String string) {
        super(string);
    }
}
