package com.AEDsistema.relatorioMVC.service.Login;

import java.util.Optional;

import com.AEDsistema.relatorioMVC.Data.DetalheUsuarioData;
import com.AEDsistema.relatorioMVC.model.entities.Usuario;
import com.AEDsistema.relatorioMVC.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DetalheUsuarioServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {

        Optional<Usuario> usuario = usuarioRepository.findByLoginIgnoreCase(login);

        if (!usuario.isPresent())
            throw new UsernameNotFoundException("Usuário " + login + " não existe.");

        return new DetalheUsuarioData(usuario);
    }

}
