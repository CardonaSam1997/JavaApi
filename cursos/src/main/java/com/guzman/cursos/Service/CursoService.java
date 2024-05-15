package com.guzman.cursos.Service;
import com.guzman.cursos.Entity.Curso;
import java.util.List;
import java.util.Optional;


public interface CursoService {
    List<Curso> obtenerCursos();

    Optional<Curso> porId(int id);

    Curso guardar(Curso curso);

    void eliminar(int id);
}
