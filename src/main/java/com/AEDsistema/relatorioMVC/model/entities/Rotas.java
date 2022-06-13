package com.AEDsistema.relatorioMVC.model.entities;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;

import com.AEDsistema.relatorioMVC.dto.client.CadastrarRotasDTO;
import com.AEDsistema.relatorioMVC.dto.client.EditarRotasDTO;
import com.AEDsistema.relatorioMVC.dto.send.RotasSendDTO;
import com.AEDsistema.relatorioMVC.enumerators.TipoRota;
import com.AEDsistema.relatorioMVC.enumerators.TipoUsuario;
import com.AEDsistema.relatorioMVC.error.login.UsuarioCadastradoInvalidoException;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo", length = 1, discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("N")
public class Rotas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    protected TipoRota tipoRota;
    protected String numeroManifesto;
    protected String numeroRomaneio;
    protected LocalDate dataEmissao;
    protected LocalDate dataChegada;

    protected Integer idMotorista;
    protected String nomeMotorista;
    protected String numeroCelularMotorista;
    protected String cpfMotorista;
    protected String pixMotorista;
    protected String identidadeMotorista;

    protected Integer idNumeroRota;
    protected String codigoRota;
    protected String destinoRota;
    protected Double valorRota;
    protected Double despesasRota;

    protected Integer idCaminhao;
    protected String placaCaminhao;

    protected boolean isAprovado;
    private boolean isOkayToAprove;

    private LocalTime horaInicio;
    private LocalTime horaFim;

    private String responsavelNome;
    private String responsavelContato;
    private String responsavelCpf;
    private String responsavelPix;
    private String responsavelIdentidade;

    private String gerenteNome;
    private String gerenteContato;
    private String gerenteCpf;
    private String gerentePix;
    private String gerenteIdentidade;

    @ManyToOne
    private Usuario responsavel;
    @ManyToOne
    private Usuario gerente;

    public Rotas() {
    }

    public Rotas(LocalDate dataEmissao,
            String numeroRomaneio, Integer idMotorista, String nomeMotorista, String numeroCelularMotorista,
            String cpfMotorista, String pixMotorista, String identidadeMotorista, Integer idNumeroRota,
            String codigoRota, String destinoRota, Double valorRota, Double despesasRota, Integer idCaminhao,
            String placaCaminhao, Usuario responsavel, Usuario gerente) throws UsuarioCadastradoInvalidoException {
        this.numeroRomaneio = numeroRomaneio;

        this.dataEmissao = dataEmissao;

        this.idMotorista = idMotorista;
        this.nomeMotorista = nomeMotorista;
        this.numeroCelularMotorista = numeroCelularMotorista;
        this.cpfMotorista = cpfMotorista;
        this.pixMotorista = pixMotorista;
        this.identidadeMotorista = identidadeMotorista;

        this.idNumeroRota = idNumeroRota;
        this.codigoRota = codigoRota;
        this.destinoRota = destinoRota;
        this.valorRota = valorRota;
        this.despesasRota = despesasRota;

        this.idCaminhao = idCaminhao;
        this.placaCaminhao = placaCaminhao;

        this.setGerente(gerente);
        this.setResponsavel(responsavel);

        tipoRota = TipoRota.NORMAL;
        this.numeroManifesto = "";
        isAprovado = false;
        isOkayToAprove = false;
    }

    public static Rotas fromCadastrarRotas(CadastrarRotasDTO cadastrarRotasDTO, Usuario responsavel, Usuario gerente)
            throws UsuarioCadastradoInvalidoException {
        return new Rotas(LocalDate.parse(cadastrarRotasDTO.getDataEmissao(), DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                cadastrarRotasDTO.getNumeroRomaneio(), cadastrarRotasDTO.getIdMotorista(),
                cadastrarRotasDTO.getNomeMotorista(),
                cadastrarRotasDTO.getNumeroCelularMotorista(), cadastrarRotasDTO.getCpfMotorista(),
                cadastrarRotasDTO.getPixMotorista(),
                cadastrarRotasDTO.getIdentidadeMotorista(), cadastrarRotasDTO.getIdNumeroRota(),
                cadastrarRotasDTO.getCodigoRota(),
                cadastrarRotasDTO.getDestinoRota(), cadastrarRotasDTO.getValorRota(),
                cadastrarRotasDTO.getDespesasRota(),
                cadastrarRotasDTO.getIdCaminhao(), cadastrarRotasDTO.getPlacaCaminhao(), responsavel, gerente);
    }

    public void fromEditarRotas(EditarRotasDTO editarRotasDTO, Usuario responsavel, Usuario gerente)
            throws UsuarioCadastradoInvalidoException {
        this.setDataEmissao(
                LocalDate.parse(editarRotasDTO.getDataEmissao(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        this.setDataChegada(editarRotasDTO.getDataChegada() == "" ? null
                : LocalDate.parse(editarRotasDTO.getDataChegada(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));

        this.setHoraInicio(LocalTime.parse(editarRotasDTO.getHoraInicio() == "" ? null
                : editarRotasDTO.getHoraInicio(), DateTimeFormatter.ofPattern("HH:mm:ss")));
        this.setHoraFim(LocalTime.parse(editarRotasDTO.getHoraFim() == "" ? null
                : editarRotasDTO.getHoraFim(), DateTimeFormatter.ofPattern("HH:mm:ss")));

        this.setIdMotorista(editarRotasDTO.getIdMotorista());
        this.setNomeMotorista(editarRotasDTO.getNomeMotorista());
        this.setNumeroCelularMotorista(editarRotasDTO.getNumeroCelularMotorista());
        this.setCpfMotorista(editarRotasDTO.getCpfMotorista());
        this.setPixMotorista(editarRotasDTO.getPixMotorista());
        this.setIdentidadeMotorista(editarRotasDTO.getIdentidadeMotorista());

        this.setIdNumeroRota(editarRotasDTO.getIdNumeroRota());
        this.setCodigoRota(editarRotasDTO.getCodigoRota());
        this.setDestinoRota(editarRotasDTO.getDestinoRota());
        this.setValorRota(editarRotasDTO.getValorRota());
        this.setDespesasRota(editarRotasDTO.getDespesasRota());

        if (!isAprovado) {
            this.setResponsavel(responsavel);
        }

        this.setNumeroManifesto(editarRotasDTO.getNumeroManifesto());
        this.setNumeroRomaneio(editarRotasDTO.getNumeroRomaneio());

        this.setIdCaminhao(editarRotasDTO.getIdCaminhao());
        this.setPlacaCaminhao(editarRotasDTO.getPlacaCaminhao());
    }

    public RotasSendDTO toRotasSend() {
        if(responsavel == null) {
           responsavel = new Usuario();
           responsavel.setId(-1);
        }
        
        if(gerente == null) {
           gerente = new Usuario();
           gerente.setId(-1);
        }
        
        return new RotasSendDTO(id, tipoRota, numeroManifesto, numeroRomaneio, dataEmissao, dataChegada, horaInicio, horaFim,
                idMotorista, nomeMotorista, numeroCelularMotorista, cpfMotorista, pixMotorista, identidadeMotorista,
                idNumeroRota, codigoRota, destinoRota, valorRota, despesasRota, idCaminhao, placaCaminhao,
                responsavel.getId(), responsavelNome, responsavelContato, responsavelCpf, responsavelPix,
                responsavelIdentidade,
                gerente.getId(), gerenteNome, gerenteContato, gerenteCpf, gerentePix, gerenteIdentidade, isAprovado);
    }

    public String getPixMotorista() {
        return pixMotorista;
    }

    public void setPixMotorista(String pixMotorista) {
        this.pixMotorista = pixMotorista;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalTime getHoraFim() {
        return horaFim;
    }

    public void setHoraFim(LocalTime horaFim) {
        this.horaFim = horaFim;
    }

    public String getIdentidadeMotorista() {
        return identidadeMotorista;
    }

    public void setIdentidadeMotorista(String identidadeMotorista) {
        this.identidadeMotorista = identidadeMotorista;
    }

    public String getResponsavelCpf() {
        return responsavelCpf;
    }

    public void setResponsavelCpf(String responsavelCpf) {
        this.responsavelCpf = responsavelCpf;
    }

    public String getResponsavelPix() {
        return responsavelPix;
    }

    public void setResponsavelPix(String responsavelPix) {
        this.responsavelPix = responsavelPix;
    }

    public String getResponsavelIdentidade() {
        return responsavelIdentidade;
    }

    public void setResponsavelIdentidade(String responsavelIdentidade) {
        this.responsavelIdentidade = responsavelIdentidade;
    }

    public String getGerenteCpf() {
        return gerenteCpf;
    }

    public void setGerenteCpf(String gerenteCpf) {
        this.gerenteCpf = gerenteCpf;
    }

    public String getGerentePix() {
        return gerentePix;
    }

    public void setGerentePix(String gerentePix) {
        this.gerentePix = gerentePix;
    }

    public String getGerenteIdentidade() {
        return gerenteIdentidade;
    }

    public void setGerenteIdentidade(String gerenteIdentidade) {
        this.gerenteIdentidade = gerenteIdentidade;
    }

    public String getCpfMotorista() {
        return cpfMotorista;
    }

    public void setCpfMotorista(String cpfMotorista) {
        this.cpfMotorista = cpfMotorista;
    }

    public String getResponsavelNome() {
        return responsavelNome;
    }

    public void setResponsavelNome(String responsavelNome) {
        this.responsavelNome = responsavelNome;
    }

    public String getResponsavelContato() {
        return responsavelContato;
    }

    public void setResponsavelContato(String responsavelContato) {
        this.responsavelContato = responsavelContato;
    }

    public String getGerenteNome() {
        return gerenteNome;
    }

    public void setGerenteNome(String gerenteNome) {
        this.gerenteNome = gerenteNome;
    }

    public String getGerenteContato() {
        return gerenteContato;
    }

    public void setGerenteContato(String gerenteContato) {
        this.gerenteContato = gerenteContato;
    }

    public Integer getIdMotorista() {
        return idMotorista;
    }

    public void setIdMotorista(Integer idMotorista) {
        this.idMotorista = idMotorista;
    }

    public Integer getIdNumeroRota() {
        return idNumeroRota;
    }

    public void setIdNumeroRota(Integer idNumeroRota) {
        this.idNumeroRota = idNumeroRota;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumeroManifesto() {
        return numeroManifesto;
    }

    public void setNumeroManifesto(String numeroManifesto) {
        this.numeroManifesto = numeroManifesto;
    }

    public LocalDate getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(LocalDate dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public String getNomeMotorista() {
        return nomeMotorista;
    }

    public void setNomeMotorista(String nomeMotorista) {
        this.nomeMotorista = nomeMotorista;
    }

    public String getNumeroCelularMotorista() {
        return numeroCelularMotorista;
    }

    public void setNumeroCelularMotorista(String numeroCelularMotorista) {
        this.numeroCelularMotorista = numeroCelularMotorista;
    }

    public String getCodigoRota() {
        return codigoRota;
    }

    public void setCodigoRota(String codigoRota) {
        this.codigoRota = codigoRota;
    }

    public String getDestinoRota() {
        return destinoRota;
    }

    public void setDestinoRota(String destinoRota) {
        this.destinoRota = destinoRota;
    }

    public Double getValorRota() {
        return valorRota;
    }

    public void setValorRota(Double valorRota) {
        this.valorRota = valorRota;
    }

    public Double getDespesasRota() {
        return despesasRota;
    }

    public void setDespesasRota(Double despesasRota) {
        this.despesasRota = despesasRota;
    }

    public Usuario getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(Usuario responsavel) throws UsuarioCadastradoInvalidoException {
        if (!responsavel.getCargo().equals(TipoUsuario.RESPONSAVEL_INTERNO))
            throw new UsuarioCadastradoInvalidoException(TipoUsuario.RESPONSAVEL_INTERNO.toString());

        this.responsavel = responsavel;
        this.responsavelNome = responsavel.getNome();
        this.responsavelContato = responsavel.getNumeroCelular();
        this.responsavelCpf = responsavel.getCpf();
        this.responsavelPix = responsavel.getPix();
        this.responsavelIdentidade = responsavel.getIdentidade();
    }

    public void cleanResponsavel() {
        this.responsavel = null;
    }

    public Usuario getGerente() {
        return gerente;
    }

    public void setGerente(Usuario gerente) {
        this.gerente = gerente;
        this.gerenteNome = gerente.getNome();
        this.gerenteContato = gerente.getNumeroCelular();
        this.gerenteCpf = gerente.getCpf();
        this.gerentePix = gerente.getPix();
        this.gerenteIdentidade = gerente.getIdentidade();
    }

    public void cleanGerente() {
        this.gerente = null;
    }

    public boolean isAprovado() {
        return isAprovado;
    }

    public void setAprovado(boolean isAprovado) {
        if (!numeroManifesto.isEmpty() || isAprovado == false) {
            this.isAprovado = isAprovado;
        }
    }

    public TipoRota getTipoRota() {
        return tipoRota;
    }

    public void setTipoRota(TipoRota tipoRota) {
        this.tipoRota = tipoRota;
    }

    public LocalDate getDataChegada() {
        return dataChegada;
    }

    public void setDataChegada(LocalDate dataChegada) {
        this.dataChegada = dataChegada;
    }

    public Integer getIdCaminhao() {
        return idCaminhao;
    }

    public void setIdCaminhao(Integer idCaminhao) {
        this.idCaminhao = idCaminhao;
    }

    public String getPlacaCaminhao() {
        return placaCaminhao;
    }

    public void setPlacaCaminhao(String placaCaminhao) {
        this.placaCaminhao = placaCaminhao;
    }

    public String getNumeroRomaneio() {
        return numeroRomaneio;
    }

    public void setNumeroRomaneio(String numeroRomaneio) {
        this.numeroRomaneio = numeroRomaneio;
    }

    public boolean isOkayToAprove() {
        return isOkayToAprove;
    }

    public void setOkayToAprove(boolean isOkayToAprove) {
        this.isOkayToAprove = isOkayToAprove;
    }
}
