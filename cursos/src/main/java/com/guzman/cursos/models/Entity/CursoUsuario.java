package com.guzman.cursos.models.Entity;

import com.guzman.cursos.Repository.CursoUsuarioRepository;
import com.guzman.cursos.models.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Table(name = "Cursos_usuarios")
@Data
public class CursoUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "id_usuario_CU", unique = true)
    private Integer idusuario;

    private Usuario usuario;

    public CursoUsuario(){

    }

    public CursoUsuario(Integer id,int idusuario, Usuario usuario){
        this.id = id;
        this.usuario = usuario;
    }

    /*el equals se modifico para que su verficiacion fuera por el idUusuario
     y no por instancia del objeto*/
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
