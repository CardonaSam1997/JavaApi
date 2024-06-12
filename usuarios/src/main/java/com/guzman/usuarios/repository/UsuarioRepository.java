package com.guzman.usuarios.repository;
import com.guzman.usuarios.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Integer> {

    /*
    Recuerda que hay dos tipos de consultas personalizadas en java
    la primera con unas palabras claves que las podemos encontrar en la guia de jpa data
    y la segunda es para consultas mas complejas en el cual podemos escribir codigo SQL con
    la anotacion @Query(sentencia SQL)
     */

    //RECORDAR USAR OPTIONAL PARA PODER VALIR LA EXISTENCIA DEL OBJETO
    Optional<Usuario> findByEmail(String email);

    //CONSULTA PERSONALIZADA con query
    //@Query("SELECT u FROM usuario u WHERE u.user =?1")
    Optional<Usuario> findByUser(String user);

    //valida si existe
    //hace una consulta pero solo devuelve un valor booleano
    boolean existsByName(String name);
}
