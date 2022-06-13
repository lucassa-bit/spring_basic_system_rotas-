package com.AEDsistema.relatorioMVC.service.Login;

import java.util.List;

import com.AEDsistema.relatorioMVC.dto.send.UsuarioSendDTO;
import com.AEDsistema.relatorioMVC.enumerators.TipoUsuario;
import com.AEDsistema.relatorioMVC.error.login.AcessoNegadoException;
import com.AEDsistema.relatorioMVC.error.login.UsuarioNaoExisteException;

public interface VerificadorPermissoesService {
	public UsuarioSendDTO verificarPermissao(String loginUsuario, List<TipoUsuario> permitidos) throws UsuarioNaoExisteException, AcessoNegadoException;
	public boolean existeUsuarioByLogin(String login);

}
