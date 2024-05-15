package com.guzman.usuarios.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
    @Basic
    @NotBlank(message = "El campo nombre debe estar lleno")
    private String name;
    @NotBlank //@NotNull para cualquier otro tipo de dato diferente a string
    private String user;
    @Column(length = 15)
    private String password;
    @Column(unique = true)
    @Email
    private String email;
/**
 * es obligatorio crear una clase para la validacion o se puede hacer en la entidad sin ningun problema?
 *
 */
    /*
    No me permite hacer uso de la clase Cursos ya que esta clase
    esta en un contenedor de docker distinto
     */



}
