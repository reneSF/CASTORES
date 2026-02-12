package com.castores.inventario.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.castores.inventario.model.Usuario;
import com.castores.inventario.repository.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;

    // Inyección por constructor (best practice)
    public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Usuario guardarUsuario(Usuario usuario) {
        // Validación de negocio
        if (usuarioRepository.existsByCorreo(usuario.getCorreo())) {
            throw new RuntimeException("El correo ya está registrado");
        }
        return usuarioRepository.save(usuario);
    }

    @Override
    public Optional<Usuario> buscarPorCorreo(String Correo) {
        return usuarioRepository.findByCorreo(Correo);
    }

    @Override
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    @Override
    public void eliminarUsuario(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("El id del usuario no puede ser null");
        }
        usuarioRepository.deleteById(id);
    }

    @Override
    public boolean existeCorreo(String Correo) {
        return usuarioRepository.existsByCorreo(Correo);
    }
}