package com.example.demo.service;

import com.example.demo.model.Usuario;


import java.util.List;

public interface Usuarioservice {
    List<Usuario> obtenerTodosLosUsuarios();
    boolean anadirUsuario(String nombre, String apellido, String correo, String contrasena, int privilegio);
    boolean eliminarUsuario(Long id);
    boolean actualizarUsuario(Long id,String nombre, String apellido, String correo, String contrasena, int privilegio);
    boolean existeElUsuario(Long id);
    List<Usuario> obtenerUnUsuarioporID(Long id);
    List<Usuario> obtenerUnUsuarioPorEmail(String email);
}
