package com.AEDsistema.relatorioMVC.dto.send;

public class NumeroRotaSendDTO {
    private Integer id;

    private String codigoRota;
    private String destinoRota;
    private Double valorRota;
    private Double despesasRota;

    public NumeroRotaSendDTO() {
    }

    public NumeroRotaSendDTO(Integer id, String codigoRota, String destinoRota, Double valorRota, Double despesasRota) {
        this.id = id;
        this.codigoRota = codigoRota;
        this.destinoRota = destinoRota;
        this.valorRota = valorRota;
        this.despesasRota = despesasRota;
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
