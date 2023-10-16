package com.foroalura.ChallengeApiRest.controller;

import com.foroalura.ChallengeApiRest.jwt.JwtAuthenticationFilter;
import com.foroalura.ChallengeApiRest.jwt.JwtService;
import com.foroalura.ChallengeApiRest.model.Respuesta;
import com.foroalura.ChallengeApiRest.model.Topico;
import com.foroalura.ChallengeApiRest.model.Usuario;
import com.foroalura.ChallengeApiRest.repository.IRespuestaRepository;
import com.foroalura.ChallengeApiRest.repository.ITopicoRepository;
import com.foroalura.ChallengeApiRest.repository.IUsuarioRepository;
import com.foroalura.ChallengeApiRest.service.IRespuestaService;
import com.foroalura.ChallengeApiRest.service.ITopicoService;
import com.foroalura.ChallengeApiRest.service.IUsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/private")
public class PrivateController {

    @Autowired
    ITopicoService iTopicoService;
    @Autowired
    private IRespuestaService iRespuestaService;
    @Autowired
    ITopicoRepository iTopicoRepository;
    @Autowired
    JwtService jwtService;
    @Autowired
    JwtAuthenticationFilter jwtAuthenticationFilter;
    @Autowired
    IUsuarioRepository iUsuarioRepository;
    @Autowired
    IUsuarioService iUsuarioService;
    @Autowired
    IRespuestaRepository iRespuestaRepository;
    @Autowired
    PasswordEncoder passwordEncoder;



    @GetMapping(value = "/leer/topicos")
    @Operation(summary = "Permite leer los topicos",
            description = "Permite leer los topcios creados por los usuarios, pero no podra interactuar con ninguno de ellos si no esta logueado."
    )
    public List<Topico> getTopicos(){

        return iTopicoRepository.obtenerTopicosConRespuestas();
    }

    @GetMapping(value = "/topico/{id}")
    @Operation(summary = "Busca un topico por su ID.",
            description = "Permite leer un topico en particular, llamandolo por su ID."
    )
    public ResponseEntity<Topico> getTopico(@PathVariable Long id){

        return ResponseEntity.ok(iTopicoService.findTopico(id));
    }

    @PostMapping(value = "/crear/topico")
    @Operation(summary = "Crea y guarda el topico",
            description = "Guarada el topico en la base de datos, los unicos datos que debe modificar en el Json son:" +
                    "Titulo, Mensaje." +
                    "Los demas datos se cargan automaticamente."

    )
    public ResponseEntity<String> createTopico(@RequestBody Topico topico){
        String token = jwtAuthenticationFilter.getToken();
        String username = jwtService.getUsernameFromToken(token);
        Usuario usu = iUsuarioRepository.getUsername(username);

        if (usu != null){
            iTopicoService.saveTopicUser(topico,jwtService,token);
            List<Topico> Listatopic = iTopicoRepository.getTopicos(username);
            usu.setLista_topicos(Listatopic);
            iUsuarioService.editUsuario2(usu);
            return  ResponseEntity.ok("Topico guardado");

        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }

    }

    @PostMapping(value = "/{id}/respuesta")
    @Operation(summary = "Crea y guarda una respuesta",
            description = "Primero selecciona el topico por id y a ese topico le agrega la respuesta, los unicos datos que debe modificar en el Json son:" +
                    "Texto_Respuesta" +
                    "Los demas datos se cargan automanticamente."

    )
    public ResponseEntity<String> createRespuesta(@PathVariable Long id, @RequestBody Respuesta respuesta){
        Topico topico = iTopicoService.findTopico(id);
        String token = jwtAuthenticationFilter.getToken();
        String username = jwtService.getUsernameFromToken(token);
        Usuario usu = iUsuarioRepository.getUsername(username);
        if(topico != null && usu != null){
            Respuesta respu = iRespuestaService.saveRespuestaUser(respuesta,id, jwtService, token);
            return ResponseEntity.ok("Respuesta guardada");
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Topico o usuario no encontrado");
        }

    }




