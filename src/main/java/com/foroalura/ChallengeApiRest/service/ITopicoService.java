package com.foroalura.ChallengeApiRest.service;

import com.foroalura.ChallengeApiRest.jwt.JwtService;
import com.foroalura.ChallengeApiRest.model.Respuesta;
import com.foroalura.ChallengeApiRest.model.Topico;

import java.util.List;

public interface ITopicoService {


    //Metodo para traer a todas los topicos de la Base de Datos
    public List<Topico> getTopicos();

    //Metodo para dar de alta a un topico
    public void saveTopico(Topico topic);

    public void saveTopicUser(Topico topico, JwtService jwtService, String token);

    //Metodo para guardar varios topicos

    public void saveTopicos(List<Topico> listaTopicos);

    //Metodo para borrar a un topico
    public void deleteTopico(Long id);

    public boolean usuarioConPermiso(String username, Long id);

    //Metodo para traer a una solo topico
    public Topico findTopico(long id);

    //Metodo para modificar los datos de un topico, debe recibir el ID original para buscarlo con el metodo Find y despues setear los nuevos valores.
    public void editTopico(long idOriginal, String titulo, String mensaje);

    void editTopico(Topico topic);
}
