package com.guzman.cursos.Controller;
import com.guzman.cursos.Service.CursoServiceImp;
import com.guzman.cursos.models.Entity.Curso;
import com.guzman.cursos.models.Usuario;
import feign.FeignException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/cursos")
public class CursoController {

    @Autowired
    private CursoServiceImp cursoServiceImp;

    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody @Valid Curso curso, BindingResult result) {
        if (result.hasErrors()) {
            return validarErores(result);
        }
        Curso cursoDB = cursoServiceImp.guardar(curso);
        return ResponseEntity.status(HttpStatus.CREATED).body(cursoDB);
    }

    @GetMapping
    public ResponseEntity<List<Curso>> listar() {
        return ResponseEntity.ok(cursoServiceImp.obtenerCursos());
    }

    @GetMapping("{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable int id) {
        Optional<Curso> o = cursoServiceImp.porId(id);
        if (o.isPresent()) {
            return ResponseEntity.ok(o.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<?> editar(@RequestBody Curso curso, BindingResult result,@PathVariable int id) {
        if (result.hasErrors()) {
            return validarErores(result);
        }
        Optional<Curso> o = cursoServiceImp.porId(id);//obtenemos el curso
        if (o.isPresent()) {//verificamos si existe
            Curso cursoDB = o.get(); //almacenamos el valor en un cursoDB
            cursoDB.setName(curso.getName());//cambiamos el nombre
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(cursoServiceImp.guardar(cursoDB));
        }
        return ResponseEntity.notFound().build();

    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> eliminar(@PathVariable int id) {
        Optional<Curso> o = cursoServiceImp.porId(id);
        if (o.isPresent()) {
            cursoServiceImp.eliminar(o.get().getId());
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

                     //METODOS DE LA TABLA INTERMEDIA
    @PutMapping("asignar-usuario/{idCurso}")
    public ResponseEntity<?> asignarUsuario(@RequestBody Usuario usuario, @PathVariable Integer idCurso){
        Optional<Usuario> o;
        try{
            o = cursoServiceImp.asignarUsuario(usuario,idCurso);
        }catch (FeignException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Collections.singletonMap("mensaje","No existe el usuario " +
                            "o error en la comunicacion"
                    + ex.getMessage()));
        }
        if(o.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).body(o.get());
        }
        return ResponseEntity.notFound().build();
    }

    //No hay validacion para tomar acciones si el usuario ya existe!!-----
    @PostMapping("crear-usuario/{idCurso}")
    public ResponseEntity<?> guardarUsuario(@RequestBody Usuario usuario, @PathVariable Integer idCurso){
        Optional<Usuario> o;
        try{
            o = cursoServiceImp.crearUsuario(usuario,idCurso);
        }catch (FeignException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Collections.singletonMap("mensaje","No se pudo crear el usuario " +
                            "o error en la comunicacion"
                            + ex.getMessage()));
        }
        if(o.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).body(o.get());
        }
        return ResponseEntity.notFound().build();
    }


    //sale error si no colocamos el ID!! ----
    @DeleteMapping("eliminar-usuario/{idCurso}")
    public ResponseEntity<?> eliminarUsuario(@RequestBody Usuario usuario, @PathVariable Integer idCurso){
        Optional<Usuario> o;
        try{
            o = cursoServiceImp.eliminarUsuario(usuario,idCurso);
        }catch (FeignException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Collections.singletonMap("mensaje","No se pudo crear el usuario " +
                            "o error en la comunicacion"
                            + ex.getMessage()));
        }
        if(o.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(o.get());
        }
        return ResponseEntity.notFound().build();
    }


    @DeleteMapping("/eliminar-cursoUsuario/{id}")
    public ResponseEntity<?> eliminarCursoUsuarioPorId(@PathVariable Integer id){
        cursoServiceImp.eliminarCursoUsuarioPorId(id);
        return ResponseEntity.ok().build();
    }

@GetMapping("/prueba999")
    public List<Curso> obtenerTodosCompletos(){
    System.out.println("${config.url.client}");
        List<Curso> cursos = new ArrayList<>();
        try{
            cursos = cursoServiceImp.obtenerTodosCompletos();
        }catch (ClassCastException e){
            System.err.println(e.getMessage());
        }
        return cursos;
    }

    private static ResponseEntity<Map<String, String>> validarErores(BindingResult result) {
        //convertir a JSON
        Map<String, String> errores = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errores.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errores);
    }
}
