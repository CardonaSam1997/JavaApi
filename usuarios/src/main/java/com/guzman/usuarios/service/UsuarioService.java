package com.guzman.usuarios.service;
import com.guzman.usuarios.entity.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {

    Usuario guardarUsuario(Usuario usuario);

    Optional<Usuario> porId(int id);

    List<Usuario> obtenerUsuarios();

    List<Usuario> obtenerTodosPorId(Iterable<Integer> lista);

    void eliminarUsuario(int id);

    Optional<Usuario> verificarEmail(String email);

    Optional<Usuario> verificarUsuario(String user);
}
