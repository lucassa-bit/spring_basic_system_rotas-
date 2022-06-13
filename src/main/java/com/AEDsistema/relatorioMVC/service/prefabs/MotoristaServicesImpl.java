package com.AEDsistema.relatorioMVC.service.prefabs;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import com.AEDsistema.relatorioMVC.dto.client.CadastrarMotoristaDTO;
import com.AEDsistema.relatorioMVC.dto.client.EditarMotoristaDTO;
import com.AEDsistema.relatorioMVC.dto.send.MotoristaSendDTO;
import com.AEDsistema.relatorioMVC.error.prefabs.MotoristaNaoExisteException;
import com.AEDsistema.relatorioMVC.model.entities.Motorista;
import com.AEDsistema.relatorioMVC.repository.MotoristaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MotoristaServicesImpl implements MotoristaServices {
    private final MotoristaRepository mRepository;

    @Autowired
    public MotoristaServicesImpl(MotoristaRepository mRepository) {
        this.mRepository = mRepository;
    }
    
    @Override
    public void cadastrarMotorista(CadastrarMotoristaDTO cadastrarMotoristaDTO) {
        mRepository.save(Motorista.fromCadastrarRotas(cadastrarMotoristaDTO));
    }

    @Override
    public List<MotoristaSendDTO> pickAllMotoristas() {
        List<MotoristaSendDTO> returnList = new ArrayList<>();

        for (Motorista motorista : sortRotas(mRepository.findAll())) {
            returnList.add(motorista.toMotoristaSend());
        }

        return returnList;
    }

    @Override
    public void editarMotorista(EditarMotoristaDTO motoristaDTO) throws MotoristaNaoExisteException {
        Optional<Motorista> toEdit = mRepository.findById(motoristaDTO.getId());
        
        if(!toEdit.isPresent())
            throw new MotoristaNaoExisteException(motoristaDTO.getNomeMotorista());

        toEdit.get().fromEditarRotas(motoristaDTO);
        mRepository.save(toEdit.get());
    }

    @Override
    public void deleteMotorista(Integer id) {
        mRepository.deleteById(id);
    }

    private int compareName(String name1, String name2) {
        for (int index = 0; index < name1.length() && index < name2.length(); index++) {
            if(name1.charAt(index) > name2.charAt(index)) return 1;
            else if(name1.charAt(index) < name2.charAt(index)) return -1;
        }
        return 0;
    }

    public List<Motorista> sortRotas(List<Motorista> motoristas) {
        List<Motorista> returnList = new ArrayList<>();
        Comparator<Motorista> compare = (motorista1, motorista2) -> {
            return compareName(motorista1.getNomeMotorista().toLowerCase(), motorista2.getNomeMotorista().toLowerCase());
        };
        motoristas.stream().sorted(compare).forEach((val) -> {
            returnList.add(val);
        });

        return returnList;
    }
}
