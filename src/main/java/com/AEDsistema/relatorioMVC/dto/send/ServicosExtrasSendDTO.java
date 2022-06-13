package com.AEDsistema.relatorioMVC.dto.send;

import java.time.LocalDate;

public class ServicosExtrasSendDTO {
    private Integer id;

    private LocalDate dataEmissao;
    private String descricaoServico;

    private String destinoRota;
    private Double despesasRota;
    private Double valorRota;

    public ServicosExtrasSendDTO() {}

    public ServicosExtrasSendDTO(Integer id, LocalDate dataEmissao,
            String descricaoServico, String destinoRota, Double despesasRota, Double valorRota) {
        this.id = id;
        this.dataEmissao = dataEmissao;
        this.descricaoServico = descricaoServico;
        this.destinoRota = destinoRota;
        this.despesasRota = despesasRota;
        this.valorRota = valorRota;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(LocalDate dataEmissao) {
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
