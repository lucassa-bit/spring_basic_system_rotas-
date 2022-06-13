package com.AEDsistema.relatorioMVC.repository;

import java.time.LocalDate;
import java.util.List;

import com.AEDsistema.relatorioMVC.model.entities.Rotas;
import com.AEDsistema.relatorioMVC.model.entities.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RotasRepository extends JpaRepository<Rotas, Integer>{
    public List<Rotas> findAllByDataEmissao(LocalDate dataEmissao);
    public List<Rotas> findAllByGerente(Usuario gerente);
    public List<Rotas> findAllByResponsavel(Usuario responsavel);
}
