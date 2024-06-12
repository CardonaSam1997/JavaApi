package com.guzman.cursos.Repository;
import com.guzman.cursos.models.Entity.CursoUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CursoUsuarioRepository extends JpaRepository<CursoUsuario,Integer> {


}
