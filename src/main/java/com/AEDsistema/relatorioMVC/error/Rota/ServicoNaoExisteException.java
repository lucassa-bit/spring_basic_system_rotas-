package com.AEDsistema.relatorioMVC.error.rota;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class ServicoNaoExisteException extends Exception {
    private static final String ACESSO_NEGADO = "Servico que está sendo editado não existe %s";
	
	public ServicoNaoExisteException(String documento) {
		super(String.format(ACESSO_NEGADO, documento));
	}
}
