package com.AEDsistema.relatorioMVC.service.Rotas;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import com.AEDsistema.relatorioMVC.dto.client.CadastrarServicosExtrasDTO;
import com.AEDsistema.relatorioMVC.dto.client.EditarServicosExtrasDTO;
import com.AEDsistema.relatorioMVC.dto.send.ServicosExtrasSendDTO;
import com.AEDsistema.relatorioMVC.error.data.DataRangeException;
import com.AEDsistema.relatorioMVC.error.login.UsuarioCadastradoInvalidoException;
import com.AEDsistema.relatorioMVC.error.login.UsuarioNaoExisteException;
import com.AEDsistema.relatorioMVC.error.rota.ServicoNaoExisteException;
import com.AEDsistema.relatorioMVC.model.entities.ServicosExtras;
import com.AEDsistema.relatorioMVC.repository.ServicosExtrasRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServicosExtrasServicesImpl implements ServicosExtrasServices {
    private final ServicosExtrasRepository repository;

    @Autowired
    public ServicosExtrasServicesImpl(ServicosExtrasRepository repository) {
        this.repository = repository;
    }

    @Override
    public void cadastrarServicoExtra(CadastrarServicosExtrasDTO cadastrarRotasDTO)
            throws UsuarioNaoExisteException {
        repository.save(ServicosExtras.fromCadastrarServicosExtras(cadastrarRotasDTO));
    }

    @Override
    public List<ServicosExtrasSendDTO> pickAllServicosExtras(String dataEmissao) {
        List<ServicosExtrasSendDTO> returnList = new ArrayList<>();

        for (ServicosExtras servicosExtras : repository
                .findAllByDataEmissao(LocalDate.parse(dataEmissao, DateTimeFormatter.ofPattern("dd/MM/yyyy")))) {
            if (servicosExtras != null)
                returnList.add(servicosExtras.toServicosExtrasSend());
        }

        return returnList;
    }

    @Override
    public void editarServicoExtra(EditarServicosExtrasDTO editarServicosExtrasDTO)
            throws UsuarioCadastradoInvalidoException, ServicoNaoExisteException {
        Optional<ServicosExtras> sOptional = repository.findById(editarServicosExtrasDTO.getId());

        if (!sOptional.isPresent())
            throw new ServicoNaoExisteException(editarServicosExtrasDTO.getDescricaoServico());

        sOptional.get().fromEditarServicosExtras(editarServicosExtrasDTO);

        repository.save(sOptional.get());
    }

    @Override
    public void deleteServicoExtra(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public ServicosExtrasSendDTO getServicosExtrasById(Integer id) {
        return repository.findById(id).get().toServicosExtrasSend();
    }

    @Override
    public List<ServicosExtrasSendDTO> pickAllServicosExtrasByRangeData(String dataEmissaoInicial,
            String dataEmissaoFinal) throws DataRangeException {
        List<ServicosExtras> rotasLista = repository.findAll();
        List<LocalDate> datas = getRange(dataEmissaoInicial, dataEmissaoFinal);
        List<ServicosExtrasSendDTO> returnLista = new ArrayList<>();

        for (ServicosExtras sExtras : sortServicos(rotasLista)) {
            for (LocalDate localDate : datas) {
                if (localDate.isEqual(sExtras.getDataEmissao())) {
                    returnLista.add(sExtras.toServicosExtrasSend());
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

    public List<ServicosExtras> sortServicos(List<ServicosExtras> servicos) {
        List<ServicosExtras> returnList = new ArrayList<>();
        Comparator<ServicosExtras> compareValues = (servico1, servico2) -> {
            return servico1.getDataEmissao().compareTo(servico2.getDataEmissao());
        };
        servicos.stream().sorted(compareValues).forEach((val) -> {
            returnList.add(val);
        });

        return returnList;
    }
}
