package com.AEDsistema.relatorioMVC.model.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.AEDsistema.relatorioMVC.dto.client.CadastrarNumeroRotaDTO;
import com.AEDsistema.relatorioMVC.dto.client.EditarNumeroRotaDTO;
import com.AEDsistema.relatorioMVC.dto.send.NumeroRotaSendDTO;

@Entity
public class NumeroRota {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String codigoRota;
    private String destinoRota;
    private Double valorRota;
    private Double despesasRota;


    public NumeroRota() {
    }

    public NumeroRota(String codigoRota, String destinoRota, Double valorRota, Double despesasRota) {
        this.codigoRota = codigoRota;
        this.destinoRota = destinoRota;
        this.valorRota = valorRota;
        this.despesasRota = despesasRota;
    }

    public static NumeroRota fromCadastrarRotas(CadastrarNumeroRotaDTO cadastrarNumeroRotaDTO) {
        return new NumeroRota(
                cadastrarNumeroRotaDTO.getCodigoRota(), cadastrarNumeroRotaDTO.getDestinoRota(),
                cadastrarNumeroRotaDTO.getValorRota(),
                cadastrarNumeroRotaDTO.getDespesasRota());
    }

    public void fromEditarRotas(EditarNumeroRotaDTO numeroRotaSendDTO) {

        this.setCodigoRota(numeroRotaSendDTO.getCodigoRota());
        this.setDestinoRota(numeroRotaSendDTO.getDestinoRota());
        this.setValorRota(numeroRotaSendDTO.getValorRota());
        this.setDespesasRota(numeroRotaSendDTO.getDespesasRota());
    }

    public NumeroRotaSendDTO toRotasSend() {
        return new NumeroRotaSendDTO(id, codigoRota, destinoRota, valorRota, despesasRota);
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
