package com.AEDsistema.relatorioMVC.service.Login;
import java.util.List;

import com.AEDsistema.relatorioMVC.dto.send.UsuarioSendDTO;
import com.AEDsistema.relatorioMVC.enumerators.TipoUsuario;
import com.AEDsistema.relatorioMVC.error.login.AcessoNegadoException;
import com.AEDsistema.relatorioMVC.error.login.UsuarioNaoExisteException;
import com.AEDsistema.relatorioMVC.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VerificadorPermissoesServiceImpl implements VerificadorPermissoesService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	@Autowired
	private UsuarioServices usuarioServices;

	@Override
	public UsuarioSendDTO verificarPermissao(String loginUsuario, List<TipoUsuario> permitidos) throws UsuarioNaoExisteException, AcessoNegadoException {
		
		
		UsuarioSendDTO usuario = usuarioServices.getUsuarioByLogin(loginUsuario);
		
		if (!permitidos.contains(usuario.getCargo())) throw new AcessoNegadoException();
		
		return usuario;
	}
	
	@Override
	public boolean existeUsuarioByLogin(String login) {
		return usuarioRepository.existsByLoginIgnoreCaseOrderByNomeAsc(login);
	}
}
