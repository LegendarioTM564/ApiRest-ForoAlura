package com.foroalura.ChallengeApiRest.model;

import jakarta.persistence.*;
import lombok.*;



@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Respuesta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_respuesta;
    private String texto_respuesta;
    private String autor;

}
