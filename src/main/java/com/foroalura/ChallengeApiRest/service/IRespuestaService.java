package com.foroalura.ChallengeApiRest.service;

import com.foroalura.ChallengeApiRest.jwt.JwtService;
import com.foroalura.ChallengeApiRest.model.Respuesta;

import java.util.List;

public interface IRespuestaService {

    //Metodo para traer a todas las Respuestas de la Base de Datos
    public List<Respuesta> getRespuestas();

    //Metodo para dar de alta a un Respuesta
    public void saveRespuesta(Respuesta respue);

    //Metodo para guardar varios Respuestas

    public void saveRespuesta(List<Respuesta> listaRespuestas);

    //Metodo para borrar a una Respuesta
    public void deleteRespuesta(Long id);

    public boolean usuarioConPermiso(String username, Long id);

    //Metodo para traer a una solo Respuesta
    public Respuesta findRespuesta(long id);

    //Metodo para modificar la Respuesta, debe recibir el ID original para buscarlo con el metodo Find y despues setear los nuevos valores.
    public void editRespuesta(long idOriginal, String texto_respuesta);

    void editRespuesta(Respuesta respu, Long idTopico, Long idRespuesta);

    Respuesta saveRespuestaUser(Respuesta respuesta, long idTopico, JwtService jwtService, String token);
}
