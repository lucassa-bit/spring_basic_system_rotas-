package com.AEDsistema.relatorioMVC.dto.client;

import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

public class CadastrarNumeroRotaDTO {
    @NotBlank(message = "Erro na criação da rota: Código da rota está em branco")
    private String codigoRota;
    @NotBlank(message = "Erro na criação da rota: Destino em branco")
    private String destinoRota;
    @Range(min = 0, message = "Erro na criação da rota: valor da rota é menor que zero")
    private Double valorRota;
    @Range(min = 0, message = "Erro na criação da rota: valor de despesa da rota é menor que zero")
    private Double despesasRota;

    public CadastrarNumeroRotaDTO() {}

    public String getCodigoRota() {
        return codigoRota;
    }

    public void setCodigoRota(String codigoRota) {
        this.codigoRota = codigoRota;
    }

    public String getDestinoRota() {
        return destinoRota;
    }

    public void setDestinoRota(String destinoRota) {
        this.destinoRota = destinoRota;
    }

    public Double getValorRota() {
        return valorRota;
    }

    public void setValorRota(Double valorRota) {
        this.valorRota = valorRota;
    }

    public Double getDespesasRota() {
        return despesasRota;
    }

    public void setDespesasRota(Double despesasRota) {
        this.despesasRota = despesasRota;
    }

    
}
