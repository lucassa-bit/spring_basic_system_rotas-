package com.AEDsistema.relatorioMVC.service.Login;

import java.util.List;

import com.AEDsistema.relatorioMVC.dto.client.CadastrarUsuarioDTO;
import com.AEDsistema.relatorioMVC.dto.client.EditarUsuarioDTO;
import com.AEDsistema.relatorioMVC.dto.send.UsuarioSendDTO;
import com.AEDsistema.relatorioMVC.error.login.EncodingPasswordException;
import com.AEDsistema.relatorioMVC.error.login.LoginAlreadyExistsException;
import com.AEDsistema.relatorioMVC.error.login.UsuarioNaoExisteException;
import com.AEDsistema.relatorioMVC.model.entities.Usuario;

import org.springframework.security.core.context.SecurityContextHolder;

public interface UsuarioServices {
    public List<UsuarioSendDTO> pickAllUsers();
    public List<UsuarioSendDTO> pickAllResponsaveisInterno();
    public void createNewUsuario(CadastrarUsuarioDTO dto) throws LoginAlreadyExistsException, EncodingPasswordException;
    public void editarUsuario(EditarUsuarioDTO editarUsuarioDTO) throws UsuarioNaoExisteException;
    public UsuarioSendDTO getUsuarioById(Integer id);
    public UsuarioSendDTO getUsuarioByLogin(String login) throws UsuarioNaoExisteException;
    public void deleteUsuario(int id);
    
    public static Usuario authenticated() {
        try {
            return (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            return null;
        }
    }
}
