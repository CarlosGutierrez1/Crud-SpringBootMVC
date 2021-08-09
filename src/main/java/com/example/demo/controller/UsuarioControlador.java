package com.example.demo.controller;

import com.example.demo.repository.Usuariorepositorio;

import com.example.demo.model.Usuario;
import com.example.demo.service.Usuarioservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UsuarioControlador {
    @Autowired
    private Usuariorepositorio usuariorepo;

    @Autowired
    private Usuarioservice usuarioservice;

    private ArrayList<Usuario> usuarios;

    private ArrayList<Usuario> usuarioslist;

    private List<Usuario> usuariosraw = null;



    @GetMapping(value = "/")
    public String getUsers(Model model){
        usuarios = new ArrayList<Usuario>();
        usuariosraw = usuarioservice.obtenerTodosLosUsuarios();
        for(Usuario usr : usuariosraw){
            usuarios.add(usr);
        }
        model.addAttribute("usuarios",usuarios);
        return "index";
    }

    @PostMapping(value = "/eliminaruser")
    public String eliminarUsuarios(@RequestParam("id2") Long id, Model model, RedirectAttributes redirAttrs){
        boolean estado = usuarioservice.eliminarUsuario(id);

        if (estado){
            redirAttrs.addFlashAttribute("success", "Se elimino el usuario exitosamente");
        }else {
            redirAttrs.addFlashAttribute("error", "No se pudo eliminar el usuario. Intente de nuevo.");
        }
        return "redirect:/";
    }

    @PostMapping(value = "/goToactualizar")
    public String goToActualizar(Model model, @RequestParam("id") Long id){

        usuarios = new ArrayList<Usuario>();
        usuariosraw = usuarioservice.obtenerUnUsuarioporID(id);
        for(Usuario usr : usuariosraw){
            usuarios.add(usr);
        }
        model.addAttribute("usuarios",usuarios);

        return "actualizar";
    }

    @PostMapping(value = "/actualizaruser")
    public String actualizarusuario(@RequestParam("id") Long id, @RequestParam("nombre") String nombre, @RequestParam("apellido") String apellido,
                                    @RequestParam("correo") String correo, @RequestParam("contrasena") String contrasena, @RequestParam("privilegio") int privilegio, RedirectAttributes redirAttrs,
                                    Model model){

        boolean estado = usuarioservice.actualizarUsuario(id, nombre, apellido, correo, contrasena, privilegio);
        if (estado){
            redirAttrs.addFlashAttribute("success", "Se actualizo el usuario exitosamente");
        }else {
            redirAttrs.addFlashAttribute("error", "No se pudo actualizar el usuario. Intente de nuevo.");
        }

        return "redirect:/";
    }



    @PostMapping(value = "/anadirUsuario")
    public String anadirNuevoUser(@RequestParam("nombre") String nombre, @RequestParam("apellido") String apellido,@RequestParam("correo") String correo,
                                  @RequestParam("contrasena") String contrasena, @RequestParam("privilegio") int privilegio, Model model, RedirectAttributes redirAttrs){
        boolean estado = usuarioservice.anadirUsuario(nombre, apellido, correo, contrasena, privilegio);
        if (estado){
            redirAttrs.addFlashAttribute("success", "Se añadio el usuario exitosamente");
        }else {
            redirAttrs.addFlashAttribute("error", "No se pudo añadir el usuario. Intente de nuevo.");
        }

        return "redirect:/";

    }

    @PostMapping(value = "/buscarusuarioporcorreo")
    public String buscarUsuarioPorCorreo(@RequestParam("buscarcorreo") String correo, Model model){
        usuarioslist = new ArrayList<Usuario>();
        usuariosraw = usuarioservice.obtenerUnUsuarioPorEmail(correo);
        for(Usuario usr : usuariosraw){
            usuarioslist.add(usr);
        }
        model.addAttribute("usuarioslist",usuarioslist);
        return "mostrarusuario";

    }

    @PostMapping(value = "/eliminartodosusuarios")
    public String eliminarTodosLosUsuarios(RedirectAttributes redirAttrs){
        usuariorepo.deleteAll();
        redirAttrs.addFlashAttribute("success", "Se eliminaron todos los usuarios exitosamente");
        return "redirect:/";
    }

}
