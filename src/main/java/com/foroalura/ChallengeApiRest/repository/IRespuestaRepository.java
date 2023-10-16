package com.foroalura.ChallengeApiRest.repository;

import com.foroalura.ChallengeApiRest.model.Respuesta;
import com.foroalura.ChallengeApiRest.model.Topico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IRespuestaRepository extends JpaRepository<Respuesta,Long> {

    Optional<Respuesta> findById(Long id);

    @Query("select r from Respuesta r where r.autor = ?1")
    List<Respuesta> getRespuestas(String username);

}