    @PutMapping(value = "/editar/usuario/{idUsuario}")
    @Operation(summary = "Modificación de usuario",
            description = "Busca un usuario por su ID, corrobora si el usuario logueado es el mismo al que se quiere editar." +
                    "Si es asi, permite la edición del mismo." +
                    "los unicos datos que debe modificar en el Json son:" +
                    "Nombre, Apellido, Curso, Username, Password y Email." +
                    "Los demas datos se cargan automaticamente."

    )
    public ResponseEntity<String> editUsuario(@RequestBody Usuario usuarioActualizado, @PathVariable Long idUsuario){
        String token = jwtAuthenticationFilter.getToken();
        String username = jwtService.getUsernameFromToken(token);
        Usuario usu = iUsuarioRepository.getUsername(username);
        List<Topico> listaDeTopicos = iTopicoRepository.getTopicos(username);
        List<Respuesta> listaDeRespuesta = iRespuestaRepository.getRespuestas(username);

        if(iUsuarioService.usuarioConPermiso(username,idUsuario)){
            Usuario usuarioExistente = iUsuarioService.findUsuario(idUsuario);
            usuarioExistente.setNombre(usuarioActualizado.getNombre());
            usuarioExistente.setApellido(usuarioActualizado.getApellido());
            usuarioExistente.setCurso(usuarioActualizado.getCurso());
            usuarioExistente.setUsername(usuarioActualizado.getUsername());

            if (usuarioActualizado.getPassword() != null){
                usuarioExistente.setPassword(passwordEncoder.encode(usuarioActualizado.getPassword()));

            }


            for (Topico topico : listaDeTopicos){
                topico.setAutor(usuarioActualizado.getUsername());
            }

            for (Respuesta respuesta : listaDeRespuesta){
                respuesta.setAutor(usuarioExistente.getUsername());
            }

            iUsuarioService.editUsuario2(usuarioExistente);
            iTopicoService.saveTopicos(listaDeTopicos);
            iRespuestaService.saveRespuesta(listaDeRespuesta);
            return ResponseEntity.ok("Usuario modificado");
        }else{

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no autorizado");
        }

    }

    @PutMapping(value = "/editar/topico/{id}")
    @Operation(summary = "Modificación de topico",
            description = "Busca un topico por su ID, corrobora si el usuario logueado es el autor del topico a editar" +
                    "Si es asi, permite la edición del mismo." +
                    "los unicos datos que debe modificar en el Json son:" +
                    "Titulo y mensaje."

    )
    public ResponseEntity<String> editTopico(@RequestBody Topico topico, @PathVariable Long id){
        String token = jwtAuthenticationFilter.getToken();
        String username = jwtService.getUsernameFromToken(token);
        Usuario usu = iUsuarioRepository.getUsername(username);

        if(iTopicoService.usuarioConPermiso(username,id)){

            iTopicoService.saveTopicUser(topico,jwtService,token);

            return ResponseEntity.ok("Topico editado con exito");

        }else {

            return   ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no autorizado");
        }

    }

    @PutMapping(value = "/editar/topico/{idTopico}/respuestas/{idRespuesta}")
    @Operation(summary = "Modificación de respuesta",
            description = "Busca un topico por su ID y seleciona la respuesta a editar por su ID, corrobora si el usuario logueado es el autor de la respuesta a editar" +
                    "Si es asi, permite la edición del mismo." +
                    "los unicos datos que debe modificar en el Json son:" +
                    "Texto_Respuesta"

    )
    public ResponseEntity<String> editRespuesta(@RequestBody Respuesta respuesta, @PathVariable Long idRespuesta, @PathVariable Long idTopico) {
        String token = jwtAuthenticationFilter.getToken();
        String username = jwtService.getUsernameFromToken(token);
        Usuario usu = iUsuarioRepository.getUsername(username);

        if (iRespuestaService.findRespuesta(idRespuesta) != null) {

            if (iRespuestaService.usuarioConPermiso(username, idRespuesta)) {

                iRespuestaService.editRespuesta(respuesta, idTopico, idRespuesta);

                return ResponseEntity.ok("Respuesta editada con exito");

            }else {

                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No puede editar esta respuesta");
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Topico o respuesta no encontrada");
    }



    @DeleteMapping(value= "/borrar/topico/{id}")
    @Operation(summary = "Borrar topico",
            description = "Busca un topico por su ID, corrobora si el usuario logueado es el autor del topico a editar" +
                    "Si es asi, permite la edición del mismo."
    )
    public ResponseEntity<String> deleteTopico(@PathVariable Long id){
        String token = jwtAuthenticationFilter.getToken();
        String username = jwtService.getUsernameFromToken(token);
        Usuario usu = iUsuarioRepository.getUsername(username);

        if (iTopicoService.usuarioConPermiso(username,id)){

            iTopicoService.deleteTopico(id);

            return ResponseEntity.ok("Topico Eliminado");
        }else {

            return ResponseEntity.ofNullable("No puede eliminar este topico");

        }

    }

    @DeleteMapping(value= "/borrar/respuesta/{id}")
    @Operation(summary = "Borrar respuesta",
            description = "Busca una respuesta por su ID, corrobora si el usuario logueado es el autor de la respuesta a editar" +
                    "Si es asi, permite la edición del mismo."
    )
    public ResponseEntity<String> deleteRespuesta(@PathVariable Long id){
        String token = jwtAuthenticationFilter.getToken();
        String username = jwtService.getUsernameFromToken(token);
        Usuario usu = iUsuarioRepository.getUsername(username);

        if (iRespuestaService.usuarioConPermiso(username,id)){

            iRespuestaService.deleteRespuesta(id);
            return ResponseEntity.ok("Respuesta Eliminada");
        }else {

            return ResponseEntity.ofNullable("No puede eliminar esta respuesta");

        }

    }


}



