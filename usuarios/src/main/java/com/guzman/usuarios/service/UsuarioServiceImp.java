package com.guzman.usuarios.service;
import com.guzman.usuarios.client.CursoClientRest;
import com.guzman.usuarios.entity.Usuario;
import com.guzman.usuarios.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioServiceImp implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CursoClientRest cursoClientRest;

    @Override
    @Transactional
    public Usuario guardarUsuario(Usuario usuario){
        return usuarioRepository.save(usuario);
    }

    //si el valor es nullo manda un mensaje indicando que el error es nulo y el error es 500 ya que el servidor no sabe como manejarlo
    @Override
    @Transactional(readOnly = true)
    public Optional<Usuario> porId(int id){
        return usuarioRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    //true = solo lectura, mejora el rendimiento de las consultas al saber que no hay modificaciones
    //false = permitir escritura, su estado por defecto es false
    public List<Usuario> obtenerUsuarios(){
        return usuarioRepository.findAll();
    }

    @Override
    public List<Usuario> obtenerTodosPorId(Iterable<Integer> lista) {
        return usuarioRepository.findAllById(lista); //casteo? (List<Usuario>)
    }

    @Override
    @Transactional
    public void eliminarUsuario(int id){
        /*Tiene logica, Si se elimina el usuario se debe de eliminar la relacion entre
        el usuario y el curso
         */
        usuarioRepository.deleteById(id);
        cursoClientRest.eliminarCursoUsuarioPorId(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Usuario> verificarEmail(String email){
        return usuarioRepository.findByEmail(email);
    }

    public Optional<Usuario> verificarUsuario(String user){
        return usuarioRepository.findByUser(user);
    }

    public List<Usuario> listaPorId(List<Integer> ids){
        // Extraer los ids de la lista de usuarios


        // Obtener la lista de usuarios por los ids
        return usuarioRepository.findAllById(ids);
    }

}
