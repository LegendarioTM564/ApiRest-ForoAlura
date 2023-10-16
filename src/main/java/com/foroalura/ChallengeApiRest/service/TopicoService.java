package com.foroalura.ChallengeApiRest.service;

import com.foroalura.ChallengeApiRest.jwt.JwtService;
import com.foroalura.ChallengeApiRest.model.Respuesta;
import com.foroalura.ChallengeApiRest.model.Topico;
import com.foroalura.ChallengeApiRest.repository.ITopicoRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TopicoService implements ITopicoService{

    @Autowired
    private ITopicoRepository iTopicoRepository;
    @Autowired
    EntityManager entityManager;

    @Override
    public List<Topico> getTopicos() {
        List<Topico> listaTopicos = iTopicoRepository.findAll();
        return listaTopicos;
    }

    @Override
    public void saveTopico(Topico topic) {
        iTopicoRepository.save(topic);
    }

    @Override
    public void saveTopicUser(Topico topico, JwtService jwtService, String token) {
        // Obtener la fecha actual
        LocalDate fechaActual = LocalDate.now();

        // Convertir LocalDate a java.sql.Date
        Date fechaFormateada = Date.valueOf(fechaActual);

        Topico topic = Topico.builder()
                .id_topico(topico.getId_topico())
                .titulo(topico.getTitulo())
                .mensaje(topico.getMensaje())
                .autor(jwtService.getUsernameFromToken(token))
                .fecha_creacion(fechaFormateada)
                .estado(true)
                .lista_respuesta(topico.getLista_respuesta())
                .build();

        this.saveTopico(topic);
    }

    @Override
    public void saveTopicos(List<Topico> listaTopicos) {

        iTopicoRepository.saveAll(listaTopicos);
    }

    @Override
    @Transactional
    public void deleteTopico(Long id) {


        String sqlDelete = "DELETE FROM usuario_lista_topicos WHERE lista_topicos_id_topico = :id";
        entityManager.createNativeQuery(sqlDelete)
        .setParameter("id", id)
        .executeUpdate();

        iTopicoRepository.deleteById(id);
    }

    @Override
    public boolean usuarioConPermiso(String username, Long id) {
        Optional<Topico> topicoOptional = iTopicoRepository.findById(id);

        if(topicoOptional.isPresent()){
            Topico topic = topicoOptional.get();
            return topic.getAutor().equals(username);

        }
        return false;
    }

    @Override
    public Topico findTopico(long id) {

        Topico topic = iTopicoRepository.findById(id).orElse(null);

        return topic;
    }

    @Override
    public void editTopico(long idOriginal, String titulo, String mensaje) {
        Topico topic = this.findTopico(idOriginal);
        topic.setTitulo(titulo);
        topic.setMensaje(mensaje);

        this.saveTopico(topic);

    }

    @Override
    public void editTopico(Topico topic) {

        this.saveTopico(topic);

    }
}
