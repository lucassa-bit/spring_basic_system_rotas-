package com.AEDsistema.relatorioMVC.controllers;

import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import com.AEDsistema.relatorioMVC.dto.client.CadastrarMotoristaDTO;
import com.AEDsistema.relatorioMVC.dto.client.EditarMotoristaDTO;
import com.AEDsistema.relatorioMVC.dto.send.MotoristaSendDTO;
import com.AEDsistema.relatorioMVC.enumerators.TipoUsuario;
import com.AEDsistema.relatorioMVC.error.login.AcessoNegadoException;
import com.AEDsistema.relatorioMVC.error.login.UsuarioCadastradoInvalidoException;
import com.AEDsistema.relatorioMVC.error.login.UsuarioNaoExisteException;
import com.AEDsistema.relatorioMVC.error.prefabs.MotoristaNaoExisteException;
import com.AEDsistema.relatorioMVC.service.Login.VerificadorPermissoesService;
import com.AEDsistema.relatorioMVC.service.prefabs.MotoristaServices;

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
@RequestMapping("/api/motorista")
public class MotoristaController {
    private final MotoristaServices mServices;
    private final VerificadorPermissoesService verificadorPermissoesService;

    private final String CADASTRO_ROTA_REALIZADO = "Cadastro de rota realizado com sucesso!";
    private final String EDICAO_ROTA_REALIZADO = "Edicao de rota realizado com sucesso!";
    private final String DELETE_ROTA_REALIZADO = "Delete de rota realizado com sucesso!";

    public MotoristaController(MotoristaServices mServices,
            VerificadorPermissoesService verificadorPermissoesService) {
        this.mServices = mServices;
        this.verificadorPermissoesService = verificadorPermissoesService;
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<String> createNewMotorista(Authentication authentication,
            @Valid @RequestBody CadastrarMotoristaDTO cadastrarMotoristaDTO)
            throws AcessoNegadoException, UsuarioNaoExisteException, UsuarioCadastradoInvalidoException {
        List<TipoUsuario> cargosPermitidos = Arrays
                .asList(new TipoUsuario[] { TipoUsuario.GERENTE_EXTERNO, TipoUsuario.ADMIN });
        verificadorPermissoesService.verificarPermissao(authentication.getName(), cargosPermitidos);

        mServices.cadastrarMotorista(cadastrarMotoristaDTO);

        return new ResponseEntity<String>(CADASTRO_ROTA_REALIZADO, HttpStatus.CREATED);
    }

    @PutMapping("/edit")
    public ResponseEntity<String> editMotorista(Authentication authentication,
            @Valid @RequestBody EditarMotoristaDTO editarMotoristaDTO) throws UsuarioNaoExisteException,
            AcessoNegadoException, MotoristaNaoExisteException {
        List<TipoUsuario> cargosPermitidos = Arrays.asList(new TipoUsuario[] { TipoUsuario.GERENTE_EXTERNO, TipoUsuario.ADMIN });
        verificadorPermissoesService.verificarPermissao(authentication.getName(), cargosPermitidos);

        mServices.editarMotorista(editarMotoristaDTO);

        return new ResponseEntity<String>(EDICAO_ROTA_REALIZADO, HttpStatus.CREATED);
    }

    @GetMapping
    public List<MotoristaSendDTO> getAllMotorista(Authentication authentication)
            throws UsuarioNaoExisteException, AcessoNegadoException {
        List<TipoUsuario> cargosPermitidos = Arrays.asList(new TipoUsuario[] { TipoUsuario.GERENTE_EXTERNO, TipoUsuario.ADMIN  });
        verificadorPermissoesService.verificarPermissao(authentication.getName(), cargosPermitidos);

        return mServices.pickAllMotoristas();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteMotorista(Authentication authentication,
            @RequestParam Integer id) throws UsuarioNaoExisteException, AcessoNegadoException {
        List<TipoUsuario> cargosPermitidos = Arrays
                .asList(new TipoUsuario[] { TipoUsuario.GERENTE_EXTERNO, TipoUsuario.ADMIN });
        verificadorPermissoesService.verificarPermissao(authentication.getName(), cargosPermitidos);

        mServices.deleteMotorista(id);

        return new ResponseEntity<String>(DELETE_ROTA_REALIZADO, HttpStatus.OK);
    }
}
