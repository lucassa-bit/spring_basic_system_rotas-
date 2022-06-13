package com.AEDsistema.relatorioMVC.service.Login;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import com.AEDsistema.relatorioMVC.dto.client.CadastrarUsuarioDTO;
import com.AEDsistema.relatorioMVC.dto.client.EditarUsuarioDTO;
import com.AEDsistema.relatorioMVC.dto.send.UsuarioSendDTO;
import com.AEDsistema.relatorioMVC.enumerators.TipoUsuario;
import com.AEDsistema.relatorioMVC.error.login.EncodingPasswordException;
import com.AEDsistema.relatorioMVC.error.login.LoginAlreadyExistsException;
import com.AEDsistema.relatorioMVC.error.login.UsuarioNaoExisteException;
import com.AEDsistema.relatorioMVC.model.entities.Rotas;
import com.AEDsistema.relatorioMVC.model.entities.Usuario;
import com.AEDsistema.relatorioMVC.repository.RotasRepository;
import com.AEDsistema.relatorioMVC.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServicesImpl implements UsuarioServices {

    private final UsuarioRepository uRepository;
    private final RotasRepository rotasRepository;
    @Autowired
    private PasswordEncoder pEncoder;

    @Autowired
    public UsuarioServicesImpl(UsuarioRepository uRepository, RotasRepository rotasRepository) {
        this.uRepository = uRepository;
        this.rotasRepository = rotasRepository;
    }

    @Override
    public void createNewUsuario(CadastrarUsuarioDTO dto)
            throws LoginAlreadyExistsException, EncodingPasswordException {
        if (uRepository.existsByLoginIgnoreCaseOrderByNomeAsc(dto.getLogin()))
            throw new LoginAlreadyExistsException(dto.getLogin());

        uRepository.save(Usuario.fromDto(dto, pEncoder));
    }

    @Override
    public List<UsuarioSendDTO> pickAllUsers() {
        List<UsuarioSendDTO> usuariosList = new ArrayList<>();
        Iterator<Usuario> usuarios = uRepository.findAll().iterator();
        while (usuarios.hasNext()) {
            usuariosList.add(usuarios.next().toSendDTO());
        }

        return usuariosList;
    }

    @Override
    public void editarUsuario(EditarUsuarioDTO editarUsuarioDTO) throws UsuarioNaoExisteException {
        Optional<Usuario> pOptional = uRepository.findById(editarUsuarioDTO.getId());

        if (!pOptional.isPresent())
            throw new UsuarioNaoExisteException(editarUsuarioDTO.getNome());

        pOptional.get().fromEditarUsuario(editarUsuarioDTO);

        if (!editarUsuarioDTO.getSenha().isEmpty()) {
            String senhaCriptografada = pEncoder.encode(editarUsuarioDTO.getSenha());
            pOptional.get().setPassword(senhaCriptografada);
        }

        uRepository.save(pOptional.get());
    }

    @Override
    public UsuarioSendDTO getUsuarioByLogin(String login) throws UsuarioNaoExisteException {
        Optional<Usuario> usuario_optional = uRepository.findByLoginIgnoreCase(login);
        if (!usuario_optional.isPresent())
            throw new UsuarioNaoExisteException(login);

        return usuario_optional.get().toSendDTO();
    }

    @Override
    public void deleteUsuario(int id) {
        TipoUsuario tipos = uRepository.getById(id).getCargo();

        switch (tipos) {
            case ADMIN:
                {
                    for (Rotas rotas : rotasRepository.findAllByGerente(uRepository.findById(id).get())) {
                        if(!rotas.isAprovado()) {
                            rotas.cleanGerente();
                            rotas.setGerenteNome("");
                            rotas.setGerenteContato("");

                            rotasRepository.save(rotas);
                        }
                    }
                }
                break;
            case RESPONSAVEL_INTERNO:
                {
                    for (Rotas rotas : rotasRepository.findAllByResponsavel(uRepository.findById(id).get())) {
                        if(!rotas.isAprovado()) {
                            rotas.cleanResponsavel();
                            rotas.setResponsavelNome("");
                            rotas.setResponsavelContato("");
                            rotas.setNumeroManifesto("");

                            rotasRepository.save(rotas);
                        }
                    }
                }
                break;
            case GERENTE_EXTERNO:
                {
                    for (Rotas rotas : rotasRepository.findAllByGerente(uRepository.findById(id).get())) {
                        if(!rotas.isAprovado()) {
                            rotas.cleanGerente();
                            rotas.setGerenteNome("");
                            rotas.setGerenteContato("");

                            rotasRepository.save(rotas);
                        }
                    }
                }
                break;
        }

        uRepository.deleteById(id);
    }

    @Override
    public UsuarioSendDTO getUsuarioById(Integer id) {
        return uRepository.findById(id).get().toSendDTO();
    }

    @Override
    public List<UsuarioSendDTO> pickAllResponsaveisInterno() {
        List<UsuarioSendDTO> usuariosList = new ArrayList<>();

        for (Usuario usuario : sortRotas(uRepository.findAll())) {
            if(usuario.getCargo().equals(TipoUsuario.RESPONSAVEL_INTERNO))
                usuariosList.add(usuario.toSendDTO());
        }

        return usuariosList;
    }

    public List<Usuario> sortRotas(List<Usuario> usuario) {
        List<Usuario> returnList = new ArrayList<>();
        Comparator<Usuario> compare = (usuario1, usuario2) -> {
            return compareName(usuario1.getNome().toLowerCase(), usuario2.getNome().toLowerCase());
        };
        usuario.stream().sorted(compare).forEach((val) -> {
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
