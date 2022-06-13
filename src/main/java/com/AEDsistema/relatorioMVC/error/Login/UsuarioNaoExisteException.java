package com.AEDsistema.relatorioMVC.error.login;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class UsuarioNaoExisteException extends Exception {
	public static final String USUARIO_NAO_EXISTE = "Usuario %s não existe.";
	
	public UsuarioNaoExisteException(String usuario) {
		super(String.format(USUARIO_NAO_EXISTE, usuario));
	}
}
