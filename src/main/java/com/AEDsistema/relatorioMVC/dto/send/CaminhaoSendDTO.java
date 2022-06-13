package com.AEDsistema.relatorioMVC.dto.send;

public class CaminhaoSendDTO {
    private Integer id;
    private String placa;

    public CaminhaoSendDTO(Integer id, String placa) {
        this.id = id;
        this.placa = placa;
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
