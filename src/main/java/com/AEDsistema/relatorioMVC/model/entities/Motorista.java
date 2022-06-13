package com.AEDsistema.relatorioMVC.model.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.AEDsistema.relatorioMVC.dto.client.CadastrarMotoristaDTO;
import com.AEDsistema.relatorioMVC.dto.client.EditarMotoristaDTO;
import com.AEDsistema.relatorioMVC.dto.send.MotoristaSendDTO;

@Entity
public class Motorista {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nomeMotorista;
    private String numeroCelularMotorista;
    private String cpfMotorista;
    private String pixMotorista;
    private String identidadeMotorista;

    public Motorista() {
    }

    public Motorista(String nomeMotorista, String numeroCelularMotorista, String cpfMotorista, String pixMotorista,
            String identidadeMotorista) {
        this.nomeMotorista = nomeMotorista;
        this.numeroCelularMotorista = numeroCelularMotorista;
        this.cpfMotorista = cpfMotorista;
        this.pixMotorista = pixMotorista;
        this.identidadeMotorista = identidadeMotorista;
    }

    public static Motorista fromCadastrarRotas(CadastrarMotoristaDTO cadastrarMotoristaDTO) {
        return new Motorista(cadastrarMotoristaDTO.getNomeMotorista(),
                cadastrarMotoristaDTO.getNumeroCelularMotorista(), cadastrarMotoristaDTO.getCpfMotorista(),
                cadastrarMotoristaDTO.getPixMotorista(), cadastrarMotoristaDTO.getIdentidadeMotorista());
    }

    public void fromEditarRotas(EditarMotoristaDTO editarMotoristaDTO) {
        setNomeMotorista(editarMotoristaDTO.getNomeMotorista());
        setNumeroCelularMotorista(editarMotoristaDTO.getNumeroCelularMotorista());
        setCpfMotorista(editarMotoristaDTO.getCpfMotorista());
        setPixMotorista(editarMotoristaDTO.getPixMotorista());
        setIdentidadeMotorista(editarMotoristaDTO.getIdentidadeMotorista());
    }

    public MotoristaSendDTO toMotoristaSend() {
        return new MotoristaSendDTO(id, nomeMotorista, numeroCelularMotorista, cpfMotorista, pixMotorista,
                identidadeMotorista);
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
