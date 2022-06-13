package com.AEDsistema.relatorioMVC.dto.client;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

public class CadastrarRotasDTO {
    @NotBlank(message = "Erro na criação da rota: Data de emissao não definida")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private String dataEmissao;

    private Integer idMotorista;
    @NotBlank(message = "Erro na criação da rota: Nome do motorista em branco")
    private String nomeMotorista;
    private String cpfMotorista;
    private String identidadeMotorista;
    private String numeroCelularMotorista;
    private String pixMotorista;

    private Integer idCaminhao;
    @NotBlank(message = "Erro na criação da rota: Placa do caminhao em branco")
    private String placaCaminhao;

    private Integer idNumeroRota;
    @NotBlank(message = "Erro na criação da rota: Código da rota está em branco")
    private String codigoRota;
    @NotBlank(message = "Erro na criação da rota: Destino em branco")
    private String destinoRota;
    @NotNull(message = "Erro na criação da rota: Valor da rota está vázio")
    @Range(min = 0, message = "Erro na criação da rota: valor da rota menor que zero")
    private Double valorRota;
    @Range(min = 0, message = "Erro na criação da rota: valor da despesa é menor que zero")
    private Double despesasRota;

    @NotNull(message = "Erro na criação da rota: Responsável interno não existe")
    private Integer gerenteExterno;
    @NotNull(message = "Erro na criação da rota: Gerente externo não existe")
    private Integer responsavelInterno;
    private String numeroRomaneio;

    public CadastrarRotasDTO() {
    }

    public String getIdentidadeMotorista() {
        return identidadeMotorista;
    }

    public void setIdentidadeMotorista(String identidadeMotorista) {
        this.identidadeMotorista = identidadeMotorista;
    }

    public String getPixMotorista() {
        return pixMotorista;
    }

    public void setPixMotorista(String pixMotorista) {
        this.pixMotorista = pixMotorista;
    }

    public Integer getIdMotorista() {
        return idMotorista;
    }

    public void setIdMotorista(Integer idMotorista) {
        this.idMotorista = idMotorista;
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

    public String getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(String dataEmissao) {
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

    public Integer getGerenteExterno() {
        return gerenteExterno;
    }

    public void setGerenteExterno(Integer gerenteExterno) {
        this.gerenteExterno = gerenteExterno;
    }

    public Integer getResponsavelInterno() {
        return responsavelInterno;
    }

    public void setResponsavelInterno(Integer responsavelInterno) {
        this.responsavelInterno = responsavelInterno;
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

    public String getNumeroRomaneio() {
        return numeroRomaneio;
    }

    public void setNumeroRomaneio(String numeroRomaneio) {
        this.numeroRomaneio = numeroRomaneio;
    }
}
