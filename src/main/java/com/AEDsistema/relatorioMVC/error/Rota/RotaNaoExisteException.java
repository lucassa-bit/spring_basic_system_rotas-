package com.AEDsistema.relatorioMVC.error.rota;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class RotaNaoExisteException extends Exception {
    public static final String ROTA_NAO_EXISTE = "Rota com destino %s não existe.";
	
	public RotaNaoExisteException(String rota) {
		super(String.format(ROTA_NAO_EXISTE, rota));
	}
}
