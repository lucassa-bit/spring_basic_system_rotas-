package com.AEDsistema.relatorioMVC.model.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.AEDsistema.relatorioMVC.dto.client.CadastrarCaminhaoDTO;
import com.AEDsistema.relatorioMVC.dto.client.EditarCaminhaoDTO;
import com.AEDsistema.relatorioMVC.dto.send.CaminhaoSendDTO;

@Entity
public class Caminhao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String placa;

    public Caminhao() {}

    public Caminhao(String placa) {
        this.placa = placa;
    }

    public static Caminhao fromCadastrarCaminhao(CadastrarCaminhaoDTO cadastrarCaminhao) {
        return new Caminhao(cadastrarCaminhao.getPlaca());
    }

    public void fromEditarCaminhao(EditarCaminhaoDTO caminhaoDTO) {
        caminhaoDTO.setPlaca(caminhaoDTO.getPlaca());
    }

    public CaminhaoSendDTO toCaminhaoSendDTO() {
        return new CaminhaoSendDTO(id, placa);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

}
