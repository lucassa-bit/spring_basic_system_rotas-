package com.AEDsistema.relatorioMVC.dto.client;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class EditarMotoristaDTO {
    @NotNull(message = "Erro na edição da motorista: motorista não existe")
    private Integer id;
    @NotBlank(message = "Erro na edição do motorista: Nome do motorista em branco")
    private String nomeMotorista;
    private String cpfMotorista;
    private String numeroCelularMotorista;
    private String identidadeMotorista;
    private String pixMotorista;

    public EditarMotoristaDTO() {
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCpfMotorista() {
        return cpfMotorista;
    }

    public void setCpfMotorista(String cpfMotorista) {
        this.cpfMotorista = cpfMotorista;
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

}
