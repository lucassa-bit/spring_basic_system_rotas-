package com.AEDsistema.relatorioMVC.controllers;

import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import com.AEDsistema.relatorioMVC.dto.client.CadastrarRotasExtrasDTO;
import com.AEDsistema.relatorioMVC.dto.client.EditarRotasExtrasDTO;
import com.AEDsistema.relatorioMVC.dto.send.RotasExtrasSendDTO;
import com.AEDsistema.relatorioMVC.enumerators.TipoUsuario;
import com.AEDsistema.relatorioMVC.error.data.DataRangeException;
import com.AEDsistema.relatorioMVC.error.login.AcessoNegadoException;
import com.AEDsistema.relatorioMVC.error.login.UsuarioCadastradoInvalidoException;
import com.AEDsistema.relatorioMVC.error.login.UsuarioNaoExisteException;
import com.AEDsistema.relatorioMVC.error.rota.RotaNaoExisteException;
import com.AEDsistema.relatorioMVC.service.Login.VerificadorPermissoesService;
import com.AEDsistema.relatorioMVC.service.Rotas.RotasExtrasServices;

import org.springframework.format.annotation.DateTimeFormat;
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
@RequestMapping("/api/rota_extra")
public class RotasExtrasController {
    private final RotasExtrasServices rServices;
    private final VerificadorPermissoesService verificadorPermissoesService;

    private final String CADASTRO_ROTA_REALIZADO = "Cadastro de rota realizado com sucesso!";
    private final String EDICAO_ROTA_REALIZADO = "Edicao de rota realizado com sucesso!";
    private final String DELETE_ROTA_REALIZADO = "Delete de rota realizado com sucesso!";

    public RotasExtrasController(RotasExtrasServices rServices,
            VerificadorPermissoesService verificadorPermissoesService) {
        this.rServices = rServices;
        this.verificadorPermissoesService = verificadorPermissoesService;
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<String> createNewRotaExtra(Authentication authentication,
            @Valid @RequestBody CadastrarRotasExtrasDTO cadastrarRotasDTO)
            throws AcessoNegadoException, UsuarioNaoExisteException, UsuarioCadastradoInvalidoException {
        List<TipoUsuario> cargosPermitidos = Arrays
                .asList(new TipoUsuario[] { TipoUsuario.ADMIN, TipoUsuario.GERENTE_EXTERNO });
        verificadorPermissoesService.verificarPermissao(authentication.getName(), cargosPermitidos);

        rServices.cadastrarRota(cadastrarRotasDTO);

        return new ResponseEntity<String>(CADASTRO_ROTA_REALIZADO, HttpStatus.CREATED);
    }

    @PutMapping("/edit")
    public ResponseEntity<String> editNewRotaExtra(Authentication authentication,
            @Valid @RequestBody EditarRotasExtrasDTO usuarioDTO) throws UsuarioNaoExisteException,
            AcessoNegadoException, UsuarioCadastradoInvalidoException, RotaNaoExisteException {
        List<TipoUsuario> cargosPermitidos = Arrays.asList(new TipoUsuario[] { TipoUsuario.ADMIN });
        verificadorPermissoesService.verificarPermissao(authentication.getName(), cargosPermitidos);

        rServices.editarRota(usuarioDTO);

        return new ResponseEntity<String>(EDICAO_ROTA_REALIZADO, HttpStatus.CREATED);
    }

    @GetMapping("/find")
    public RotasExtrasSendDTO getUsuarioById(Authentication authentication, @RequestParam int id)
            throws UsuarioNaoExisteException, AcessoNegadoException {
        List<TipoUsuario> cargosPermitidos = Arrays.asList(new TipoUsuario[] { TipoUsuario.ADMIN });
        verificadorPermissoesService.verificarPermissao(authentication.getName(), cargosPermitidos);

        return rServices.getRotasExtrasById(id);
    }

    @GetMapping
    public List<RotasExtrasSendDTO> getAllRotasExtrasByData(Authentication authentication,
            @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") String dataEmissaoInicial,
            @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") String dataEmissaoFinal)
            throws UsuarioNaoExisteException, AcessoNegadoException, DataRangeException {
        List<TipoUsuario> cargosPermitidos = Arrays.asList(new TipoUsuario[] { TipoUsuario.ADMIN });
        verificadorPermissoesService.verificarPermissao(authentication.getName(), cargosPermitidos);

        return rServices.pickAllRotasByDataRange(dataEmissaoInicial, dataEmissaoFinal);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteRotaExtra(Authentication authentication,
            @RequestParam Integer id) throws UsuarioNaoExisteException, AcessoNegadoException {
        List<TipoUsuario> cargosPermitidos = Arrays
                .asList(new TipoUsuario[] { TipoUsuario.ADMIN });
        verificadorPermissoesService.verificarPermissao(authentication.getName(), cargosPermitidos);

        rServices.deleteRota(id);

        return new ResponseEntity<String>(DELETE_ROTA_REALIZADO, HttpStatus.OK);
    }
}
