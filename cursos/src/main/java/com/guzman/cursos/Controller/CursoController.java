package com.guzman.cursos.Controller;

import com.guzman.cursos.Entity.Curso;
import com.guzman.cursos.Service.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/cursos")
public class CursoController {


    @Autowired
    private CursoService cursoService;

    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody Curso curso, BindingResult result) {
        if (result.hasErrors()) {
            return validarErores(result);
        }
        Curso cursoDB = cursoService.guardar(curso);
        return ResponseEntity.status(HttpStatus.CREATED).body(cursoDB);
    }

    @GetMapping
    public ResponseEntity<List<Curso>> listar() {
        return ResponseEntity.ok(cursoService.obtenerCursos());
    }

    @GetMapping("{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable int id) {
        Optional<Curso> o = cursoService.porId(id);
        if (o.isPresent()) {
            return ResponseEntity.ok(o.get());
        }
        return ResponseEntity.notFound().build();
    }

    /*
    no funciona, crea y luego actualiza, ojo
    por eso hay que poner solo el objeto de tipo curso
     */
    @PutMapping("{id}")
    public ResponseEntity<?> editar(@RequestBody Curso curso, BindingResult result, int id) {
        if (result.hasErrors()) {
            return validarErores(result);
        }
        Optional<Curso> o = cursoService.porId(id);//obtenemos el curso
        if (o.isPresent()) {//verificamos si existe
            Curso cursoDB = o.get(); //almacenamos el valor en un cursoDB
            cursoDB.setName(curso.getName());//cambiamos el nombre
            return ResponseEntity.status(HttpStatus.CREATED).body(cursoService.guardar(cursoDB));
        }
        return ResponseEntity.notFound().build();

    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> eliminar(@PathVariable int id) {
        Optional<Curso> o = cursoService.porId(id);
        if (o.isPresent()) {
            cursoService.eliminar(o.get().getId());
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
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
