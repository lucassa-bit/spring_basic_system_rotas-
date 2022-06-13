package com.AEDsistema.relatorioMVC.service.Rotas;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import com.AEDsistema.relatorioMVC.dto.client.CadastrarRotasExtrasDTO;
import com.AEDsistema.relatorioMVC.dto.client.EditarRotasExtrasDTO;
import com.AEDsistema.relatorioMVC.dto.send.RotasExtrasSendDTO;
import com.AEDsistema.relatorioMVC.enumerators.TipoRota;
import com.AEDsistema.relatorioMVC.error.data.DataRangeException;
import com.AEDsistema.relatorioMVC.error.login.UsuarioCadastradoInvalidoException;
import com.AEDsistema.relatorioMVC.error.login.UsuarioNaoExisteException;
import com.AEDsistema.relatorioMVC.error.rota.RotaNaoExisteException;
import com.AEDsistema.relatorioMVC.model.entities.RotasExtras;
import com.AEDsistema.relatorioMVC.model.entities.Usuario;
import com.AEDsistema.relatorioMVC.repository.RotasExtrasRepository;
import com.AEDsistema.relatorioMVC.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RotasExtrasServicesImpl implements RotasExtrasServices {
    private final RotasExtrasRepository repository;
    private final UsuarioRepository uRepository;

    @Autowired
    public RotasExtrasServicesImpl(RotasExtrasRepository repository, UsuarioRepository uRepository) {
        this.repository = repository;
        this.uRepository = uRepository;
    }

    @Override
    public void cadastrarRota(CadastrarRotasExtrasDTO cadastrarRotasDTO)
            throws UsuarioNaoExisteException, UsuarioCadastradoInvalidoException {
        Optional<Usuario> uOptionalAdmin = uRepository.findById(cadastrarRotasDTO.getIdAdministrador());

        if (!uOptionalAdmin.isPresent())
            throw new UsuarioNaoExisteException("com cargo Administrador");
        
        RotasExtras rota = RotasExtras.fromCadastrarRotas(cadastrarRotasDTO, uOptionalAdmin.get());
        rota.setAprovado(true);

        repository.save(rota);
    }

    @Override
    public List<RotasExtrasSendDTO> pickAllRotas(String dataEmissao) {
        List<RotasExtrasSendDTO> returnList = new ArrayList<>();

        for (RotasExtras rotas : sortRotas(repository.findAllByDataEmissao(LocalDate.parse(dataEmissao, DateTimeFormatter.ofPattern("dd/MM/yyyy"))))) {
            if(rotas != null && rotas.getTipoRota().equals(TipoRota.EXTRA)) 
            
            returnList.add(rotas.toRotasExtrasSend());
        }

        return returnList;
    }

    @Override
    public void editarRota(EditarRotasExtrasDTO editarRotasExtrasDTO) throws UsuarioCadastradoInvalidoException, RotaNaoExisteException {
        Optional<RotasExtras> rOptional = repository.findById(editarRotasExtrasDTO.getId());
        if (!rOptional.isPresent())
            throw new RotaNaoExisteException(editarRotasExtrasDTO.getDestinoRota());

        rOptional.get().fromEditarRotas(editarRotasExtrasDTO, uRepository.getById(editarRotasExtrasDTO.getIdAdministrador()));

        repository.save(rOptional.get());
    }

    @Override
    public void deleteRota(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public RotasExtrasSendDTO getRotasExtrasById(Integer id) {
        return repository.findById(id).get().toRotasExtrasSend();
    }

    @Override
    public List<RotasExtrasSendDTO> pickAllRotasByDataRange(String dataEmissaoInicial, String dataEmissaoFinal) throws DataRangeException {
        List<RotasExtras> rotasLista = repository.findAllByOrderByCodigoRotaDesc();
        List<RotasExtrasSendDTO> returnLista = new ArrayList<>();
        List<LocalDate> datas = getRange(dataEmissaoInicial, dataEmissaoFinal);

        for (RotasExtras rotas : sortRotas(rotasLista)) {
            if (rotas != null && rotas.getTipoRota() == TipoRota.EXTRA)
            for (LocalDate localDate : datas) {
                if(localDate.isEqual(rotas.getDataEmissao())) {
                    returnLista.add(rotas.toRotasExtrasSend());
                    break;
                }
            }    
        }

        return returnLista;
    }

    
    private List<LocalDate> getRange(String minimun, String max) throws DataRangeException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        LocalDate minimunDate = LocalDate.parse(minimun, formatter);
        LocalDate maxDate = LocalDate.parse(max, formatter);

        List<LocalDate> rangeList = new ArrayList<>();

        if (minimunDate.isAfter(maxDate))
            throw new DataRangeException();

        for (LocalDate date = minimunDate; maxDate.isAfter(date); date = date.plusDays(1L)) {
            rangeList.add(date);
        }
        rangeList.add(maxDate);

        return rangeList;
    }

    public List<RotasExtras> sortRotas(List<RotasExtras> rotas) {
        List<RotasExtras> returnList = new ArrayList<>();
        Comparator<RotasExtras> compareValues = (rota1, rota2) -> {
            return rota1.getDataEmissao().compareTo(rota2.getDataEmissao());
        };
        rotas.stream().sorted(compareValues).forEach((val) -> {
            returnList.add(val);
        });

        return returnList;
    }
}
