package com.guzman.usuarios.service;
import com.guzman.usuarios.entity.Usuario;
import com.guzman.usuarios.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImp implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

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
    @Transactional
    public void eliminarUsuario(int id){
        usuarioRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Usuario> verificarEmail(String email){
        return usuarioRepository.findByEmail(email);
    }

    public Optional<Usuario> verificarUsuario(String user){
        return usuarioRepository.findByUser(user);
    }

}
