package com.AEDsistema.relatorioMVC.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.AEDsistema.relatorioMVC.dto.send.UsuarioSendDTO;
import com.AEDsistema.relatorioMVC.enumerators.TipoUsuario;
import com.AEDsistema.relatorioMVC.error.data.DataRangeException;
import com.AEDsistema.relatorioMVC.error.data.TokenInexistenteException;
import com.AEDsistema.relatorioMVC.error.login.AcessoNegadoException;
import com.AEDsistema.relatorioMVC.error.login.UsuarioNaoExisteException;
import com.AEDsistema.relatorioMVC.security.TokenGenerate;
import com.AEDsistema.relatorioMVC.service.Login.VerificadorPermissoesService;
import com.AEDsistema.relatorioMVC.service.controleHoras.RelatorioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/relatorio")
public class RelatorioController {
        private final VerificadorPermissoesService verificadorPermissoesService;
        private final RelatorioService rService;

        List<String> temporaryTokensAdmin = new ArrayList<String>();
        List<String> temporaryTokensGerente = new ArrayList<String>();

        @Autowired
        public RelatorioController(VerificadorPermissoesService verificadorPermissoesService,
                        RelatorioService rService) {
                this.verificadorPermissoesService = verificadorPermissoesService;
                this.rService = rService;
        }

        @GetMapping("/token")
        public ResponseEntity<String> tokenTemporarioAdmin(Authentication authentication)
                        throws UsuarioNaoExisteException, AcessoNegadoException {
                List<TipoUsuario> cargosPermitidos = Arrays
                                .asList(new TipoUsuario[] { TipoUsuario.ADMIN, TipoUsuario.GERENTE_EXTERNO });
                UsuarioSendDTO usuario = verificadorPermissoesService.verificarPermissao(authentication.getName(),
                                cargosPermitidos);

                if(temporaryTokensAdmin.size() > 20) temporaryTokensAdmin.clear();
                if(temporaryTokensGerente.size() > 20) temporaryTokensGerente.clear();

                String temporaryToken = TokenGenerate.generateToken();
                if (usuario.getCargo() == TipoUsuario.ADMIN)
                        temporaryTokensAdmin.add(temporaryToken);
                else if (usuario.getCargo() == TipoUsuario.GERENTE_EXTERNO)
                        temporaryTokensGerente.add(temporaryToken);

                return new ResponseEntity<String>(temporaryToken, HttpStatus.OK);
        }

        // request do relatorio
        @GetMapping
        public ResponseEntity<InputStreamResource> generateRelatorio(
                        @RequestParam(value = "temporaryToken", required = true) String temporaryToken,
                        @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") String data_inicial,
                        @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") String data_final,
                        @RequestParam boolean isForAprovado)
                        throws UsuarioNaoExisteException, AcessoNegadoException, IOException, DataRangeException,
                        TokenInexistenteException {

                if (!temporaryTokensGerente.contains(temporaryToken) && !temporaryTokensAdmin.contains(temporaryToken))
                        throw new TokenInexistenteException();
                else if (temporaryTokensGerente.contains(temporaryToken)) {
                        rService.createCSV(data_inicial, data_final, false, isForAprovado);
                        temporaryTokensGerente.remove(temporaryToken);
                }
                else if (temporaryTokensAdmin.contains(temporaryToken)) {
                        rService.createCSV(data_inicial, data_final, true, isForAprovado);
                        temporaryTokensAdmin.remove(temporaryToken);
                }

                

                File file = new File("workbook.xlsx");
                InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

                HttpHeaders header = new HttpHeaders();
                header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + "workbook.xlsx");

                return ResponseEntity.ok()
                                .headers(header)
                                .contentLength(file.length())
                                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                                .body(resource);
        }

        @GetMapping("/funcionario")
        public ResponseEntity<InputStreamResource> generateRelatorioByFuncionario(
                        @RequestParam(value = "temporaryToken", required = true) String temporaryToken,
                        @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") String data_inicial,
                        @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") String data_final)
                        throws UsuarioNaoExisteException, AcessoNegadoException, IOException, DataRangeException,
                        TokenInexistenteException {

                if (!temporaryTokensGerente.contains(temporaryToken) && !temporaryTokensAdmin.contains(temporaryToken))
                        throw new TokenInexistenteException();
                else if (temporaryTokensAdmin.contains(temporaryToken)) {
                        rService.createCSVByFuncionario(data_inicial, data_final, true);
                        temporaryTokensAdmin.remove(temporaryToken);
                }

                File file = new File("workbook.xlsx");
                InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

                HttpHeaders header = new HttpHeaders();
                header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + "workbook.xlsx");

                return ResponseEntity.ok()
                                .headers(header)
                                .contentLength(file.length())
                                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                                .body(resource);
        }
        @GetMapping("/resumo")
        public ResponseEntity<InputStreamResource> generateResumo(
                        @RequestParam(value = "temporaryToken", required = true) String temporaryToken,
                        @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") String data_inicial,
                        @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") String data_final)
                        throws UsuarioNaoExisteException, AcessoNegadoException, IOException, DataRangeException,
                        TokenInexistenteException {

                if (!temporaryTokensGerente.contains(temporaryToken) && !temporaryTokensAdmin.contains(temporaryToken))
                        throw new TokenInexistenteException();
                else if (temporaryTokensGerente.contains(temporaryToken)) {
                        rService.createResumoCSV(data_inicial, data_final, false);
                        temporaryTokensGerente.remove(temporaryToken);
                }
                else if (temporaryTokensAdmin.contains(temporaryToken)) {
                        rService.createResumoCSV(data_inicial, data_final, true);
                        temporaryTokensAdmin.remove(temporaryToken);
                }

                

                File file = new File("workbook.xlsx");
                InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

                HttpHeaders header = new HttpHeaders();
                header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + "workbook.xlsx");

                return ResponseEntity.ok()
                                .headers(header)
                                .contentLength(file.length())
                                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                                .body(resource);
        }
}