package com.AEDsistema.relatorioMVC.service.Rotas;

import java.util.List;

import com.AEDsistema.relatorioMVC.dto.client.CadastrarRotasDTO;
import com.AEDsistema.relatorioMVC.dto.client.EditarManifestoDTO;
import com.AEDsistema.relatorioMVC.dto.client.EditarRotasDTO;
import com.AEDsistema.relatorioMVC.dto.send.RotasSendDTO;
import com.AEDsistema.relatorioMVC.error.data.DataRangeException;
import com.AEDsistema.relatorioMVC.error.login.UsuarioCadastradoInvalidoException;
import com.AEDsistema.relatorioMVC.error.login.UsuarioNaoExisteException;
import com.AEDsistema.relatorioMVC.error.rota.RotaNaoExisteException;

public interface RotasServices {
    public void cadastrarRota(CadastrarRotasDTO cadastrarRotasDTO)
            throws UsuarioNaoExisteException, UsuarioCadastradoInvalidoException;

    public List<RotasSendDTO> pickAllRotas(String dataEmissao);
    public List<RotasSendDTO> pickAllNovasRotas(int idCriador);
    public List<RotasSendDTO> pickAllRotasAprovadasByData(String dataEmissaoInicial, String dataEmissaoFinal) throws DataRangeException;
    public List<RotasSendDTO> pickAllRotasByData(String dataEmissaoInicial, String dataEmissaoFinal) throws DataRangeException;
    public List<RotasSendDTO> pickAllRotasAprovadas(String dataEmissao);

    public void editarRota(EditarRotasDTO editarRotasDTO) throws UsuarioCadastradoInvalidoException, RotaNaoExisteException;
    public void deleteRota(Integer id);

    public RotasSendDTO getRotasById(Integer id);
    public List<RotasSendDTO> getRotasForResponsavel(Integer idUsuario, boolean isForRelatorio);
    public List<RotasSendDTO> getRotasForGerente(Integer idUsuario);

    public void editarManifesto(EditarManifestoDTO editarRotasExtrasDTO) throws RotaNaoExisteException;
    public void aprovar(Integer id) throws RotaNaoExisteException;
    public void naoAprovado(Integer id) throws RotaNaoExisteException;
}
