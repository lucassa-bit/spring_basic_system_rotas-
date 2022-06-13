package com.AEDsistema.relatorioMVC.error.prefabs;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class MotoristaNaoExisteException extends Exception {
    public static final String ROTA_NAO_EXISTE = "Motorista com nome %s não existe.";
	
	public MotoristaNaoExisteException(String rota) {
		super(String.format(ROTA_NAO_EXISTE, rota));
	}
}
