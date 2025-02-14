package com.guzman.usuarios.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "usuarios")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usuario implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Basic //por defecto es basic
    @NotBlank
    private String name;
    @NotBlank //@NotNull para cualquier otro tipo de dato diferente a string
    private String user;
    @Column(length = 15)
    @NotBlank //pero imagino que permite espacios en blanco entre palabras
    private String password;
    @Column(unique = true)
    @Email
    private String email;
/**
 * es obligatorio crear una clase para la validacion o se puede hacer en la entidad sin ningun problema?
 *
 */
}