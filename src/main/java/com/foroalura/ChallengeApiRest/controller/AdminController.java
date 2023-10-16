package com.foroalura.ChallengeApiRest.controller;

import com.foroalura.ChallengeApiRest.jwt.JwtAuthenticationFilter;
import com.foroalura.ChallengeApiRest.jwt.JwtService;
import com.foroalura.ChallengeApiRest.model.Usuario;
import com.foroalura.ChallengeApiRest.service.IRespuestaService;
import com.foroalura.ChallengeApiRest.service.ITopicoService;
import com.foroalura.ChallengeApiRest.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    IUsuarioService iUsuarioService;
    @Autowired
    ITopicoService iTopicoService;

    @Autowired
    IRespuestaService iRespuestaService;

    @DeleteMapping(value = "/borrar/usuario/{id}")
    public ResponseEntity<String> deleteUsuario(@PathVariable Long id){
        iUsuarioService.deleteUsuario(id);
        return ResponseEntity.ok("Usuario con el id: " + id + " fue eliminado");
    }


    @DeleteMapping(value= "/borrar/topico/{id}")
    public ResponseEntity<String> deleteTopico(@PathVariable Long id){

        iTopicoService.deleteTopico(id);

        return ResponseEntity.ok("Topico Eliminado");

    }

    @DeleteMapping(value= "/borrar/respuesta/{id}")
    public ResponseEntity<String> deleteRespuesta(@PathVariable Long id){

        iRespuestaService.deleteRespuesta(id);

        return ResponseEntity.ok("Respuesta Eliminada");

    }


}
