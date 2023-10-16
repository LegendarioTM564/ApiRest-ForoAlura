package com.foroalura.ChallengeApiRest.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.type.TrueFalseConverter;


import java.util.Date;
import java.util.List;



@Builder
@Setter @Getter
@AllArgsConstructor @NoArgsConstructor
@Entity
public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_topico;
    @Column(nullable = false)
    private String titulo;
    @Column(nullable = false)
    private String mensaje;
    private String autor;
    @Temporal(TemporalType.DATE)
    private Date fecha_creacion;
    @Convert(converter = TrueFalseConverter.class)
    private boolean estado;


    @ManyToMany(cascade = CascadeType.REMOVE)
    @JoinTable(name="topico_respuesta",
    joinColumns = @JoinColumn(name = "topico_id"),
    inverseJoinColumns = @JoinColumn(name = "respuesta_id"))
    private List<Respuesta> lista_respuesta;


    }

