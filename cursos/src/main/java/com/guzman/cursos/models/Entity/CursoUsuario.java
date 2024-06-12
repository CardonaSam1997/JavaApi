package com.guzman.cursos.models.Entity;

import com.guzman.cursos.Repository.CursoUsuarioRepository;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Table(name = "Cursos_usuarios")
@Data
public class CursoUsuario {
    /*
    Creo que aqui tengo un error,
    esta clase solo tiene 2 propiedades
    su id y el id del usuario, pero creo que le hace falta
    indicar el id de curso
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "id_usuario_CU", unique = true)
    private Integer idusuario;

    //el equals se modifico para que su verficiacion fuera por el idUusuario
    // y no por instancia del objeto
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
