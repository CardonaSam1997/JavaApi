package com.guzman.cursos.client;
import com.guzman.cursos.models.Usuario;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@FeignClient(name = "usuarios", url = "usuariosprueba:8001/Usuarios")
//el name hace referencia al nombre del microservicio que se va consumir
public interface UsuarioClientRest {
/*
El nombre puede ser cualquiera o el mismo, pero la firma debe ser la misma
 */


    @GetMapping("/{id}")
    Usuario UsuarioPorId(@PathVariable int id);
    //public ResponseEntity<?> UsuarioPorId(@PathVariable int id){

    @PostMapping
    Usuario guardar(@RequestBody Usuario usuario); //la validacion bindingResult se hace en el controlador
    //public ResponseEntity<?> guardar(@RequestBody @Valid Usuario usuario, BindingResult result){//bindinresult debe ir despues del objeto que queremos validar


    @GetMapping("/usuarios-por-cursos")
    //el nombre del requestParam es el parametro que se debe poner en la url para poder pasar los datos
    public ResponseEntity<?> obtenerUsuariosPorCurso(@RequestParam(name = "ids") List<Integer> listaIds);
}
