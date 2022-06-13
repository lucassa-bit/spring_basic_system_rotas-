package com.AEDsistema.relatorioMVC.controllers;

import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import com.AEDsistema.relatorioMVC.dto.client.CadastrarNumeroRotaDTO;
import com.AEDsistema.relatorioMVC.dto.client.EditarNumeroRotaDTO;
import com.AEDsistema.relatorioMVC.dto.send.NumeroRotaSendDTO;
import com.AEDsistema.relatorioMVC.enumerators.TipoUsuario;
import com.AEDsistema.relatorioMVC.error.login.AcessoNegadoException;
import com.AEDsistema.relatorioMVC.error.login.UsuarioCadastradoInvalidoException;
import com.AEDsistema.relatorioMVC.error.login.UsuarioNaoExisteException;
import com.AEDsistema.relatorioMVC.error.prefabs.NumeroRotaNaoExisteException;
import com.AEDsistema.relatorioMVC.service.Login.VerificadorPermissoesService;
import com.AEDsistema.relatorioMVC.service.prefabs.NumeroRotaService;

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
@RequestMapping("/api/numeroRota")
public class NumeroRotaController {
    private final  NumeroRotaService nServices;
    private final VerificadorPermissoesService verificadorPermissoesService;

    private final String CADASTRO_ROTA_REALIZADO = "Cadastro de rota realizado com sucesso!";
    private final String EDICAO_ROTA_REALIZADO = "Edicao de rota realizado com sucesso!";
    private final String DELETE_ROTA_REALIZADO = "Delete de rota realizado com sucesso!";

    public NumeroRotaController(NumeroRotaService nServices,
            VerificadorPermissoesService verificadorPermissoesService) {
        this.nServices = nServices;
        this.verificadorPermissoesService = verificadorPermissoesService;
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<String> createNewNumeroRota(Authentication authentication,
            @Valid @RequestBody CadastrarNumeroRotaDTO cadastrarMotoristaDTO)
            throws AcessoNegadoException, UsuarioNaoExisteException, UsuarioCadastradoInvalidoException {
        List<TipoUsuario> cargosPermitidos = Arrays
                .asList(new TipoUsuario[] { TipoUsuario.GERENTE_EXTERNO, TipoUsuario.ADMIN });
        verificadorPermissoesService.verificarPermissao(authentication.getName(), cargosPermitidos);

        nServices.cadastrarNumeroRota(cadastrarMotoristaDTO);

        return new ResponseEntity<String>(CADASTRO_ROTA_REALIZADO, HttpStatus.CREATED);
    }

    @PutMapping("/edit")
    public ResponseEntity<String> editNewNumeroRota(Authentication authentication,
            @Valid @RequestBody EditarNumeroRotaDTO editarMotoristaDTO) throws UsuarioNaoExisteException,
            AcessoNegadoException, NumeroRotaNaoExisteException {
        List<TipoUsuario> cargosPermitidos = Arrays.asList(new TipoUsuario[] { TipoUsuario.GERENTE_EXTERNO, TipoUsuario.ADMIN });
        verificadorPermissoesService.verificarPermissao(authentication.getName(), cargosPermitidos);

        nServices.editarNumeroRota(editarMotoristaDTO);

        return new ResponseEntity<String>(EDICAO_ROTA_REALIZADO, HttpStatus.CREATED);
    }

    @GetMapping
    public List<NumeroRotaSendDTO> getAllNumeroRotaByData(Authentication authentication)
            throws UsuarioNaoExisteException, AcessoNegadoException {
        List<TipoUsuario> cargosPermitidos = Arrays.asList(new TipoUsuario[] { TipoUsuario.GERENTE_EXTERNO, TipoUsuario.ADMIN });
        verificadorPermissoesService.verificarPermissao(authentication.getName(), cargosPermitidos);

        return nServices.pickAllNumeroRotas();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteNumeroRota(Authentication authentication,
            @RequestParam Integer id) throws UsuarioNaoExisteException, AcessoNegadoException {
        List<TipoUsuario> cargosPermitidos = Arrays
                .asList(new TipoUsuario[] { TipoUsuario.GERENTE_EXTERNO, TipoUsuario.ADMIN });
        verificadorPermissoesService.verificarPermissao(authentication.getName(), cargosPermitidos);

        nServices.deleteNumeroRota(id);

        return new ResponseEntity<String>(DELETE_ROTA_REALIZADO, HttpStatus.OK);
    }

    @PostMapping("/deleteWeb")
    public ResponseEntity<String> deleteNumeroRotaweb(Authentication authentication,
            @RequestParam Integer id) throws UsuarioNaoExisteException, AcessoNegadoException {
        List<TipoUsuario> cargosPermitidos = Arrays
                .asList(new TipoUsuario[] { TipoUsuario.GERENTE_EXTERNO, TipoUsuario.ADMIN });
        verificadorPermissoesService.verificarPermissao(authentication.getName(), cargosPermitidos);

        nServices.deleteNumeroRota(id);

        return new ResponseEntity<String>(DELETE_ROTA_REALIZADO, HttpStatus.OK);
    }
}
