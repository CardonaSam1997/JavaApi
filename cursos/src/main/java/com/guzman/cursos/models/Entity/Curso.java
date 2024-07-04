package com.guzman.cursos.models.Entity;
import com.guzman.cursos.models.Usuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cursos")
@Data
@AllArgsConstructor
public class Curso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Basic
    //@NotEmpty pero permite espacios en blanco
    @NotBlank //no permite espacios en blanco, ni valores nulos
    private String name;

    //FK CURSOS
    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name = "id_cursos")
    private List<CursoUsuario> cursoUsuarioList;

    @Transient
    private List<Usuario> usuarios;

    public Curso(){
        cursoUsuarioList = new ArrayList<>();
        usuarios = new ArrayList<>();
    }

    public void addCursoUsuario(CursoUsuario cursoUsuario){
        cursoUsuarioList.add(cursoUsuario);
    }

    public void removeCursoUsuario(CursoUsuario cursoUsuario){
        cursoUsuarioList.remove(cursoUsuario);
    }
}
