package com.guzman.cursos.models.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.guzman.cursos.Repository.CursoUsuarioRepository;
import com.guzman.cursos.models.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Cursos_usuarios")
@Data
@ToString
public class CursoUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "id_usuario_CU", unique = true)
    private Integer idusuario;

    public CursoUsuario(){

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true; //si la instancia es la misma
        if(!(o instanceof CursoUsuario)){
            return false;
        }
        CursoUsuario ob = (CursoUsuario) o;
        return this.idusuario != null && this.idusuario.equals(ob.idusuario);
    }
}