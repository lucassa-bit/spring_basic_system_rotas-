package com.AEDsistema.relatorioMVC.dto.client;

import javax.validation.constraints.NotNull;

public class EditarAprovarRotaDTO {
    @NotNull(message = "Erro na edição da rota: rota não existe")
    private Integer id;
    @NotNull(message = "Erro na edição da rota: valor de aprovação inválido")
    private Boolean isAprovado;
    @NotNull(message = "Erro na aprovação")
    private Boolean isOkayToAprove;
    
    
    public EditarAprovarRotaDTO() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getIsAprovado() {
        return isAprovado;
    }

    public void setIsAprovado(Boolean isAprovado) {
        this.isAprovado = isAprovado;
    }

    public Boolean getIsOkayToAprove() {
        return isOkayToAprove;
    }

    public void setIsOkayToAprove(Boolean isOkayToAprove) {
        this.isOkayToAprove = isOkayToAprove;
    }
}
