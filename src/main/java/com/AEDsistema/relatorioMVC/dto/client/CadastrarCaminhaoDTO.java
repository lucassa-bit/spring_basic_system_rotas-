package com.AEDsistema.relatorioMVC.dto.client;

import javax.validation.constraints.NotBlank;

public class CadastrarCaminhaoDTO {
    @NotBlank(message = "Erro na criação de Caminhao: A placa está em branco")
    private String placa;

    public CadastrarCaminhaoDTO() {}

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    
}
