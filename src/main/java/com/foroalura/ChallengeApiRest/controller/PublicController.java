package com.foroalura.ChallengeApiRest.controller;

import com.foroalura.ChallengeApiRest.auth.AuthResponse;
import com.foroalura.ChallengeApiRest.auth.LoginRequest;
import com.foroalura.ChallengeApiRest.auth.RegisterRequest;
import com.foroalura.ChallengeApiRest.model.Topico;
import com.foroalura.ChallengeApiRest.repository.ITopicoRepository;
import com.foroalura.ChallengeApiRest.service.IAuthService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

@RequestMapping("/public")
public class PublicController {

    @Autowired
    IAuthService iAuthService;
    @Autowired
    ITopicoRepository iTopicoRepository;


    @PostMapping(value = "/login")
    @Operation(summary = "Permite al usuario loguearse en la aplicacion",
            description = "El usuario puede loguearse siempre y cuando esta registrado antes."
    )
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request){
        return ResponseEntity.ok(iAuthService.login(request));
    }

    @PostMapping(value = "/register")
    @Operation(summary = "Permite al usuario registrarse en la aplicacion",
            description = "El usuario debera estar registrado para poder acceder a las demas caracteristicas de la aplicación."
    )
    public  ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(iAuthService.register(request));
    }

    @GetMapping(value = "/leer/topicos")
    @Operation(summary = "Permite leer los topicos",
            description = "Permite leer los topcios creados por los usuarios, pero no podra interactuar con ninguno de ellos si no esta logueado."
    )
    public List<Topico> getTopicos(){

        return iTopicoRepository.obtenerTopicosConRespuestas();
    }


}
