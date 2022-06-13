package com.AEDsistema.relatorioMVC.repository;

import com.AEDsistema.relatorioMVC.model.entities.Caminhao;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CaminhaoRepository extends JpaRepository<Caminhao, Integer> {
    
}
