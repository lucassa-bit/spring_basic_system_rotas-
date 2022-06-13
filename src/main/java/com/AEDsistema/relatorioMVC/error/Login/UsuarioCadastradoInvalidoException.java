package com.AEDsistema.relatorioMVC.error.login;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class UsuarioCadastradoInvalidoException extends Exception {
    private static final String ACESSO_NEGADO = "Usuario cadastrado não é um %s.";
	
	public UsuarioCadastradoInvalidoException(String usuario) {
		super(String.format(ACESSO_NEGADO, usuario));
	}
}
