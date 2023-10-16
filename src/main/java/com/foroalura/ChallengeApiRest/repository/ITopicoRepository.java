package com.foroalura.ChallengeApiRest.repository;

import com.foroalura.ChallengeApiRest.model.Topico;
import com.foroalura.ChallengeApiRest.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ITopicoRepository extends JpaRepository<Topico,Long> {

    Optional<Topico> findById(Long id);

    @Query("SELECT DISTINCT t FROM Topico t LEFT JOIN FETCH t.lista_respuesta r")
    List<Topico> obtenerTopicosConRespuestas();

    @Query ("select t from Topico t where t.autor = ?1")
    List<Topico> getTopicos(String username);
}
