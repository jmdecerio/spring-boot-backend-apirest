package com.bolsadeideas.springboot.backend.apirest.models.entity;

import com.bolsadeideas.springboot.backend.apirest.formater.CustomJsonOffsetDateTimeDeserializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.OffsetDateTime;

@Data
@Entity
@Table(name = "clientes")
public class Cliente implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "no puede estar vacío")
    @Size(min = 4, max = 12, message = "debe tener entre 4 y 12 caracteres")
    @Column(nullable = false)
    private String nombre;

    @NotEmpty(message = "no puede estar vacío")
    private String apellido;

    @NotEmpty(message = "no puede estar vacío")
    @Email(message = "debe ser un email correcto")
    @Column(nullable = false, unique = true)
    private String email;

    @NotNull(message = "no puede estar vacia")
    @JsonDeserialize(using = CustomJsonOffsetDateTimeDeserializer.class)
    @Column(name = "created_at", columnDefinition = "TIMESTAMP")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private OffsetDateTime createdAt;

    private String foto;

//    @PrePersist
//    public void prepersist() {
//        createdAt = new Date();
//    }
}
