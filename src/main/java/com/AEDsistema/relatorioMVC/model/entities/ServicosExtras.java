package com.AEDsistema.relatorioMVC.model.entities;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.AEDsistema.relatorioMVC.dto.client.CadastrarServicosExtrasDTO;
import com.AEDsistema.relatorioMVC.dto.client.EditarServicosExtrasDTO;
import com.AEDsistema.relatorioMVC.dto.send.ServicosExtrasSendDTO;

@Entity
public class ServicosExtras {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDate dataEmissao;
    private String descricaoServico;

    private String destinoRota;
    private Double valorRota;
    private Double despesasRota;

    public ServicosExtras() {
    }

    public ServicosExtras(LocalDate dataEmissao,
            String descricaoServico, String destinoRota, Double despesasRota, Double valorRota) {
        this.dataEmissao = dataEmissao;
        this.descricaoServico = descricaoServico;
        this.destinoRota = destinoRota;
        this.valorRota = valorRota;
        this.despesasRota = despesasRota;
    }

    public static ServicosExtras fromCadastrarServicosExtras(CadastrarServicosExtrasDTO cadastrarServicosExtrasDTO) {
        return new ServicosExtras(
                LocalDate.parse(cadastrarServicosExtrasDTO.getDataEmissao(), DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                cadastrarServicosExtrasDTO.getDescricaoServico(), cadastrarServicosExtrasDTO.getDestinoRota(),
                cadastrarServicosExtrasDTO.getDespesasRota(), cadastrarServicosExtrasDTO.getValorRota());
    }

    public void fromEditarServicosExtras(EditarServicosExtrasDTO editarServicosExtrasDTO) {
        setDataEmissao(
                LocalDate.parse(editarServicosExtrasDTO.getDataEmissao(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        setDescricaoServico(editarServicosExtrasDTO.getDescricaoServico());
        setDestinoRota(editarServicosExtrasDTO.getDestinoRota());
        setDespesasRota(editarServicosExtrasDTO.getDespesasRota());
        setValorRota(editarServicosExtrasDTO.getValorRota());
    }

    public ServicosExtrasSendDTO toServicosExtrasSend() {
        return new ServicosExtrasSendDTO(id, dataEmissao, descricaoServico,
                destinoRota, despesasRota, valorRota);
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
