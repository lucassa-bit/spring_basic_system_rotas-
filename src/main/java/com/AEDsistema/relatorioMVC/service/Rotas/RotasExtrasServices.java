package com.AEDsistema.relatorioMVC.service.Rotas;

import java.util.List;

import com.AEDsistema.relatorioMVC.dto.client.CadastrarRotasExtrasDTO;
import com.AEDsistema.relatorioMVC.dto.client.EditarRotasExtrasDTO;
import com.AEDsistema.relatorioMVC.dto.send.RotasExtrasSendDTO;
import com.AEDsistema.relatorioMVC.error.data.DataRangeException;
import com.AEDsistema.relatorioMVC.error.login.UsuarioCadastradoInvalidoException;
import com.AEDsistema.relatorioMVC.error.login.UsuarioNaoExisteException;
import com.AEDsistema.relatorioMVC.error.rota.RotaNaoExisteException;

public interface RotasExtrasServices {
    public void cadastrarRota(CadastrarRotasExtrasDTO cadastrarRotasDTO)
            throws UsuarioNaoExisteException, UsuarioCadastradoInvalidoException;

    public List<RotasExtrasSendDTO> pickAllRotas(String dataEmissao);
    public List<RotasExtrasSendDTO> pickAllRotasByDataRange(String dataEmissaoInicial, String dataEmissaoFinal) throws DataRangeException;

    public void editarRota(EditarRotasExtrasDTO editarRotasExtrasDTO) throws UsuarioCadastradoInvalidoException, RotaNaoExisteException;
    public void deleteRota(Integer id);
    public RotasExtrasSendDTO getRotasExtrasById(Integer id);
}
