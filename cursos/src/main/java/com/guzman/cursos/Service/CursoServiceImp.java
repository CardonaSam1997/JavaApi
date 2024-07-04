package com.guzman.cursos.Service;
import com.guzman.cursos.Repository.CursoUsuarioRepository;
import com.guzman.cursos.client.UsuarioClientRest;
import com.guzman.cursos.models.Entity.Curso;
import com.guzman.cursos.Repository.CursoRepository;
import com.guzman.cursos.models.Entity.CursoUsuario;
import com.guzman.cursos.models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CursoServiceImp implements CursoService{
    @Autowired
    private UsuarioClientRest usuarioClientRest;

    @Autowired
    private CursoRepository cursoRepository;

    @Override
    @Transactional(readOnly = true)//Aun no entiendo bien esta anotacion
    public List<Curso> obtenerCursos() {
        return cursoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Curso> porId(int id) {
        return cursoRepository.findById(id);
    }

    @Override
    @Transactional
    public Curso guardar(Curso curso) {
        return cursoRepository.save(curso);
    }

    @Override
    @Transactional
    public void eliminar(int id) {
        cursoRepository.deleteById(id);
    }

    //METODOS DE LA TABLA INTERMEDIA
    @Override
    @Transactional
    public Optional<Usuario> crearUsuario(Usuario usuario, Integer idCurso) {
        Optional<Curso> cursoOptional = cursoRepository.findById(idCurso);
        if(cursoOptional.isPresent()){
            Usuario usuarioMsvc = usuarioClientRest.guardar(usuario);

            Curso curso = cursoOptional.get();
            CursoUsuario cursoUsuario = new CursoUsuario();
            cursoUsuario.setIdusuario(usuarioMsvc.getId());
            //en que momento estamos agregando el id del curso??
            curso.addCursoUsuario(cursoUsuario);
            cursoRepository.save(curso);
            return Optional.of(usuarioMsvc);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<Usuario> asignarUsuario(Usuario usuario, Integer idCurso) {
        Optional<Curso> cursoOptional = cursoRepository.findById(idCurso);
        if(cursoOptional.isPresent()){
            Usuario usuarioMsvc = usuarioClientRest.UsuarioPorId(usuario.getId());

            Curso curso = cursoOptional.get();
            CursoUsuario cursoUsuario = new CursoUsuario();

            cursoUsuario.setIdusuario(usuarioMsvc.getId());
            //en que momento estamos agregando el id del curso??
            curso.addCursoUsuario(cursoUsuario);//aqui se agrega id curso
            /*
            me sigue pareciendo ilogico, desde curso se usa el metodo addCursoUsuario
            pero ese metodo lo que hace es agregar un objeto de tipo cursoUsuario
            y la clase cursoUsuario explicitamente no tiene el id de curso, ni la relacion
            no logro comprender bien en que momento se guarda
             */
            cursoRepository.save(curso);
            return Optional.of(usuarioMsvc);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<Usuario> eliminarUsuario(Usuario usuario, Integer idCurso) {
        Optional<Curso> cursoOptional = cursoRepository.findById(idCurso);
        if(cursoOptional.isPresent()){
            Usuario usuarioMsvc = usuarioClientRest.UsuarioPorId(usuario.getId());

            Curso curso = cursoOptional.get();
            CursoUsuario cursoUsuario = new CursoUsuario();
            cursoUsuario.setIdusuario(usuarioMsvc.getId());
            //en que momento estamos agregando el id del curso??
            curso.removeCursoUsuario(cursoUsuario);
            cursoRepository.save(curso);
            return Optional.of(usuarioMsvc);
        }
        return Optional.empty();

    }

    @Override
    @Transactional
    public void eliminarCursoUsuarioPorId(Integer id) {
        cursoRepository.eliminarCursoUsuarioPorId(id);
    }

    /**
     * Este metodo obtiene los cursos de la BD
     * y luego la recorre para obtener los ids de usuarios
     * que seran guardados en una lista de enteros para luego
     * volver a recorrer la lista cursos y hacer una comparacion
     * si la clave del map es similar al nombre del curso
     * se hara una consulta BD para meter los usuarios correspondientes
     * a la lista de usuarios que contiene la clase CURSOS
     * @return Lista Cursos
     */
    public List<Curso>obtenerTodosCompletos() {
        // Paso 1: Obtener cursos desde la base de datos
        List<Curso> cursos = cursoRepository.findAll();
        // Paso 2: Crear el Map
        Map<String, List<Integer>> cursoUsuariosMap = cursos.stream()
                .collect(Collectors.toMap(
                        Curso::getName,
                        curso -> curso.getCursoUsuarioList().stream()
                                .map(CursoUsuario::getIdusuario)
                                .collect(Collectors.toList())
                ));
        try {
            // Paso 3 y 4: Asignar los usuarios a cada curso
            cursos.forEach(curso -> {
                List<Integer> idsUsuarios = cursoUsuariosMap.get(curso.getName());

                List<Usuario> usuarios = usuarioClientRest.obtenerUsuariosPorCurso(idsUsuarios);
                curso.setUsuarios(usuarios);
            });
        }catch(ClassCastException e){
            System.err.println(e.getMessage()+"--------\n");
             new RuntimeException(e.getMessage());
        }
        return cursos;
    }



}
