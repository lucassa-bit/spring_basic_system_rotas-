package com.AEDsistema.relatorioMVC.dto.send;

public class MotoristaSendDTO {
    private Integer id;

    private String nomeMotorista;
    private String numeroCelularMotorista;
    private String cpfMotorista;
    private String pixMotorista;
    private String identidadeMotorista;

    public MotoristaSendDTO() {
    }

    public MotoristaSendDTO(Integer id, String nomeMotorista, String numeroCelularMotorista, String cpfMotorista,
            String pixMotorista, String identidadeMotorista) {
        this.id = id;
        this.nomeMotorista = nomeMotorista;
        this.numeroCelularMotorista = numeroCelularMotorista;
        this.cpfMotorista = cpfMotorista;
        this.pixMotorista = pixMotorista;
        this.identidadeMotorista = identidadeMotorista;
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
