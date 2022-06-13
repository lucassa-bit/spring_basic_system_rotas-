package com.AEDsistema.relatorioMVC.model.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.AEDsistema.relatorioMVC.dto.client.CadastrarUsuarioDTO;
import com.AEDsistema.relatorioMVC.dto.client.EditarUsuarioDTO;
import com.AEDsistema.relatorioMVC.dto.send.UsuarioSendDTO;
import com.AEDsistema.relatorioMVC.enumerators.TipoUsuario;
import com.AEDsistema.relatorioMVC.error.login.EncodingPasswordException;
import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String login;

    private String password;

    private String nome;

    private String cpf;

    private String pix;
    private String identidade;

    @Column(unique = true)
    private String email;

    private String numeroCelular;

    private TipoUsuario cargo;

    @OneToMany(mappedBy = "responsavel", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Rotas> rotasCadastradasResponsavel;

    @OneToMany(mappedBy = "gerente", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Rotas> rotasCadastradasGerente;

    @OneToMany(mappedBy = "administrador", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<RotasExtras> rotasCadastradasAdministrador;

    public Usuario() {
    }

    public Usuario(String login, String password, String nome, String cpf, TipoUsuario cargo, String email,
            String numeroCelular, String pix, String identidade) {
        this.login = login;
        this.password = password;
        this.nome = nome;
        this.cpf = cpf;
        this.cargo = cargo;
        this.email = email;
        this.numeroCelular = numeroCelular;
        this.pix = pix;
        this.identidade = identidade;
    }

    public static Usuario fromDto(CadastrarUsuarioDTO dto, PasswordEncoder pEncoder) throws EncodingPasswordException {
        String passwordEncoded = pEncoder.encode(dto.getSenha());

        return new Usuario(dto.getLogin(), passwordEncoded, dto.getNome(), dto.getCpf(), dto.getCargo(), dto.getEmail(),
                dto.getNumeroCelular(), dto.getPix(), dto.getIdentidade());
    }

    public void fromEditarUsuario(EditarUsuarioDTO editarUsuarioDTO) {
        setCargo(editarUsuarioDTO.getCargo());
        setLogin(editarUsuarioDTO.getLogin());
        setCpf(editarUsuarioDTO.getCpf());
        setNome(editarUsuarioDTO.getNome());
        setEmail(editarUsuarioDTO.getEmail());
        setNumeroCelular(editarUsuarioDTO.getNumeroCelular());
        setPix(editarUsuarioDTO.getPix());
        setIdentidade(editarUsuarioDTO.getIdentidade());
    }

    public UsuarioSendDTO toSendDTO() {
        return new UsuarioSendDTO(id, login, nome, cpf, pix, identidade, cargo, email, numeroCelular);
    }

    public String getPix() {
        return pix;
    }

    public void setPix(String pix) {
        this.pix = pix;
    }

    public String getIdentidade() {
        return identidade;
    }

    public void setIdentidade(String identidade) {
        this.identidade = identidade;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public TipoUsuario getCargo() {
        return cargo;
    }

    public void setCargo(TipoUsuario cargo) {
        this.cargo = cargo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumeroCelular() {
        return numeroCelular;
    }

    public void setNumeroCelular(String numeroCelular) {
        this.numeroCelular = numeroCelular;
    }
}
