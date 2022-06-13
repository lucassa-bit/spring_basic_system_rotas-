package com.AEDsistema.relatorioMVC.dto.client;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

public class EditarManifestoDTO {
    @NotNull(message = "Erro na edição da rota: rota não existe")
    private Integer id;
    @NotNull(message = "Erro na edição da rota: número do manifesto está vázio")
    private String numeroManifesto;
    @NotNull(message = "Erro na edição da rota: Data de chegada não definida")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private String dataChegada;

    @NotNull(message = "Erro na edição da rota: hora de inicio não definida")
    @DateTimeFormat(pattern = "HH:mm:ss")
    private String horaInicio;
    @NotNull(message = "Erro na edição da rota: hora de fim não definida")
    @DateTimeFormat(pattern = "HH:mm:ss")
    private String horaFim;

    public EditarManifestoDTO() {
    }

    public Integer getId() {
        return id;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getHoraFim() {
        return horaFim;
    }

    public void setHoraFim(String horaFim) {
        this.horaFim = horaFim;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumeroManifesto() {
        return numeroManifesto;
    }

    public void setNumeroManifesto(String numeroManifesto) {
        this.numeroManifesto = numeroManifesto;
    }

    public String getDataChegada() {
        return dataChegada;
    }

    public void setDataChegada(String dataChegada) {
        this.dataChegada = dataChegada;
    }
}
