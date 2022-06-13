package com.AEDsistema.relatorioMVC.dto.client;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

public class EditarServicosExtrasDTO {
    @NotNull(message = "Erro na edição da rota: Serviço extra não existe")
    private Integer id;
    @NotBlank(message = "Erro na edição da rota: Data de emissao não definida")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private String dataEmissao;

    @NotBlank(message = "Erro na edição da rota: Descrição do serviço está em branco")
    private String descricaoServico;

    @NotBlank(message = "Erro na edição da rota: Destino está em branco")
    private String destinoRota;
    @NotNull(message = "Erro na edição da rota: A despesa está em branco")
    @Range(min = 0, message = "Erro na edição da rota: Valor da despesa é menor que zero")
    private Double despesasRota;
    @NotNull(message = "Erro na edição da rota: O valor está em branco")
    @Range(min = 0, message = "Erro na criação da rota: Valor da rota é menor que zero")
    private Double valorRota;

    public EditarServicosExtrasDTO() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(String dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public String getDescricaoServico() {
        return descricaoServico;
    }

    public void setDescricaoServico(String descricaoServico) {
        this.descricaoServico = descricaoServico;
    }

    public String getDestinoRota() {
        return destinoRota;
    }

    public void setDestinoRota(String destinoRota) {
        this.destinoRota = destinoRota;
    }

    public Double getDespesasRota() {
        return despesasRota;
    }

    public void setDespesasRota(Double despesasRota) {
        this.despesasRota = despesasRota;
    }

    public Double getValorRota() {
        return valorRota;
    }

    public void setValorRota(Double valorRota) {
        this.valorRota = valorRota;
    }
}
