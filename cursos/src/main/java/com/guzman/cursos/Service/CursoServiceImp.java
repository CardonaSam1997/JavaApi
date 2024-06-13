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
import java.util.Optional;

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

    public Curso porId2(int id) {
        Curso curso = cursoRepository.findById(id).get();
        Usuario usuario = usuarioClientRest.UsuarioPorId(curso.getCursoUsuarioList().get(0).getIdusuario());
        curso.getCursoUsuarioList().get(0).setUsuario(usuario);
    //    cursoRepository.findAllById(); //FUNCIONA PERO HAY QUE MEJORARLO
        return curso;
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
            curso.addCursoUsuario(cursoUsuario);
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

}
