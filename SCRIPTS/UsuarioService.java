package com.castores.inventario.service;

import java.util.List;
import java.util.Optional;

import com.castores.inventario.model.Usuario;

public interface UsuarioService {

    Usuario guardarUsuario(Usuario usuario);

    Optional<Usuario> buscarPorCorreo(String correo);

    List<Usuario> listarUsuarios();

    void eliminarUsuario(Long id);

    boolean existeCorreo(String correo);
}