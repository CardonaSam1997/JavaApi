package com.guzman.cursos.models;
import lombok.Data;

@Data
public class Usuario {
    private Integer id;
    private String name;
    private String user;
    private String password;
    private String email;
//cuando hablan del pojo hacen referencia a la clase que esta en el paquete entity?

    /*@OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name = "id_usuarios")
    private List<CursoUsuario> cursoUsuarioList;

    public Usuario(){
        cursoUsuarioList = new ArrayList<>();
    }*/
}
