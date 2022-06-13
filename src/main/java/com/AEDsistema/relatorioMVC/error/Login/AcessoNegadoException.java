package com.AEDsistema.relatorioMVC.error.login;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN)
public class AcessoNegadoException extends Exception {
	private static final String ACESSO_NEGADO = "Acesso negado.";
	
	public AcessoNegadoException() {
		super(ACESSO_NEGADO);
	}
}
