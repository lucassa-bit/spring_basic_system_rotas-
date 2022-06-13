package com.AEDsistema.relatorioMVC.repository;

import java.time.LocalDate;
import java.util.List;

import com.AEDsistema.relatorioMVC.model.entities.ServicosExtras;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ServicosExtrasRepository extends JpaRepository<ServicosExtras, Integer> {
    public List<ServicosExtras> findAllByDataEmissao(LocalDate dataEmissao);
}
