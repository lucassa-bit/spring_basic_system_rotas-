package com.AEDsistema.relatorioMVC.dto.send;

import java.time.LocalDate;
import java.time.LocalTime;

import com.AEDsistema.relatorioMVC.enumerators.TipoRota;

public class RotasSendDTO {
    private Integer id;
    private TipoRota tipoRota;
    private String numeroManifesto;
    private String numeroRomaneio;
    private LocalDate dataEmissao;
    private LocalDate dataChegada;
    private LocalTime horaInicio;
    private LocalTime horaFim;

    Integer idMotorista;
    private String nomeMotorista;
    private String numeroCelularMotorista;
    private String cpfMotorista;
    private String pixMotorista;
    private String identidadeMotorista;

    Integer idNumeroRota;
    private String codigoRota;
    private String destinoRota;
    private Double valorRota;
    private Double despesasRota;

    private Integer idCaminhao;
    private String placaCaminhao;

    private Integer idResponsavel;
    private String nomeResponsavel;
    private String contatoResponsavel;
    private String cpfResponsavel;
    private String pixResponsavel;
    private String identidadeResponsavel;

    private Integer idGerente;
    private String nomeGerente;
    private String contatoGerente;
    private String cpfGerente;
    private String pixGerente;
    private String identidadeGerente;

    private boolean isAprovado;

    public RotasSendDTO() {
    }

