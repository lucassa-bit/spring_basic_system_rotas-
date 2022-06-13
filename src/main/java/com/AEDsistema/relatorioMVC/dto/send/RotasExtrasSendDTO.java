package com.AEDsistema.relatorioMVC.dto.send;

import java.time.LocalDate;

import com.AEDsistema.relatorioMVC.enumerators.TipoRota;

public class RotasExtrasSendDTO {
    private Integer id;
    private TipoRota tipoRota;
    private String numeroManifesto;
    private String numeroRomaneio;
    private LocalDate dataEmissao;
    private LocalDate dataChegada;

    private Integer idCaminhao;
    private String placaCaminhao;

    private Integer idMotorista;
    private String nomeMotorista;
    private String numeroCelularMotorista;
    private String cpfMotorista;
    private String pixMotorista;
    private String identidadeMotorista;

    private Integer idNumeroRota;
    private String codigoRota;
    private String destinoRota;
    private Double valorRota;
    private Double despesasRota;

    private Integer idAdministrador;
    private String nomeAdministrador;
    private String contatoAdministrador;
    private String cpfAdministrador;
    private String pixAdministrador;
    private String identidadeAdministrador;

    public RotasExtrasSendDTO() {
    }


    
    public RotasExtrasSendDTO(Integer id, TipoRota tipoRota, String numeroManifesto, String numeroRomaneio,
            LocalDate dataEmissao, LocalDate dataChegada, Integer idCaminhao, String placaCaminhao, Integer idMotorista,
            String nomeMotorista, String numeroCelularMotorista, String cpfMotorista, String pixMotorista,
            String identidadeMotorista, Integer idNumeroRota, String codigoRota, String destinoRota, Double valorRota,
            Double despesasRota, Integer idAdministrador, String nomeAdministrador, String contatoAdministrador,
            String cpfAdministrador, String pixAdministrador, String identidadeAdministrador) {
        this.id = id;
        this.tipoRota = tipoRota;
        this.numeroManifesto = numeroManifesto;
        this.numeroRomaneio = numeroRomaneio;

        this.dataEmissao = dataEmissao;
        this.dataChegada = dataChegada;

        this.idCaminhao = idCaminhao;
        this.placaCaminhao = placaCaminhao;
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

        this.idAdministrador = idAdministrador;
        this.nomeAdministrador = nomeAdministrador;
        this.contatoAdministrador = contatoAdministrador;
        this.cpfAdministrador = cpfAdministrador;
        this.pixAdministrador = pixAdministrador;
        this.identidadeAdministrador = identidadeAdministrador;
    }



    public String getPixAdministrador() {
        return pixAdministrador;
    }

    public void setPixAdministrador(String pixAdministrador) {
        this.pixAdministrador = pixAdministrador;
    }

    public String getIdentidadeAdministrador() {
        return identidadeAdministrador;
    }

    public void setIdentidadeAdministrador(String identidadeAdministrador) {
        this.identidadeAdministrador = identidadeAdministrador;
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

    public String getCpfAdministrador() {
        return cpfAdministrador;
    }

    public void setCpfAdministrador(String cpfAdministrador) {
        this.cpfAdministrador = cpfAdministrador;
    }

    public String getCpfMotorista() {
        return cpfMotorista;
    }

    public void setCpfMotorista(String cpfMotorista) {
        this.cpfMotorista = cpfMotorista;
    }

    public Integer getIdNumeroRota() {
        return idNumeroRota;
    }

    public void setIdNumeroRota(Integer idNumeroRota) {
        this.idNumeroRota = idNumeroRota;
    }

    public Integer getIdMotorista() {
        return idMotorista;
    }

    public void setIdMotorista(Integer idMotorista) {
        this.idMotorista = idMotorista;
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

    public TipoRota getTipoRota() {
        return tipoRota;
    }

    public void setTipoRota(TipoRota tipoRota) {
        this.tipoRota = tipoRota;
    }

    public LocalDate getDataChegada() {
        return dataChegada;
    }

    public void setDataChegada(LocalDate dataChegada) {
        this.dataChegada = dataChegada;
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

    public Integer getIdAdministrador() {
        return idAdministrador;
    }

    public void setIdAdministrador(Integer idAdministrador) {
        this.idAdministrador = idAdministrador;
    }

    public String getNomeAdministrador() {
        return nomeAdministrador;
    }

    public void setNomeAdministrador(String nomeAdministrador) {
        this.nomeAdministrador = nomeAdministrador;
    }

    public String getContatoAdministrador() {
        return contatoAdministrador;
    }

    public void setContatoAdministrador(String contatoAdministrador) {
        this.contatoAdministrador = contatoAdministrador;
    }

    public String getNumeroRomaneio() {
        return numeroRomaneio;
    }

    public void setNumeroRomaneio(String numeroRomaneio) {
        this.numeroRomaneio = numeroRomaneio;
    }
}
