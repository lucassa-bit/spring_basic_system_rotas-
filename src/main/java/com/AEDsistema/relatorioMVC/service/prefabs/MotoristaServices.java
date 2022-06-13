package com.AEDsistema.relatorioMVC.service.prefabs;

import java.util.List;

import com.AEDsistema.relatorioMVC.dto.client.CadastrarMotoristaDTO;
import com.AEDsistema.relatorioMVC.dto.client.EditarMotoristaDTO;
import com.AEDsistema.relatorioMVC.dto.send.MotoristaSendDTO;
import com.AEDsistema.relatorioMVC.error.prefabs.MotoristaNaoExisteException;

public interface MotoristaServices {
    public void cadastrarMotorista(CadastrarMotoristaDTO cadastrarRotasDTO);

    public List<MotoristaSendDTO> pickAllMotoristas();

    public void editarMotorista(EditarMotoristaDTO motoristaDTO) throws MotoristaNaoExisteException;

    public void deleteMotorista(Integer id);
}
