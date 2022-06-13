package com.AEDsistema.relatorioMVC.service.prefabs;

import java.util.List;

import com.AEDsistema.relatorioMVC.dto.client.CadastrarCaminhaoDTO;
import com.AEDsistema.relatorioMVC.dto.client.EditarCaminhaoDTO;
import com.AEDsistema.relatorioMVC.dto.send.CaminhaoSendDTO;
import com.AEDsistema.relatorioMVC.error.prefabs.CaminhaoNaoExisteException;

public interface CaminhaoServices {
    public void cadastrarCaminhao(CadastrarCaminhaoDTO cadastrarRotasDTO);

    public List<CaminhaoSendDTO> pickAllCaminhoes();

    public void editarCaminhao(EditarCaminhaoDTO motoristaDTO) throws CaminhaoNaoExisteException;

    public void deleteCaminhao(Integer id);
}
