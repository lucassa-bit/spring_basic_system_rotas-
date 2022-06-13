package com.AEDsistema.relatorioMVC.repository;

import java.time.LocalDate;
import java.util.List;

import com.AEDsistema.relatorioMVC.model.entities.RotasExtras;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RotasExtrasRepository extends JpaRepository<RotasExtras, Integer> {
    public List<RotasExtras> findAllByDataEmissao(LocalDate dataEmissao);

    public List<RotasExtras> findAllByOrderByCodigoRotaDesc();
}