    public RotasSendDTO(Integer id, TipoRota tipoRota, String numeroManifesto, String numeroRomaneio,
            LocalDate dataEmissao, LocalDate dataChegada, LocalTime horaInicio, LocalTime horaFim, Integer idMotorista,
            String nomeMotorista,
            String numeroCelularMotorista, String cpfMotorista, String pixMotorista, String identidadeMotorista,
            Integer idNumeroRota, String codigoRota, String destinoRota, Double valorRota, Double despesasRota,
            Integer idCaminhao, String placaCaminhao, Integer idResponsavel, String nomeResponsavel,
            String contatoResponsavel, String cpfResponsavel, String pixResponsavel, String identidadeResponsavel,
            Integer idGerente, String nomeGerente, String contatoGerente, String cpfGerente, String pixGerente,
            String identidadeGerente, boolean isAprovado) {
        this.id = id;

        this.tipoRota = tipoRota;
        this.numeroManifesto = numeroManifesto;
        this.numeroRomaneio = numeroRomaneio;

        this.dataEmissao = dataEmissao;
        this.dataChegada = dataChegada;

        this.horaInicio = horaInicio;
        this.horaFim = horaFim;

        this.idMotorista = idMotorista;
        this.nomeMotorista = nomeMotorista;
        this.numeroCelularMotorista = numeroCelularMotorista;
        this.cpfMotorista = cpfMotorista;
        this.pixMotorista = pixMotorista;
        this.identidadeMotorista = identidadeMotorista;

        this.idNumeroRota = idNumeroRota;
        this.codigoRota = codigoRota;
        this.destinoRota = destinoRota;
        this.valorRota = valorRota;
        this.despesasRota = despesasRota;

        this.idCaminhao = idCaminhao;
        this.placaCaminhao = placaCaminhao;

        this.idResponsavel = idResponsavel;
        this.nomeResponsavel = nomeResponsavel;
        this.contatoResponsavel = contatoResponsavel;
        this.cpfResponsavel = cpfResponsavel;
        this.pixResponsavel = pixResponsavel;
        this.identidadeResponsavel = identidadeResponsavel;

        this.idGerente = idGerente;
        this.nomeGerente = nomeGerente;
        this.contatoGerente = contatoGerente;
        this.cpfGerente = cpfGerente;
        this.pixGerente = pixGerente;
        this.identidadeGerente = identidadeGerente;

        this.isAprovado = isAprovado;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalTime getHoraFim() {
        return horaFim;
    }

    public void setHoraFim(LocalTime horaFim) {
        this.horaFim = horaFim;
    }

    public String getPixMotorista() {
        return pixMotorista;
    }

    public void setPixMotorista(String pixMotorista) {
        this.pixMotorista = pixMotorista;
    }

    public String getIdentidadeMotorista() {
        return identidadeMotorista;
    }

    public void setIdentidadeMotorista(String identidadeMotorista) {
        this.identidadeMotorista = identidadeMotorista;
    }

    public String getPixResponsavel() {
        return pixResponsavel;
    }

    public void setPixResponsavel(String pixResponsavel) {
        this.pixResponsavel = pixResponsavel;
    }

    public String getIdentidadeResponsavel() {
        return identidadeResponsavel;
    }

    public void setIdentidadeResponsavel(String identidadeResponsavel) {
        this.identidadeResponsavel = identidadeResponsavel;
    }

    public String getPixGerente() {
        return pixGerente;
    }

    public void setPixGerente(String pixGerente) {
        this.pixGerente = pixGerente;
    }

    public String getIdentidadeGerente() {
        return identidadeGerente;
    }

    public void setIdentidadeGerente(String identidadeGerente) {
        this.identidadeGerente = identidadeGerente;
    }

    public String getCpfResponsavel() {
        return cpfResponsavel;
    }

    public void setCpfResponsavel(String cpfResponsavel) {
        this.cpfResponsavel = cpfResponsavel;
    }

    public String getCpfGerente() {
        return cpfGerente;
    }

    public void setCpfGerente(String cpfGerente) {
        this.cpfGerente = cpfGerente;
    }

    public String getCpfMotorista() {
        return cpfMotorista;
    }

    public void setCpfMotorista(String cpfMotorista) {
        this.cpfMotorista = cpfMotorista;
    }

    public LocalDate getDataChegada() {
        return dataChegada;
    }

    public void setDataChegada(LocalDate dataChegada) {
        this.dataChegada = dataChegada;
    }

    public Integer getIdMotorista() {
        return idMotorista;
    }

    public void setIdMotorista(Integer idMotorista) {
        this.idMotorista = idMotorista;
    }

    public Integer getIdNumeroRota() {
        return idNumeroRota;
    }

    public void setIdNumeroRota(Integer idNumeroRota) {
        this.idNumeroRota = idNumeroRota;
    }

    public Integer getId() {
        return id;
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

    public LocalDate getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(LocalDate dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public String getNomeMotorista() {
        return nomeMotorista;
    }

    public void setNomeMotorista(String nomeMotorista) {
        this.nomeMotorista = nomeMotorista;
    }

    public String getNumeroCelularMotorista() {
        return numeroCelularMotorista;
    }

    public void setNumeroCelularMotorista(String numeroCelularMotorista) {
        this.numeroCelularMotorista = numeroCelularMotorista;
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

    public boolean isAprovado() {
        return isAprovado;
    }

    public void setAprovado(boolean isAprovado) {
        this.isAprovado = isAprovado;
    }

    public TipoRota getTipoRota() {
        return tipoRota;
    }

    public void setTipoRota(TipoRota tipoRota) {
        this.tipoRota = tipoRota;
    }

    public Integer getIdCaminhao() {
        return idCaminhao;
    }

    public void setIdCaminhao(Integer idCaminhao) {
        this.idCaminhao = idCaminhao;
    }

    public String getPlacaCaminhao() {
        return placaCaminhao;
    }

    public void setPlacaCaminhao(String placaCaminhao) {
        this.placaCaminhao = placaCaminhao;
    }

    public Integer getIdResponsavel() {
        return idResponsavel;
    }

    public void setIdResponsavel(Integer idResponsavel) {
        this.idResponsavel = idResponsavel;
    }

    public String getNomeResponsavel() {
        return nomeResponsavel;
    }

    public void setNomeResponsavel(String nomeResponsavel) {
        this.nomeResponsavel = nomeResponsavel;
    }

    public String getContatoResponsavel() {
        return contatoResponsavel;
    }

    public void setContatoResponsavel(String contatoResponsavel) {
        this.contatoResponsavel = contatoResponsavel;
    }

    public Integer getIdGerente() {
        return idGerente;
    }

    public void setIdGerente(Integer idGerente) {
        this.idGerente = idGerente;
    }

    public String getNomeGerente() {
        return nomeGerente;
    }

    public void setNomeGerente(String nomeGerente) {
        this.nomeGerente = nomeGerente;
    }

    public String getContatoGerente() {
        return contatoGerente;
    }

    public void setContatoGerente(String contatoGerente) {
        this.contatoGerente = contatoGerente;
    }

    public String getNumeroRomaneio() {
        return numeroRomaneio;
    }

    public void setNumeroRomaneio(String numeroRomaneio) {
        this.numeroRomaneio = numeroRomaneio;
    }
}
