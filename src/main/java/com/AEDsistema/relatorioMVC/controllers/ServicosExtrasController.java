package com.AEDsistema.relatorioMVC.controllers;

import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import com.AEDsistema.relatorioMVC.dto.client.CadastrarServicosExtrasDTO;
import com.AEDsistema.relatorioMVC.dto.client.EditarServicosExtrasDTO;
import com.AEDsistema.relatorioMVC.dto.send.ServicosExtrasSendDTO;
import com.AEDsistema.relatorioMVC.enumerators.TipoUsuario;
import com.AEDsistema.relatorioMVC.error.data.DataRangeException;
import com.AEDsistema.relatorioMVC.error.login.AcessoNegadoException;
import com.AEDsistema.relatorioMVC.error.login.UsuarioCadastradoInvalidoException;
import com.AEDsistema.relatorioMVC.error.login.UsuarioNaoExisteException;
import com.AEDsistema.relatorioMVC.error.rota.ServicoNaoExisteException;
import com.AEDsistema.relatorioMVC.service.Login.VerificadorPermissoesService;
import com.AEDsistema.relatorioMVC.service.Rotas.ServicosExtrasServices;

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
@RequestMapping("/api/servico_extra")
public class ServicosExtrasController {
    private final ServicosExtrasServices sServices;
    private final VerificadorPermissoesService verificadorPermissoesService;

    private final String CADASTRO_ROTA_REALIZADO = "Cadastro de rota realizado com sucesso!";
    private final String EDICAO_ROTA_REALIZADO = "Edicao de rota realizado com sucesso!";
    private final String DELETE_ROTA_REALIZADO = "Delete de rota realizado com sucesso!";

    public ServicosExtrasController(ServicosExtrasServices sServices,
            VerificadorPermissoesService verificadorPermissoesService) {
        this.sServices = sServices;
        this.verificadorPermissoesService = verificadorPermissoesService;
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<String> createNewServicoExtra(Authentication authentication,
            @Valid @RequestBody CadastrarServicosExtrasDTO cadastrarRotasDTO)
            throws AcessoNegadoException, UsuarioNaoExisteException, UsuarioCadastradoInvalidoException {
        List<TipoUsuario> cargosPermitidos = Arrays
                .asList(new TipoUsuario[] { TipoUsuario.ADMIN, TipoUsuario.GERENTE_EXTERNO });
        verificadorPermissoesService.verificarPermissao(authentication.getName(), cargosPermitidos);

        sServices.cadastrarServicoExtra(cadastrarRotasDTO);

        return new ResponseEntity<String>(CADASTRO_ROTA_REALIZADO, HttpStatus.CREATED);
    }

    @PutMapping("/edit")
    public ResponseEntity<String> editNewServicoExtra(Authentication authentication,
            @Valid @RequestBody EditarServicosExtrasDTO usuarioDTO) throws UsuarioNaoExisteException,
            AcessoNegadoException, UsuarioCadastradoInvalidoException, ServicoNaoExisteException {
        List<TipoUsuario> cargosPermitidos = Arrays.asList(new TipoUsuario[] { TipoUsuario.ADMIN });
        verificadorPermissoesService.verificarPermissao(authentication.getName(), cargosPermitidos);

        sServices.editarServicoExtra(usuarioDTO);

        return new ResponseEntity<String>(EDICAO_ROTA_REALIZADO, HttpStatus.CREATED);
    }

    @GetMapping
    public List<ServicosExtrasSendDTO> getAllServicoExtraByData(Authentication authentication,
            @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") String dataEmissaoInicial,
            @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") String dataEmissaoFinal)
            throws UsuarioNaoExisteException, AcessoNegadoException, DataRangeException {
        List<TipoUsuario> cargosPermitidos = Arrays.asList(new TipoUsuario[] { TipoUsuario.ADMIN });
        verificadorPermissoesService.verificarPermissao(authentication.getName(), cargosPermitidos);

        return sServices.pickAllServicosExtrasByRangeData(dataEmissaoInicial, dataEmissaoFinal);
    }

    @GetMapping("/find")
    public ServicosExtrasSendDTO getUsuarioById(Authentication authentication, @RequestParam int id)
            throws UsuarioNaoExisteException, AcessoNegadoException {
        List<TipoUsuario> cargosPermitidos = Arrays.asList(new TipoUsuario[] { TipoUsuario.ADMIN });
        verificadorPermissoesService.verificarPermissao(authentication.getName(), cargosPermitidos);

        return sServices.getServicosExtrasById(id);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteServicoExtra(Authentication authentication,
            @RequestParam Integer id) throws UsuarioNaoExisteException, AcessoNegadoException {
        List<TipoUsuario> cargosPermitidos = Arrays
                .asList(new TipoUsuario[] { TipoUsuario.ADMIN });
        verificadorPermissoesService.verificarPermissao(authentication.getName(), cargosPermitidos);

        sServices.deleteServicoExtra(id);

        return new ResponseEntity<String>(DELETE_ROTA_REALIZADO, HttpStatus.OK);
    }
}
