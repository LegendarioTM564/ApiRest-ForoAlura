package com.foroalura.ChallengeApiRest.service;

import com.foroalura.ChallengeApiRest.jwt.JwtService;
import com.foroalura.ChallengeApiRest.model.Respuesta;
import com.foroalura.ChallengeApiRest.model.Topico;
import com.foroalura.ChallengeApiRest.repository.IRespuestaRepository;
import com.foroalura.ChallengeApiRest.repository.ITopicoRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RespuestaService implements IRespuestaService{

    @Autowired
    private IRespuestaRepository iRespuestaRepository;
    @Autowired
    private ITopicoRepository iTopicoRepository;
    @Autowired
    EntityManager entityManager;

    @Override
    public List<Respuesta> getRespuestas() {
        List<Respuesta> listaRespuestas = iRespuestaRepository.findAll();
        return listaRespuestas;
    }

    @Override
    public void saveRespuesta(Respuesta respue) {
        iRespuestaRepository.save(respue);
    }

    @Override
    public void saveRespuesta(List<Respuesta> listaRespuestas) {
        iRespuestaRepository.saveAll(listaRespuestas);
    }

    @Override
    public boolean usuarioConPermiso(String username, Long id) {
        Optional<Respuesta> respuestaOptional = iRespuestaRepository.findById(id);

        if(respuestaOptional.isPresent()){
            Respuesta respu = respuestaOptional.get();
            return respu.getAutor().equals(username);

        }
        return false;
    }

    @Override
    @Transactional
    public void deleteRespuesta(Long id) {

        String sqlDelete = "DELETE FROM topico_respuesta WHERE respuesta_id = :id";
        entityManager.createNativeQuery(sqlDelete)
                .setParameter("id", id)
                .executeUpdate();

        iRespuestaRepository.deleteById(id);
    }

    @Override
    public Respuesta findRespuesta(long id) {
        Respuesta respue = iRespuestaRepository.findById(id).orElse(null);

        return respue;
    }

    @Override
    public void editRespuesta(long idOriginal, String texto_respuesta) {
        Respuesta respue = this.findRespuesta(idOriginal);
        respue.setTexto_respuesta(texto_respuesta);

        this.saveRespuesta(respue);
    }

    @Override
    public void editRespuesta(Respuesta respu, Long idTopico, Long idRespuesta) {

        Optional<Topico> topicoOptional = iTopicoRepository.findById(idTopico);

        if (topicoOptional.isPresent()) {
            Topico topico = topicoOptional.get();

            Respuesta respuesta = topico.getLista_respuesta()
                    .stream()
                    .filter(r -> r.getId_respuesta()==idRespuesta)
                    .findFirst()
                    .orElse(null);

            if(respuesta != null){
                respuesta.setTexto_respuesta(respu.getTexto_respuesta());
                this.saveRespuesta(respuesta);
            }

        }

    }

    @Override
    public Respuesta saveRespuestaUser(Respuesta respuesta, long idTopico, JwtService jwtService, String token) {
        Optional<Topico> topicoOptional = iTopicoRepository.findById(idTopico);

        if(topicoOptional.isPresent()){
            Topico topico = topicoOptional.get();

            Respuesta respue = Respuesta.builder()
                    .id_respuesta(respuesta.getId_respuesta())
                    .texto_respuesta(respuesta.getTexto_respuesta())
                    .autor(jwtService.getUsernameFromToken(token))
                    .build();
            topico.getLista_respuesta().add(respue);

            this.saveRespuesta(respue);

            return respue;
        }else {
            throw new NoResultException("Tópico no encontrado");
        }

    }
}
