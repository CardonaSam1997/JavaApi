package com.guzman.cursos.Service;
import com.guzman.cursos.Entity.Curso;
import com.guzman.cursos.Repository.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CursoServiceImp implements CursoService{
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
}
