package com.AEDsistema.relatorioMVC.repository;

import java.util.Optional;

import com.AEDsistema.relatorioMVC.model.entities.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    public Optional<Usuario> findByLoginIgnoreCase(String login);

    public boolean existsByLoginIgnoreCaseOrderByNomeAsc(String login);
}
