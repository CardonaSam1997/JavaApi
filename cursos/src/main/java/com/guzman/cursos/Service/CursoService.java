package com.guzman.cursos.Service;
import com.guzman.cursos.models.Entity.Curso;
import com.guzman.cursos.models.Usuario;

import java.util.List;
import java.util.Optional;
//LOGICA DEL NEGOCIO
//firma de los metodos que se deben de crear
public interface CursoService {
    List<Curso> obtenerCursos();

    Optional<Curso> porId(int id);

    Curso guardar(Curso curso);

    void eliminar(int id);

    //METODOS DE CLASE INTERMEDIA
    //al usuario 'x' le asignamos el curso pasado por id
    Optional<Usuario> crearUsuario(Usuario usuario, Integer idCurso);

    Optional<Usuario> asignarUsuario(Usuario usuario, Integer idCurso);

    Optional<Usuario> eliminarUsuario(Usuario usuario, Integer idCurso);

    void eliminarCursoUsuarioPorId(Integer id);
}
