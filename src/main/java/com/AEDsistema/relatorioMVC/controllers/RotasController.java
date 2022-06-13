package com.AEDsistema.relatorioMVC.controllers;

import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import com.AEDsistema.relatorioMVC.dto.client.CadastrarRotasDTO;
import com.AEDsistema.relatorioMVC.dto.client.EditarAprovarRotaDTO;
import com.AEDsistema.relatorioMVC.dto.client.EditarRotasDTO;
import com.AEDsistema.relatorioMVC.dto.client.EditarManifestoDTO;
import com.AEDsistema.relatorioMVC.dto.send.RotasSendDTO;
import com.AEDsistema.relatorioMVC.enumerators.TipoUsuario;
import com.AEDsistema.relatorioMVC.error.data.DataRangeException;
import com.AEDsistema.relatorioMVC.error.login.AcessoNegadoException;
import com.AEDsistema.relatorioMVC.error.login.UsuarioCadastradoInvalidoException;
import com.AEDsistema.relatorioMVC.error.login.UsuarioNaoExisteException;
import com.AEDsistema.relatorioMVC.error.rota.RotaNaoExisteException;
import com.AEDsistema.relatorioMVC.service.Login.VerificadorPermissoesService;
import com.AEDsistema.relatorioMVC.service.Rotas.RotasServices;

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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rota")
public class RotasController {
        private final RotasServices rServices;
        private final VerificadorPermissoesService verificadorPermissoesService;

        private final String CADASTRO_ROTA_REALIZADO = "Cadastro de rota realizado com sucesso!";
        private final String EDICAO_ROTA_REALIZADO = "Edicao de rota realizado com sucesso!";
        private final String DELETE_ROTA_REALIZADO = "Delete de rota realizado com sucesso!";

        public RotasController(RotasServices rServices, VerificadorPermissoesService verificadorPermissoesService) {
                this.rServices = rServices;
                this.verificadorPermissoesService = verificadorPermissoesService;
        }

        @PostMapping("/cadastrar")
        public ResponseEntity<String> createNewRota(Authentication authentication,
                        @Valid @RequestBody CadastrarRotasDTO cadastrarRotasDTO)
                        throws AcessoNegadoException, UsuarioNaoExisteException, UsuarioCadastradoInvalidoException {
                List<TipoUsuario> cargosPermitidos = Arrays
                                .asList(new TipoUsuario[] { TipoUsuario.ADMIN, TipoUsuario.GERENTE_EXTERNO });
                verificadorPermissoesService.verificarPermissao(authentication.getName(), cargosPermitidos);

                rServices.cadastrarRota(cadastrarRotasDTO);

                return new ResponseEntity<String>(CADASTRO_ROTA_REALIZADO, HttpStatus.CREATED);
        }

        @GetMapping("/find")
        @ResponseStatus(code = HttpStatus.FOUND)
        public RotasSendDTO getRotaById(Authentication authentication, @RequestParam int id)
                        throws UsuarioNaoExisteException, AcessoNegadoException {
                List<TipoUsuario> cargosPermitidos = Arrays.asList(new TipoUsuario[] { TipoUsuario.ADMIN,
                                TipoUsuario.GERENTE_EXTERNO, TipoUsuario.RESPONSAVEL_INTERNO });
                verificadorPermissoesService.verificarPermissao(authentication.getName(), cargosPermitidos);

                return rServices.getRotasById(id);
        }

