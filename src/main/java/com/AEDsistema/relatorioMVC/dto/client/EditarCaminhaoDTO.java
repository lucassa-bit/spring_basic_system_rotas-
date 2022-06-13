package com.AEDsistema.relatorioMVC.dto.client;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class EditarCaminhaoDTO {
    @NotNull(message = "Erro na edição do caminhão: caminhão não existe")
    private Integer id;
    @NotBlank(message = "Erro na edição de Caminhao: A placa está em branco")
    private String placa;

    public EditarCaminhaoDTO() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }
}
