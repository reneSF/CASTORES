package com.castores.inventario.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.castores.inventario.model.Usuario;
import com.castores.inventario.model.Rol;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Buscar usuario por email (login)
    Optional<Usuario> findByEmail(String email);

    // Validar si existe un correo
    boolean existsByEmail(String email);

    // Buscar por rol (ADMIN / ALMACENISTA)
    Optional<Usuario> findByRol(Rol rol);
}