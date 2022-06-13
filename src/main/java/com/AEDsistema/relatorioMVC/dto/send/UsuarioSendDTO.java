package com.AEDsistema.relatorioMVC.dto.send;

import com.AEDsistema.relatorioMVC.enumerators.TipoUsuario;

public class UsuarioSendDTO {
    private Integer id;
    private String login;
    private String nome;
    private String email;
    private String cpf;
    private String pix;
    private String identidade;
    private TipoUsuario cargo;
    private String numeroCelular;

    public UsuarioSendDTO() {
    }

    public UsuarioSendDTO(Integer id, String login, String nome, String cpf, String pix, String identidade,
            TipoUsuario cargo, String email, String numeroCelular) {
        this.id = id;
        this.login = login;
        this.nome = nome;
        this.cpf = cpf;
        this.pix = pix;
        this.identidade = identidade;
        this.cargo = cargo;
        this.email = email;
        this.numeroCelular = numeroCelular;
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
