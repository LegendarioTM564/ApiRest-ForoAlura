package com.foroalura.ChallengeApiRest.service;

import com.foroalura.ChallengeApiRest.model.Usuario;
import com.foroalura.ChallengeApiRest.repository.IUsuarioRepository;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService implements IUsuarioService {

    @Autowired
    private IUsuarioRepository iUsuarioRepository;

    @Override
    public List<Usuario> getUsuarios() {
        List<Usuario> listaUser = iUsuarioRepository.findAll();
        return listaUser;
    }

    @Override
    public void saveUsuario(Usuario user) {
        iUsuarioRepository.save(user);
    }

    @Override
    public void saveUsuarios(List<Usuario> listaUsers) {
        iUsuarioRepository.saveAll(listaUsers);
    }

    @Override
    public void deleteUsuario(long id) {

        iUsuarioRepository.deleteById(id);
    }

    @Override
    public Usuario findUsuario(long id) {
        //Si no encuentro a la persona con orElse devuelvo NULL
        Usuario usu = iUsuarioRepository.findById(id).orElse(null);
        return usu;
    }

    @Override
    public boolean usuarioConPermiso(String username, Long id) {
        Optional<Usuario> usuarioOptional = iUsuarioRepository.findById(id);

        if(usuarioOptional.isPresent()){
            Usuario usuario = usuarioOptional.get();
            return usuario.getUsername().equals(username);
        }
        return false;
    }


    @Override
    public void editUsuario2(Usuario usu) {

        this.saveUsuario(usu);

    }
}
