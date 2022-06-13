package com.AEDsistema.relatorioMVC.service.prefabs;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import com.AEDsistema.relatorioMVC.dto.client.CadastrarNumeroRotaDTO;
import com.AEDsistema.relatorioMVC.dto.client.EditarNumeroRotaDTO;
import com.AEDsistema.relatorioMVC.dto.send.NumeroRotaSendDTO;
import com.AEDsistema.relatorioMVC.error.prefabs.NumeroRotaNaoExisteException;
import com.AEDsistema.relatorioMVC.model.entities.NumeroRota;
import com.AEDsistema.relatorioMVC.repository.NumeroRotaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NumeroRotaServiceImpl implements NumeroRotaService {
    private final NumeroRotaRepository nRepository;

    @Autowired
    public NumeroRotaServiceImpl(NumeroRotaRepository nRepository) {
        this.nRepository = nRepository;
    }

    @Override
    public void cadastrarNumeroRota(CadastrarNumeroRotaDTO cadastrarNumeroRotaDTO) {
        nRepository.save(NumeroRota.fromCadastrarRotas(cadastrarNumeroRotaDTO));        
    }

    @Override
    public List<NumeroRotaSendDTO> pickAllNumeroRotas() {
        List<NumeroRotaSendDTO> returnList = new ArrayList<>();

        for (NumeroRota numeroRota : sortRotas(nRepository.findAll())) {
            returnList.add(numeroRota.toRotasSend());
        }

        return returnList;
    }

    @Override
    public void editarNumeroRota(EditarNumeroRotaDTO motoristaDTO) throws NumeroRotaNaoExisteException {
        Optional<NumeroRota> nOptional = nRepository.findById(motoristaDTO.getId());

        if(!nOptional.isPresent()) throw new NumeroRotaNaoExisteException(motoristaDTO.getCodigoRota());

        nOptional.get().fromEditarRotas(motoristaDTO);

        nRepository.save(nOptional.get());
    }

    @Override
    public void deleteNumeroRota(Integer id) {
        nRepository.deleteById(id);
    }

    public List<NumeroRota> sortRotas(List<NumeroRota> rotas) {
        List<NumeroRota> returnList = new ArrayList<>();
        
        Comparator<NumeroRota> compare = (rota1, rota2) -> {
            return compareName(rota1.getCodigoRota(), rota2.getCodigoRota());
        };
        rotas.stream().sorted(compare).forEach((val) -> {
            returnList.add(val);
        });

        return returnList;
    }

    private int compareName(String name1, String name2) {
        for (int index = 0; index < name1.length() && index < name2.length(); index++) {
            if(name1.charAt(index) > name2.charAt(index)) return 1;
            else if(name1.charAt(index) < name2.charAt(index)) return -1;
        }
        return 0;
    }
}
