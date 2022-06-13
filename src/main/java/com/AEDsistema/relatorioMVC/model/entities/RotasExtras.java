package com.AEDsistema.relatorioMVC.model.entities;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.AEDsistema.relatorioMVC.dto.client.CadastrarRotasExtrasDTO;
import com.AEDsistema.relatorioMVC.dto.client.EditarRotasExtrasDTO;
import com.AEDsistema.relatorioMVC.dto.send.RotasExtrasSendDTO;
import com.AEDsistema.relatorioMVC.enumerators.TipoRota;
import com.AEDsistema.relatorioMVC.enumerators.TipoUsuario;
import com.AEDsistema.relatorioMVC.error.login.UsuarioCadastradoInvalidoException;

@Entity
@DiscriminatorValue(value = "E")
public class RotasExtras extends Rotas {

        @ManyToOne
        private Usuario administrador;

        private String administradorNome;
        private String administradorContato;
        private String administradorCpf;
        private String administradorPix;
        private String administradorIdentidade;

        public RotasExtras() {

        }


        public RotasExtras(String numeroManifesto, String numeroRomaneio, LocalDate dataEmissao, LocalDate dataChegada, String nomeMotorista, String cpfMotorista,
                        String numeroCelularMotorista, String pixMotorista, String identidadeMotorista, String codigoRota, String destinoRota, Double valorRota,
                        Double despesasRota, String placaCaminhao, Usuario administrador)
                        throws UsuarioCadastradoInvalidoException {
                this.dataChegada = dataChegada;
                this.numeroManifesto = numeroManifesto;
                this.numeroRomaneio = numeroRomaneio;
                this.dataEmissao = dataEmissao;

                this.nomeMotorista = nomeMotorista;
                this.numeroCelularMotorista = numeroCelularMotorista;
                this.cpfMotorista = cpfMotorista;
                this.pixMotorista = pixMotorista;
                this.identidadeMotorista = identidadeMotorista;

                this.codigoRota = codigoRota;
                this.destinoRota = destinoRota;
                this.valorRota = valorRota;
                this.despesasRota = despesasRota;

                this.placaCaminhao = placaCaminhao;

                this.setAdministrador(administrador);

                this.setNumeroManifesto(numeroManifesto);
                this.setTipoRota(TipoRota.EXTRA);
                this.setAprovado(true);
        }

        public static RotasExtras fromCadastrarRotas(CadastrarRotasExtrasDTO cadastrarRotasDTO, Usuario administrador)
                        throws UsuarioCadastradoInvalidoException {
                LocalDate dataChegadaCadastro = cadastrarRotasDTO.getDataChegada() != null
                                && !cadastrarRotasDTO.getDataChegada().isEmpty()
                                                ? LocalDate.parse(
                                                                cadastrarRotasDTO.getDataChegada(),
                                                                DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                                                : null;
                return new RotasExtras(cadastrarRotasDTO.getNumeroManifesto(), cadastrarRotasDTO.getNumeroRomaneio(),
                                LocalDate.parse(cadastrarRotasDTO.getDataEmissao(),
                                                DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                                dataChegadaCadastro,
                                cadastrarRotasDTO.getNomeMotorista(), cadastrarRotasDTO.getCpfMotorista(),
                                cadastrarRotasDTO.getNumeroCelularMotorista(), cadastrarRotasDTO.getPixMotorista(),
                                cadastrarRotasDTO.getIdentidadeMotorista(),
                                cadastrarRotasDTO.getCodigoRota(), cadastrarRotasDTO.getDestinoRota(),
                                cadastrarRotasDTO.getValorRota(), cadastrarRotasDTO.getDespesasRota(),
                                cadastrarRotasDTO.getPlacaCaminhao(), administrador);
        }

        public void fromEditarRotas(EditarRotasExtrasDTO editarRotasDTO, Usuario Administrador)
                        throws UsuarioCadastradoInvalidoException {
                this.setDataEmissao(
                                LocalDate.parse(editarRotasDTO.getDataEmissao(),
                                                DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                this.setDataChegada(editarRotasDTO.getDataChegada() == "" ? null
                                : LocalDate.parse(editarRotasDTO.getDataChegada(),
                                                DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                this.setNumeroManifesto(editarRotasDTO.getNumeroManifesto());

                this.setNomeMotorista(editarRotasDTO.getNomeMotorista());
                this.setNumeroCelularMotorista(editarRotasDTO.getNumeroCelularMotorista());
                this.setCpfMotorista(editarRotasDTO.getCpfMotorista());
                this.setPixMotorista(editarRotasDTO.getPixMotorista());
                this.setIdentidadeMotorista(editarRotasDTO.getIdentidadeMotorista());

                this.setCodigoRota(editarRotasDTO.getCodigoRota());
                this.setDestinoRota(editarRotasDTO.getDestinoRota());
                this.setValorRota(editarRotasDTO.getValorRota());
                this.setDespesasRota(editarRotasDTO.getDespesasRota());

                this.setPlacaCaminhao(editarRotasDTO.getPlacaCaminhao());
        }

        public RotasExtrasSendDTO toRotasExtrasSend() {
                return new RotasExtrasSendDTO(id, tipoRota, numeroManifesto, numeroRomaneio, dataEmissao, dataChegada,
                                idCaminhao, placaCaminhao, idMotorista, nomeMotorista, numeroCelularMotorista,
                                cpfMotorista, pixMotorista, identidadeMotorista, idNumeroRota, codigoRota, destinoRota,
                                valorRota, despesasRota, administrador.getId(), administradorNome, administradorContato,
                                administradorCpf, administradorPix, administradorIdentidade);
        }

        public String getAdministradorCpf() {
                return administradorCpf;
        }

        public void setAdministradorCpf(String administradorCpf) {
                this.administradorCpf = administradorCpf;
        }

        public String getAdministradorPix() {
                return administradorPix;
        }

        public void setAdministradorPix(String administradorPix) {
                this.administradorPix = administradorPix;
        }

        public String getAdministradorIdentidade() {
                return administradorIdentidade;
        }

        public void setAdministradorIdentidade(String administradorIdentidade) {
                this.administradorIdentidade = administradorIdentidade;
        }

        public Usuario getAdministrador() {
                return administrador;
        }

        public void setAdministrador(Usuario administrador) throws UsuarioCadastradoInvalidoException {
                if (!administrador.getCargo().equals(TipoUsuario.ADMIN))
                        throw new UsuarioCadastradoInvalidoException(TipoUsuario.ADMIN.toString());

                this.administrador = administrador;
                this.administradorNome = administrador.getNome();
                this.administradorContato = administrador.getNumeroCelular();
                this.administradorCpf = administrador.getCpf();
                this.administradorPix = administrador.getPix();
                this.administradorIdentidade = administrador.getIdentidade();
        }

        public String getAdministradorNome() {
                return administradorNome;
        }

        public void setAdministradorNome(String administradorNome) {
                this.administradorNome = administradorNome;
        }

        public String getAdministradorContato() {
                return administradorContato;
        }

        public void setAdministradorContato(String administradorContato) {
                this.administradorContato = administradorContato;
        }

}
