package com.foroalura.ChallengeApiRest.repository;

import com.foroalura.ChallengeApiRest.model.Topico;
import com.foroalura.ChallengeApiRest.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUsuarioRepository extends JpaRepository<Usuario,Long> {

    Optional<Usuario> findByUsername(String username);

    @Query ("select u from Usuario u where u.username = ?1")
    Usuario getUsername(String username);
}
