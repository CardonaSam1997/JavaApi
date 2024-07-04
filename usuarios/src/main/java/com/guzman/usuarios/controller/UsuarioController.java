package com.guzman.usuarios.controller;
import com.guzman.usuarios.entity.Usuario;
import com.guzman.usuarios.service.UsuarioServiceImp;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("Usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioServiceImp usuarioService;

    @PostMapping("/listarTodos")
    public List<Usuario> listaPorId(@RequestBody List<Integer> ids){
        return usuarioService.listaPorId(ids);
    }

    @PostMapping
    //@ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> guardar(@RequestBody @Valid Usuario usuario, BindingResult result){//bindinresult debe ir despues del objeto que queremos validar
        if(result.hasErrors()){//verificar si hay errores en base a las anotaciones de validacion
            return validarErores(result);
        }
        
        if(usuarioService.verificarEmail(usuario.getEmail()).isPresent()){
            return ResponseEntity.badRequest().body(Collections.singletonMap("error","El correo ya existe"));
        }

        if(usuarioService.verificarUsuario(usuario.getUser()).isPresent()){
            return ResponseEntity.badRequest().body(Collections.singletonMap("error","El usuario ya existe"));
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.guardarUsuario(usuario));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> UsuarioPorId(@PathVariable int id){
        Optional<Usuario> optionalUsuario = usuarioService.porId(id);
        if(optionalUsuario.isPresent()){
            return ResponseEntity.ok(optionalUsuario.get());
        }
        String var = "${P:hola}";
        System.err.println(var);
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public List<Usuario> obtener(){
        return usuarioService.obtenerUsuarios();
    }

    @PutMapping
    //@ResponseStatus(HttpStatus.ACCEPTED) forma corta
    public ResponseEntity<?> actualizar(@RequestBody @Valid Usuario usuario,BindingResult result){
        if(result.hasErrors()){
            return validarErores(result);
        }

        Optional<Usuario> optionalUsuario = usuarioService.porId(usuario.getId());
        if(optionalUsuario.isPresent()){
            Usuario usuarioBD = optionalUsuario.get();
            if(!usuarioBD.getEmail().isEmpty() && usuarioService.verificarEmail(usuario.getEmail()).isPresent()){
                return ResponseEntity.badRequest().body(Collections.singletonMap("error","El email ya existe"));
            }
            usuarioBD.setName(usuario.getName());
            usuarioBD.setUser(usuario.getUser());
            usuarioBD.setEmail(usuario.getEmail());
            usuarioBD.setPassword(usuario.getPassword());
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(usuarioService.guardarUsuario(usuarioBD));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable int id){
        Optional<Usuario> optionalUsuario = usuarioService.porId(id);
        if(optionalUsuario.isPresent()){
            usuarioService.eliminarUsuario(id);
            //realmente la respuesta a una eliminacion es noContent?
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/usuarios-por-cursos")
    //el nombre del requestParam es el parametro que se debe poner en la url para poder pasar los datos
    public List<Usuario> obtenerUsuariosPorCurso(@RequestParam(name = "ids") List<Integer> listaIds){
        return usuarioService.obtenerTodosPorId(listaIds);
    }

    private static ResponseEntity<Map<String, String>> validarErores(BindingResult result) {
        //convertir a JSON
        Map<String,String> errores = new HashMap<>();
        result.getFieldErrors().forEach(err ->{
            errores.put(err.getField(), "El campo "+err.getField()+" "+err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errores);
    }

}