package com.AEDsistema.relatorioMVC.dto.client;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

public class EditarNumeroRotaDTO {
    @NotNull(message = "Erro na edição da motorista: motorista não existe")
    private Integer id;
    @NotBlank(message = "Erro na edição da rota: Código da rota está em branco")
    private String codigoRota;
    @NotBlank(message = "Erro na edição da rota: Destino em branco")
    private String destinoRota;
    @Range(min = 0, message = "Erro na edição da rota: valor da rota menor é que zero")
    private Double valorRota;
    @Range(min = 0, message = "Erro na edição da rota: valor de despesa da rota menor é que zero")
    private Double despesasRota;

    public EditarNumeroRotaDTO() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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
