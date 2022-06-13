package com.AEDsistema.relatorioMVC.service.Rotas;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import com.AEDsistema.relatorioMVC.dto.client.CadastrarRotasDTO;
import com.AEDsistema.relatorioMVC.dto.client.EditarManifestoDTO;
import com.AEDsistema.relatorioMVC.dto.client.EditarRotasDTO;
import com.AEDsistema.relatorioMVC.dto.send.RotasSendDTO;
import com.AEDsistema.relatorioMVC.enumerators.TipoRota;
import com.AEDsistema.relatorioMVC.error.data.DataRangeException;
import com.AEDsistema.relatorioMVC.error.login.UsuarioCadastradoInvalidoException;
import com.AEDsistema.relatorioMVC.error.login.UsuarioNaoExisteException;
import com.AEDsistema.relatorioMVC.error.rota.RotaNaoExisteException;
import com.AEDsistema.relatorioMVC.model.entities.Rotas;
import com.AEDsistema.relatorioMVC.model.entities.Usuario;
import com.AEDsistema.relatorioMVC.repository.RotasRepository;
import com.AEDsistema.relatorioMVC.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RotasServicesImpl implements RotasServices {
    private final RotasRepository repository;
    private final UsuarioRepository uRepository;

    @Autowired
    public RotasServicesImpl(RotasRepository repository, UsuarioRepository uRepository) {
        this.repository = repository;
        this.uRepository = uRepository;
    }

    @Override
    public void cadastrarRota(CadastrarRotasDTO cadastrarRotasDTO)
            throws UsuarioNaoExisteException, UsuarioCadastradoInvalidoException {
        Optional<Usuario> uOptionalResponsavel = uRepository.findById(cadastrarRotasDTO.getResponsavelInterno());
        Optional<Usuario> uOptionalGerente = uRepository.findById(cadastrarRotasDTO.getGerenteExterno());

        if (!uOptionalResponsavel.isPresent())
            throw new UsuarioNaoExisteException("com cargo responsavel interno");
        if (!uOptionalGerente.isPresent())
            throw new UsuarioNaoExisteException("com cargo gerente externo");

        repository
                .save(Rotas.fromCadastrarRotas(cadastrarRotasDTO, uOptionalResponsavel.get(), uOptionalGerente.get()));
    }

    @Override
    public List<RotasSendDTO> pickAllRotas(String dataEmissao) {
        List<RotasSendDTO> returnList = new ArrayList<>();

        for (Rotas rotas : sortRotas(repository
                .findAllByDataEmissao(LocalDate.parse(dataEmissao, DateTimeFormatter.ofPattern("dd/MM/yyyy"))))) {
            if (rotas != null && rotas.getTipoRota().equals(TipoRota.NORMAL))
                returnList.add(rotas.toRotasSend());
        }

        return returnList;
    }

    @Override
    public void editarRota(EditarRotasDTO editarRotasExtrasDTO)
            throws UsuarioCadastradoInvalidoException, RotaNaoExisteException {
        Optional<Rotas> rOptional = repository.findById(editarRotasExtrasDTO.getId());

        if (!rOptional.isPresent())
            throw new RotaNaoExisteException(editarRotasExtrasDTO.getDestinoRota());

        rOptional.get().fromEditarRotas(editarRotasExtrasDTO,
                uRepository.getById(editarRotasExtrasDTO.getResponsavelInterno()),
                uRepository.getById(editarRotasExtrasDTO.getGerenteExterno()));

        repository.save(rOptional.get());
    }

    @Override
    public void deleteRota(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public RotasSendDTO getRotasById(Integer id) {
        return repository.findById(id).get().toRotasSend();

    }

    @Override
    public List<RotasSendDTO> getRotasForResponsavel(Integer idUsuario, boolean isForRelatorio) {
        List<RotasSendDTO> returnList = new ArrayList<>();

        for (Rotas rotas : sortRotas(repository.findAll())) {
            if (rotas != null && (isForRelatorio || (!rotas.isAprovado() && !rotas.isOkayToAprove()))
                    && rotas.getTipoRota().equals(TipoRota.NORMAL)
                    && rotas.getResponsavel().getId().equals(idUsuario))
                returnList.add(rotas.toRotasSend());
        }

        return returnList;
    }

    @Override
    public void editarManifesto(EditarManifestoDTO editarManifestoDTO) throws RotaNaoExisteException {
        Optional<Rotas> rOptional = repository.findById(editarManifestoDTO.getId());
        if (!rOptional.isPresent())
            throw new RotaNaoExisteException("rota não existente");

        rOptional.get().setNumeroManifesto(editarManifestoDTO.getNumeroManifesto());
        rOptional.get().setOkayToAprove(true);
        rOptional.get().setDataChegada(
                LocalDate.parse(editarManifestoDTO.getDataChegada(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        
        rOptional.get().setHoraInicio(LocalTime.parse(editarManifestoDTO.getHoraInicio(), DateTimeFormatter.ofPattern("HH:mm:ss")));
        rOptional.get().setHoraFim(LocalTime.parse(editarManifestoDTO.getHoraFim(), DateTimeFormatter.ofPattern("HH:mm:ss")));

        repository.save(rOptional.get());
    }

    @Override
    public List<RotasSendDTO> pickAllRotasAprovadas(String dataEmissao) {
        List<RotasSendDTO> returnList = new ArrayList<>();

        for (Rotas rotas : sortRotas(repository
                .findAllByDataEmissao(LocalDate.parse(dataEmissao, DateTimeFormatter.ofPattern("dd/MM/yyyy"))))) {
            if (rotas != null && !rotas.getNumeroManifesto().isEmpty() && rotas.isAprovado()
                    && rotas.getTipoRota().equals(TipoRota.NORMAL) && rotas.isOkayToAprove())
                returnList.add(rotas.toRotasSend());
        }

        return returnList;
    }

    @Override
    public void aprovar(Integer id) throws RotaNaoExisteException {
        Optional<Rotas> rOptional = repository.findById(id);
        if (!rOptional.isPresent())
            throw new RotaNaoExisteException("rota não existente");

        rOptional.get().setAprovado(true);

        repository.save(rOptional.get());
    }

    @Override
    public void naoAprovado(Integer id) throws RotaNaoExisteException {
        Optional<Rotas> rOptional = repository.findById(id);
        if (!rOptional.isPresent())
            throw new RotaNaoExisteException("rota não existente");

        rOptional.get().setAprovado(false);
        rOptional.get().setOkayToAprove(false);

        repository.save(rOptional.get());
    }

    @Override
    public List<RotasSendDTO> getRotasForGerente(Integer idUsuario) {
        List<RotasSendDTO> returnList = new ArrayList<>();

        for (Rotas rotas : sortRotas(repository.findAll())) {
            if (rotas != null && !rotas.getNumeroManifesto().isEmpty() && !rotas.isAprovado()
                    && rotas.getTipoRota().equals(TipoRota.NORMAL)
                    && rotas.getGerente().getId().equals(idUsuario) && rotas.isOkayToAprove())
                returnList.add(rotas.toRotasSend());
        }

        return returnList;
    }

    @Override
    public List<RotasSendDTO> pickAllRotasAprovadasByData(String dataEmissaoInicial, String dataEmissaoFinal)
            throws DataRangeException {
        List<Rotas> rotasLista = repository.findAll();
        List<LocalDate> datas = getRange(dataEmissaoInicial, dataEmissaoFinal);
        List<RotasSendDTO> returnLista = new ArrayList<>();

        for (Rotas rotas : sortRotas(rotasLista)) {
            if (rotas != null && !rotas.getNumeroManifesto().isEmpty() && rotas.isAprovado()
                    && rotas.getTipoRota().equals(TipoRota.NORMAL))
                for (LocalDate localDate : datas) {
                    if (localDate.isEqual(rotas.getDataEmissao())) {
                        returnLista.add(rotas.toRotasSend());
                        break;
                    }
                }
        }

        return returnLista;
    }

    @Override
    public List<RotasSendDTO> pickAllRotasByData(String dataEmissaoInicial, String dataEmissaoFinal)
            throws DataRangeException {
        List<Rotas> rotasLista = repository.findAll();
        List<LocalDate> datas = getRange(dataEmissaoInicial, dataEmissaoFinal);
        List<RotasSendDTO> returnLista = new ArrayList<>();

        for (Rotas rotas : sortRotas(rotasLista)) {
            if (rotas != null && rotas.getTipoRota().equals(TipoRota.NORMAL))
                for (LocalDate localDate : datas) {
                    if (localDate.isEqual(rotas.getDataEmissao())) {
                        returnLista.add(rotas.toRotasSend());
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

    @Override
    public List<RotasSendDTO> pickAllNovasRotas(int idCriador) {
        List<RotasSendDTO> returnList = new ArrayList<>();

        for (Rotas rotas : repository.findAll()) {
            if (rotas != null && rotas.getNumeroManifesto().isEmpty() && !rotas.isAprovado()
                    && rotas.getTipoRota().equals(TipoRota.NORMAL)
                    && rotas.getGerente().getId().equals(idCriador))
                returnList.add(rotas.toRotasSend());
        }

        return returnList;
    }

    public List<Rotas> sortRotas(List<Rotas> rotas) {
        List<Rotas> returnList = new ArrayList<>();
        Comparator<Rotas> compareValues = (rota1, rota2) -> {
            return rota1.getDataEmissao().compareTo(rota2.getDataEmissao());
        };
        rotas.stream().sorted(compareValues).forEach((val) -> {
            returnList.add(val);
        });

        return returnList;
    }
}
