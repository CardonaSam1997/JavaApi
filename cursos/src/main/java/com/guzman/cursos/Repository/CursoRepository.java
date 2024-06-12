package com.guzman.cursos.Repository;
import com.guzman.cursos.models.Entity.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CursoRepository extends JpaRepository<Curso,Integer> {

    @Modifying
    //@Query solo es para hacer consultas no para modificar, por eso usamos @Modifying
    @Query("DELETE FROM CursoUsuario cu WHERE cu.idusuario = ?1")
    //Los datos del query deben ser iguales que los datos de la clase java
    void eliminarCursoUsuarioPorId(Integer id);

}
