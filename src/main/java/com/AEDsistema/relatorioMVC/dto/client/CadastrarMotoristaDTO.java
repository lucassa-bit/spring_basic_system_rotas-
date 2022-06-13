package com.AEDsistema.relatorioMVC.dto.client;

import javax.validation.constraints.NotBlank;

public class CadastrarMotoristaDTO {
    @NotBlank(message = "Erro na criação do motorista: Nome do motorista em branco")
    private String nomeMotorista;
    private String cpfMotorista;
    private String identidadeMotorista;
    private String numeroCelularMotorista;
    private String pixMotorista;

    public CadastrarMotoristaDTO() {
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

    public String getNomeMotorista() {
        return nomeMotorista;
    }

    public void setNomeMotorista(String nomeMotorista) {
        this.nomeMotorista = nomeMotorista;
    }

    public String getCpfMotorista() {
        return cpfMotorista;
    }

    public void setCpfMotorista(String cpfMotorista) {
        this.cpfMotorista = cpfMotorista;
    }

    public String getNumeroCelularMotorista() {
        return numeroCelularMotorista;
    }

    public void setNumeroCelularMotorista(String numeroCelularMotorista) {
        this.numeroCelularMotorista = numeroCelularMotorista;
    }

}
