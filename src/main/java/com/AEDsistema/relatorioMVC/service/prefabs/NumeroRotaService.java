package com.AEDsistema.relatorioMVC.service.prefabs;

import java.util.List;

import com.AEDsistema.relatorioMVC.dto.client.CadastrarNumeroRotaDTO;
import com.AEDsistema.relatorioMVC.dto.client.EditarNumeroRotaDTO;
import com.AEDsistema.relatorioMVC.dto.send.NumeroRotaSendDTO;
import com.AEDsistema.relatorioMVC.error.prefabs.NumeroRotaNaoExisteException;

public interface NumeroRotaService {
    public void cadastrarNumeroRota(CadastrarNumeroRotaDTO cadastrarNumeroRotaDTO);

    public List<NumeroRotaSendDTO> pickAllNumeroRotas();

    public void editarNumeroRota(EditarNumeroRotaDTO motoristaDTO) throws NumeroRotaNaoExisteException;

    public void deleteNumeroRota(Integer id);
}
