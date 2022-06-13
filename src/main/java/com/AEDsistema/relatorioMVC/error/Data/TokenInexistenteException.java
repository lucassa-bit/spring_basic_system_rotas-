package com.AEDsistema.relatorioMVC.error.data;

public class TokenInexistenteException extends Exception{	
	public TokenInexistenteException() {
		super(String.format("Token não está presente ou não existe"));
	}
}
