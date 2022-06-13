package com.AEDsistema.relatorioMVC.service.Rotas;

import java.util.List;

import com.AEDsistema.relatorioMVC.dto.client.CadastrarServicosExtrasDTO;
import com.AEDsistema.relatorioMVC.dto.client.EditarServicosExtrasDTO;
import com.AEDsistema.relatorioMVC.dto.send.ServicosExtrasSendDTO;
import com.AEDsistema.relatorioMVC.error.data.DataRangeException;
import com.AEDsistema.relatorioMVC.error.login.UsuarioCadastradoInvalidoException;
import com.AEDsistema.relatorioMVC.error.login.UsuarioNaoExisteException;
import com.AEDsistema.relatorioMVC.error.rota.ServicoNaoExisteException;

public interface ServicosExtrasServices {
    public void cadastrarServicoExtra(CadastrarServicosExtrasDTO cadastrarRotasDTO) throws UsuarioNaoExisteException;

    public List<ServicosExtrasSendDTO> pickAllServicosExtras(String dataEmissao);
    public List<ServicosExtrasSendDTO> pickAllServicosExtrasByRangeData(String dataEmissaoInicial, String dataEmissaoFinal) throws DataRangeException;

    public void editarServicoExtra(EditarServicosExtrasDTO editarRotasExtrasDTO)
            throws UsuarioCadastradoInvalidoException, ServicoNaoExisteException;

    public void deleteServicoExtra(Integer id);
    public ServicosExtrasSendDTO getServicosExtrasById(Integer id);
}
