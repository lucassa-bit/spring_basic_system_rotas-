package com.AEDsistema.relatorioMVC.Data;

import java.util.Collection;
import java.util.Optional;

import com.AEDsistema.relatorioMVC.model.entities.Usuario;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class DetalheUsuarioData implements UserDetails {

    private Optional<Usuario> usuario;

    private Collection<? extends GrantedAuthority> authorities;

    public DetalheUsuarioData() {
    }

    public DetalheUsuarioData(Optional<Usuario> usuario) {
        this();
        this.usuario = usuario;
    }

    public Usuario getUsuario() {
        if(!usuario.isPresent()) return null;
        return usuario.get();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return usuario.orElse(new Usuario()).getPassword();
    }

    @Override
    public String getUsername() {
        return usuario.orElse(new Usuario()).getLogin();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
