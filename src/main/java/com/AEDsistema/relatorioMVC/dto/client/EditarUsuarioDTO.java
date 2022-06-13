package com.AEDsistema.relatorioMVC.dto.client;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.AEDsistema.relatorioMVC.enumerators.TipoUsuario;

public class EditarUsuarioDTO {
    @NotNull(message = "Erro na edição da rota: usuário não existe")
    private Integer id;

    @NotBlank(message = "Erro na edição do usuario: valor em branco/nulo (login)")
    private String login;

    @JsonProperty(value = "senha")
    private String senha;

    private String cpf;
    private String pix;
    private String identidade;

    @NotBlank(message = "Erro na edição do usuario: valor em branco/nulo (nome)")
    private String nome;

    @NotBlank(message = "Erro na edição do usuario: valor em branco/nulo (celular)")
    private String numeroCelular;

    @NotBlank(message = "Erro na edição do usuario: valor em branco/nulo (email)")
    @Pattern(regexp = "^([a-zA-Z0-9]+(?:[._-][a-zA-Z0-9]+)*)@([a-zA-Z0-9]+(?:[.-][a-zA-Z0-9]+)*\\.[a-zA-Z]{2,})$")
    private String email;

    @NotNull(message = "Erro na edição do usuario: valor nulo (cargo)")
    private TipoUsuario cargo;

    public EditarUsuarioDTO() {
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

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
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
