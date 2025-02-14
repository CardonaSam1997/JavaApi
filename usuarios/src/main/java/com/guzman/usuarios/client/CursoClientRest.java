package com.guzman.usuarios.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "cursos", url = "${config.url.client}")
public interface CursoClientRest {

    @DeleteMapping("/eliminar-cursoUsuario/{id}")
    void eliminarCursoUsuarioPorId(@PathVariable Integer id);
}
