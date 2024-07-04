package com.guzman.cursos.client;
import com.guzman.cursos.models.Usuario;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@FeignClient(name = "usuarios", url = "${config.url.client}")
//el name hace referencia al nombre del microservicio que se va consumir
public interface UsuarioClientRest {

    @PostMapping("/listarTodos")
    public List<Usuario> listaPorId(@RequestBody List<Integer> ids);

    @GetMapping("/{id}")
    Usuario UsuarioPorId(@PathVariable int id);

    @PostMapping
    Usuario guardar(@RequestBody Usuario usuario); //la validacion bindingResult se hace en el controlador



//no se puede castear, necesito modificar esto desde el controlador de usuarios para que me retorne una lista de usuarios
    @GetMapping("/usuarios-por-cursos")
    public List<Usuario> obtenerUsuariosPorCurso(@RequestParam(name = "ids") List<Integer> listaIds);
}
