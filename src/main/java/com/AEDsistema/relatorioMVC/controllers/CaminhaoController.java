package com.AEDsistema.relatorioMVC.controllers;

import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import com.AEDsistema.relatorioMVC.dto.client.CadastrarCaminhaoDTO;
import com.AEDsistema.relatorioMVC.dto.client.EditarCaminhaoDTO;
import com.AEDsistema.relatorioMVC.dto.send.CaminhaoSendDTO;
import com.AEDsistema.relatorioMVC.enumerators.TipoUsuario;
import com.AEDsistema.relatorioMVC.error.login.AcessoNegadoException;
import com.AEDsistema.relatorioMVC.error.login.UsuarioCadastradoInvalidoException;
import com.AEDsistema.relatorioMVC.error.login.UsuarioNaoExisteException;
import com.AEDsistema.relatorioMVC.error.prefabs.CaminhaoNaoExisteException;
import com.AEDsistema.relatorioMVC.service.Login.VerificadorPermissoesService;
import com.AEDsistema.relatorioMVC.service.prefabs.CaminhaoServices;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/caminhao")
public class CaminhaoController {
    private final CaminhaoServices cServices;
    private final VerificadorPermissoesService verificadorPermissoesService;

    private final String CADASTRO_ROTA_REALIZADO = "Cadastro do caminhao realizado com sucesso!";
    private final String EDICAO_ROTA_REALIZADO = "Edicao do caminhao realizado com sucesso!";
    private final String DELETE_ROTA_REALIZADO = "Delete do caminhao realizado com sucesso!";

    @Autowired
    public CaminhaoController(CaminhaoServices cServices,
            VerificadorPermissoesService verificadorPermissoesService) {
        this.cServices = cServices;
        this.verificadorPermissoesService = verificadorPermissoesService;
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<String> createNewCaminhao(Authentication authentication,
            @Valid @RequestBody CadastrarCaminhaoDTO cadastrarCaminhaoDTO)
            throws AcessoNegadoException, UsuarioNaoExisteException, UsuarioCadastradoInvalidoException {
        List<TipoUsuario> cargosPermitidos = Arrays
                .asList(new TipoUsuario[] { TipoUsuario.GERENTE_EXTERNO, TipoUsuario.ADMIN });
        verificadorPermissoesService.verificarPermissao(authentication.getName(), cargosPermitidos);

        cServices.cadastrarCaminhao(cadastrarCaminhaoDTO);

        return new ResponseEntity<String>(CADASTRO_ROTA_REALIZADO, HttpStatus.CREATED);
    }

    @PutMapping("/edit")
    public ResponseEntity<String> editCaminhao(Authentication authentication,
            @Valid @RequestBody EditarCaminhaoDTO editarCaminhaoDTO) throws UsuarioNaoExisteException,
            AcessoNegadoException, CaminhaoNaoExisteException {
        List<TipoUsuario> cargosPermitidos = Arrays.asList(new TipoUsuario[] { TipoUsuario.GERENTE_EXTERNO, TipoUsuario.ADMIN });
        verificadorPermissoesService.verificarPermissao(authentication.getName(), cargosPermitidos);

        cServices.editarCaminhao(editarCaminhaoDTO);

        return new ResponseEntity<String>(EDICAO_ROTA_REALIZADO, HttpStatus.CREATED);
    }

    @GetMapping
    public List<CaminhaoSendDTO> getAllCaminhoes(Authentication authentication)
            throws UsuarioNaoExisteException, AcessoNegadoException {
        List<TipoUsuario> cargosPermitidos = Arrays.asList(new TipoUsuario[] { TipoUsuario.GERENTE_EXTERNO, TipoUsuario.ADMIN  });
        verificadorPermissoesService.verificarPermissao(authentication.getName(), cargosPermitidos);

        return cServices.pickAllCaminhoes();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteCaminhao(Authentication authentication,
            @RequestParam Integer id) throws UsuarioNaoExisteException, AcessoNegadoException {
        List<TipoUsuario> cargosPermitidos = Arrays
                .asList(new TipoUsuario[] { TipoUsuario.GERENTE_EXTERNO, TipoUsuario.ADMIN });
        verificadorPermissoesService.verificarPermissao(authentication.getName(), cargosPermitidos);

        cServices.deleteCaminhao(id);

        return new ResponseEntity<String>(DELETE_ROTA_REALIZADO, HttpStatus.OK);
    }
}
