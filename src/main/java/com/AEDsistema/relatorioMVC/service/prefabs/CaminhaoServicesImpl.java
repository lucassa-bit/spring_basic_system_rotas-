package com.AEDsistema.relatorioMVC.service.prefabs;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import com.AEDsistema.relatorioMVC.dto.client.CadastrarCaminhaoDTO;
import com.AEDsistema.relatorioMVC.dto.client.EditarCaminhaoDTO;
import com.AEDsistema.relatorioMVC.dto.send.CaminhaoSendDTO;
import com.AEDsistema.relatorioMVC.error.prefabs.CaminhaoNaoExisteException;
import com.AEDsistema.relatorioMVC.model.entities.Caminhao;
import com.AEDsistema.relatorioMVC.repository.CaminhaoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CaminhaoServicesImpl implements CaminhaoServices {
    private final CaminhaoRepository cRepository;

    @Autowired
    public CaminhaoServicesImpl(CaminhaoRepository cRepository) {
        this.cRepository = cRepository;
    }

    @Override
    public void cadastrarCaminhao(CadastrarCaminhaoDTO cadastrarCaminhao) {
        cRepository.save(Caminhao.fromCadastrarCaminhao(cadastrarCaminhao));
    }

    @Override
    public List<CaminhaoSendDTO> pickAllCaminhoes() {
        List<CaminhaoSendDTO> returnList = new ArrayList<>();

        for (Caminhao caminhao : sortRotas(cRepository.findAll())) {
            returnList.add(caminhao.toCaminhaoSendDTO());
        }

        return returnList;
    }

    @Override
    public void editarCaminhao(EditarCaminhaoDTO caminhaoDTO) throws CaminhaoNaoExisteException {
        Optional<Caminhao> toEdit = cRepository.findById(caminhaoDTO.getId());
        
        if(!toEdit.isPresent())
            throw new CaminhaoNaoExisteException(caminhaoDTO.getPlaca());

        toEdit.get().fromEditarCaminhao(caminhaoDTO);
        cRepository.save(toEdit.get());
    }

    @Override
    public void deleteCaminhao(Integer id) {
        cRepository.deleteById(id);
    }

    private int compareName(String name1, String name2) {
        for (int index = 0; index < name1.length() && index < name2.length(); index++) {
            if(name1.charAt(index) > name2.charAt(index)) return 1;
            else if(name1.charAt(index) < name2.charAt(index)) return -1;
        }
        return 0;
    }

    public List<Caminhao> sortRotas(List<Caminhao> motoristas) {
        List<Caminhao> returnList = new ArrayList<>();
        Comparator<Caminhao> compare = (caminhao1, caminhao2) -> {
            return compareName(caminhao1.getPlaca().toLowerCase(), caminhao2.getPlaca().toLowerCase());
        };
        motoristas.stream().sorted(compare).forEach((val) -> {
            returnList.add(val);
        });

        return returnList;
    }
}
