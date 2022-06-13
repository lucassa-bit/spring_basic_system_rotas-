package com.AEDsistema.relatorioMVC.repository;

import java.util.List;

import com.AEDsistema.relatorioMVC.model.entities.NumeroRota;

import org.springframework.data.jpa.repository.JpaRepository;

public interface NumeroRotaRepository extends JpaRepository<NumeroRota, Integer>{
    public List<NumeroRota> findAllByOrderByCodigoRotaAsc();
}