        @GetMapping("/find/rotas")
        @ResponseStatus(code = HttpStatus.FOUND)
        public List<RotasSendDTO> getAllRotasByData(Authentication authentication,
                        @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") String dataEmissao)
                        throws UsuarioNaoExisteException, AcessoNegadoException {
                List<TipoUsuario> cargosPermitidos = Arrays.asList(
                                new TipoUsuario[] { TipoUsuario.ADMIN, TipoUsuario.GERENTE_EXTERNO,
                                                TipoUsuario.RESPONSAVEL_INTERNO });
                verificadorPermissoesService.verificarPermissao(authentication.getName(), cargosPermitidos);

                return rServices.pickAllRotas(dataEmissao);
        }

        @GetMapping("/find/concluidos")
        public List<RotasSendDTO> getAllRotasConcluidasByData(Authentication authentication,
                        @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") String dataEmissaoInicial,
                        @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") String dataEmissaoFinal)
                        throws UsuarioNaoExisteException, AcessoNegadoException, DataRangeException {
                List<TipoUsuario> cargosPermitidos = Arrays.asList(
                                new TipoUsuario[] { TipoUsuario.ADMIN, TipoUsuario.GERENTE_EXTERNO,
                                                TipoUsuario.RESPONSAVEL_INTERNO });
                verificadorPermissoesService.verificarPermissao(authentication.getName(), cargosPermitidos);

                return rServices.pickAllRotasAprovadasByData(dataEmissaoInicial, dataEmissaoFinal);
        }

        @GetMapping("/find/responsavel")
        public List<RotasSendDTO> getRotasForResponsavel(Authentication authentication,
                        @RequestParam Integer idUsuario) throws UsuarioNaoExisteException, AcessoNegadoException {
                List<TipoUsuario> cargosPermitidos = Arrays
                                .asList(new TipoUsuario[] { TipoUsuario.ADMIN, TipoUsuario.RESPONSAVEL_INTERNO });
                verificadorPermissoesService.verificarPermissao(authentication.getName(), cargosPermitidos);

                return rServices.getRotasForResponsavel(idUsuario, false);
        }

        @GetMapping("/find/gerente")
        public List<RotasSendDTO> getRotasForGerente(Authentication authentication,
                        @RequestParam Integer idUsuario) throws UsuarioNaoExisteException, AcessoNegadoException {
                List<TipoUsuario> cargosPermitidos = Arrays
                                .asList(new TipoUsuario[] { TipoUsuario.ADMIN, TipoUsuario.GERENTE_EXTERNO });
                verificadorPermissoesService.verificarPermissao(authentication.getName(), cargosPermitidos);

                return rServices.getRotasForGerente(idUsuario);
        }

        @GetMapping("/find/novas_cadastradas")
        public List<RotasSendDTO> getRotasForNovasRotas(Authentication authentication,
                        @RequestParam Integer idUsuario) throws UsuarioNaoExisteException, AcessoNegadoException {
                List<TipoUsuario> cargosPermitidos = Arrays
                                .asList(new TipoUsuario[] { TipoUsuario.ADMIN, TipoUsuario.GERENTE_EXTERNO });
                verificadorPermissoesService.verificarPermissao(authentication.getName(), cargosPermitidos);

                return rServices.pickAllNovasRotas(idUsuario);
        }

        @PutMapping("/edit")
        public ResponseEntity<String> editRota(Authentication authentication,
                        @Valid @RequestBody EditarRotasDTO editarRotasDTOs)
                        throws UsuarioNaoExisteException, AcessoNegadoException,
                        UsuarioCadastradoInvalidoException, RotaNaoExisteException {
                List<TipoUsuario> cargosPermitidos = Arrays
                                .asList(new TipoUsuario[] { TipoUsuario.ADMIN, TipoUsuario.GERENTE_EXTERNO });
                verificadorPermissoesService.verificarPermissao(authentication.getName(), cargosPermitidos);

                rServices.editarRota(editarRotasDTOs);

                return new ResponseEntity<String>(EDICAO_ROTA_REALIZADO, HttpStatus.CREATED);
        }

        @PutMapping("/edit/list")
        public ResponseEntity<String> editRotas(Authentication authentication,
                        @Valid @RequestBody List<EditarRotasDTO> editarRotasDTOs)
                        throws UsuarioNaoExisteException, AcessoNegadoException,
                        UsuarioCadastradoInvalidoException, RotaNaoExisteException {
                List<TipoUsuario> cargosPermitidos = Arrays
                                .asList(new TipoUsuario[] { TipoUsuario.ADMIN, TipoUsuario.GERENTE_EXTERNO });
                verificadorPermissoesService.verificarPermissao(authentication.getName(), cargosPermitidos);

                for (EditarRotasDTO eDto : editarRotasDTOs) {
                        rServices.editarRota(eDto);
                }

                return new ResponseEntity<String>(EDICAO_ROTA_REALIZADO, HttpStatus.CREATED);
        }

        @DeleteMapping("/delete")
        public ResponseEntity<String> deleteRota(Authentication authentication,
                        @RequestParam Integer id) throws UsuarioNaoExisteException, AcessoNegadoException {
                List<TipoUsuario> cargosPermitidos = Arrays
                                .asList(new TipoUsuario[] { TipoUsuario.ADMIN, TipoUsuario.GERENTE_EXTERNO });
                verificadorPermissoesService.verificarPermissao(authentication.getName(), cargosPermitidos);

                rServices.deleteRota(id);

                return new ResponseEntity<String>(DELETE_ROTA_REALIZADO, HttpStatus.OK);
        }

        @PutMapping("/edit/manifesto")
        public ResponseEntity<String> editManifesto(Authentication authentication,
                        @Valid @RequestBody List<EditarManifestoDTO> editarManifestoDTOs)
                        throws UsuarioNaoExisteException, AcessoNegadoException,
                        UsuarioCadastradoInvalidoException, RotaNaoExisteException {
                List<TipoUsuario> cargosPermitidos = Arrays
                                .asList(new TipoUsuario[] { TipoUsuario.ADMIN, TipoUsuario.RESPONSAVEL_INTERNO });
                verificadorPermissoesService.verificarPermissao(authentication.getName(), cargosPermitidos);

                for (EditarManifestoDTO eDto : editarManifestoDTOs) {
                        rServices.editarManifesto(eDto);
                }

                return new ResponseEntity<String>(EDICAO_ROTA_REALIZADO, HttpStatus.ACCEPTED);
        }

        @PutMapping("/edit/aprovar")
        public ResponseEntity<String> aprovarRota(Authentication authentication,
                        @Valid @RequestBody List<EditarAprovarRotaDTO> listaAprovados)
                        throws UsuarioNaoExisteException, AcessoNegadoException,
                        UsuarioCadastradoInvalidoException, RotaNaoExisteException {
                List<TipoUsuario> cargosPermitidos = Arrays
                                .asList(new TipoUsuario[] { TipoUsuario.ADMIN, TipoUsuario.GERENTE_EXTERNO });
                verificadorPermissoesService.verificarPermissao(authentication.getName(), cargosPermitidos);

                for (EditarAprovarRotaDTO eDto : listaAprovados) {
                        if (eDto.getIsAprovado() && eDto.getIsOkayToAprove()) {
                                rServices.aprovar(eDto.getId());
                        } else if(!eDto.getIsOkayToAprove()) {
                                rServices.naoAprovado(eDto.getId());
                        }
                }
                return new ResponseEntity<String>(EDICAO_ROTA_REALIZADO, HttpStatus.ACCEPTED);
        }
}
