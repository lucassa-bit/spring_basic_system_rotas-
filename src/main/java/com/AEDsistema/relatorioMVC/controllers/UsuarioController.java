package com.AEDsistema.relatorioMVC.controllers;

import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import com.AEDsistema.relatorioMVC.dto.client.CadastrarUsuarioDTO;
import com.AEDsistema.relatorioMVC.dto.client.EditarUsuarioDTO;
import com.AEDsistema.relatorioMVC.dto.send.UsuarioSendDTO;
import com.AEDsistema.relatorioMVC.enumerators.TipoUsuario;
import com.AEDsistema.relatorioMVC.error.login.AcessoNegadoException;
import com.AEDsistema.relatorioMVC.error.login.EncodingPasswordException;
import com.AEDsistema.relatorioMVC.error.login.LoginAlreadyExistsException;
import com.AEDsistema.relatorioMVC.error.login.UsuarioNaoExisteException;
import com.AEDsistema.relatorioMVC.service.Login.UsuarioServices;
import com.AEDsistema.relatorioMVC.service.Login.VerificadorPermissoesService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {
    private final UsuarioServices uServices;
    private final VerificadorPermissoesService verificadorPermissoesService;

    private final String CADASTRO_USUARIO_REALIZADO = "Cadastro do usuario realizado com sucesso!";
    private final String EDICAO_USUARIO_REALIZADO = "Edicao de usuario realizado com sucesso!";
    private final String DELETE_USUARIO_REALIZADO = "Delete de usuario realizado com sucesso!";

    @Autowired
    public UsuarioController(UsuarioServices uServices, VerificadorPermissoesService verificadorPermissoesService, ApplicationEventPublisher eventPublisher) {
        this.uServices = uServices;
        this.verificadorPermissoesService = verificadorPermissoesService;
    }

    // Criar novo usuario
    @PostMapping("/registrar")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<String> createNewUsuario(Authentication authentication,
            @Valid @RequestBody CadastrarUsuarioDTO usuarioDTO)
            throws LoginAlreadyExistsException, EncodingPasswordException, UsuarioNaoExisteException,
            AcessoNegadoException {
                List<TipoUsuario> cargosPermitidos = Arrays.asList(new TipoUsuario[] { TipoUsuario.ADMIN });
                verificadorPermissoesService.verificarPermissao(authentication.getName(), cargosPermitidos);
        if (usuarioDTO.getSenha() == null)
            throw new EncodingPasswordException("Senha possui valor nulo");

        uServices.createNewUsuario(usuarioDTO);

        return new ResponseEntity<String>(CADASTRO_USUARIO_REALIZADO, HttpStatus.CREATED);
    }

    // pegar todos os usuarios
    @GetMapping
    public List<UsuarioSendDTO> getAllUsuarios(Authentication authentication)
            throws UsuarioNaoExisteException, AcessoNegadoException {
        List<TipoUsuario> cargosPermitidos = Arrays.asList(new TipoUsuario[] { TipoUsuario.ADMIN });
        verificadorPermissoesService.verificarPermissao(authentication.getName(), cargosPermitidos);

        return uServices.pickAllUsers();
    }

    @GetMapping("/responsaveis")
    public List<UsuarioSendDTO> getAllResponsaveis(Authentication authentication)
            throws UsuarioNaoExisteException, AcessoNegadoException {
        List<TipoUsuario> cargosPermitidos = Arrays.asList(new TipoUsuario[] { TipoUsuario.ADMIN, TipoUsuario.GERENTE_EXTERNO });
        verificadorPermissoesService.verificarPermissao(authentication.getName(), cargosPermitidos);

        return uServices.pickAllResponsaveisInterno();
    }

    // pegar usuario por id
    @GetMapping("/find")
    public UsuarioSendDTO getUsuarioById(Authentication authentication, @RequestParam int id)
            throws UsuarioNaoExisteException, AcessoNegadoException {
        List<TipoUsuario> cargosPermitidos = Arrays.asList(new TipoUsuario[] { TipoUsuario.ADMIN });
        verificadorPermissoesService.verificarPermissao(authentication.getName(), cargosPermitidos);

        return uServices.getUsuarioById(id);
    }

    // editar usuario
    @PutMapping("/edit")
    public ResponseEntity<String> editUsuario(Authentication authentication,
            @Valid @RequestBody EditarUsuarioDTO usuarioDTO) throws UsuarioNaoExisteException, AcessoNegadoException {
        List<TipoUsuario> cargosPermitidos = Arrays.asList(new TipoUsuario[] { TipoUsuario.ADMIN });
        verificadorPermissoesService.verificarPermissao(authentication.getName(), cargosPermitidos);

        uServices.editarUsuario(usuarioDTO);

        return new ResponseEntity<String>(EDICAO_USUARIO_REALIZADO, HttpStatus.CREATED);
    }

    // retornar usuario logado
    @GetMapping("/me")
    public UsuarioSendDTO getUsuarioLogado(Authentication authentication)
            throws UsuarioNaoExisteException, AcessoNegadoException {
        List<TipoUsuario> cargosPermitidos = Arrays
                .asList(new TipoUsuario[] { TipoUsuario.ADMIN, TipoUsuario.GERENTE_EXTERNO, TipoUsuario.RESPONSAVEL_INTERNO });
        verificadorPermissoesService.verificarPermissao(authentication.getName(), cargosPermitidos);

        return uServices.getUsuarioByLogin(authentication.getName());
    }

    // deletar usuario
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUsuario(Authentication authentication,
            @RequestParam Integer id) throws UsuarioNaoExisteException, AcessoNegadoException {
        List<TipoUsuario> cargosPermitidos = Arrays
                .asList(new TipoUsuario[] { TipoUsuario.ADMIN });
        verificadorPermissoesService.verificarPermissao(authentication.getName(), cargosPermitidos);

        uServices.deleteUsuario(id);

        return new ResponseEntity<String>(DELETE_USUARIO_REALIZADO, HttpStatus.OK);
    }
}
