package com.AEDsistema.relatorioMVC.repository;

import com.AEDsistema.relatorioMVC.model.entities.Motorista;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MotoristaRepository extends JpaRepository<Motorista, Integer> {
    
}
