package com.example.demo.service;

import com.example.demo.repository.Usuariorepositorio;
import com.example.demo.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioserviceC implements Usuarioservice{
    @Autowired
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private Usuariorepositorio userrepo;


    public void setDataSource(DataSource dataSource){
        this.dataSource = dataSource;
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<Usuario> obtenerTodosLosUsuarios() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        String sql = "SELECT * FROM usuario GROUP by id DESC LIMIT 7";
        List<Usuario> listaDeUsuarios = new ArrayList<Usuario>();
        return jdbcTemplate.query(sql,(rs,rowNum)->
                new Usuario(
                        rs.getLong("id"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("correo"),
                        rs.getString("contrasena"),
                        rs.getInt("privilegio")
                ));

    }

    @Override
    public boolean anadirUsuario(String nombre, String apellido, String correo, String contrasena, int privilegio) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        int estado = jdbcTemplate.update("INSERT into usuario (nombre,apellido,correo,contrasena,privilegio) values (?,?,?,?,?)",nombre,
                apellido,correo,contrasena,privilegio);
        if(estado == 1){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean eliminarUsuario(Long id) {

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        int estado = jdbcTemplate.update("DELETE FROM usuario WHERE id="+id);
        if(estado == 1){
            return true;
        }else{
            return false;
        }

    }

    @Override
    public boolean actualizarUsuario(Long id, String nombre, String apellido, String correo, String contrasena, int privilegio) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        int estado = jdbcTemplate.update("UPDATE usuario SET nombre='"+nombre+"',apellido='"+apellido+"',correo='"+correo+"',contrasena='"+contrasena+"', privilegio="+privilegio+" WHERE id="+id);
        if(estado == 1){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean existeElUsuario(Long id) {
//        String sql = ("SELECT nombre FROM usuario WHERE id="+id+")");
//        int result = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM usuario WHERE id="+id, Integer.class);



//        return result>0;
        return true;
    }
    @Override
    public List<Usuario> obtenerUnUsuarioporID(Long id){
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        String sql = "SELECT * FROM usuario WHERE id="+id;
        List<Usuario> listaDeUsuarios = new ArrayList<Usuario>();
        return jdbcTemplate.query(sql,(rs,rowNum)->
                new Usuario(
                        rs.getLong("id"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("correo"),
                        rs.getString("contrasena"),
                        rs.getInt("privilegio")
                ));
    }

    public List<Usuario>obtenerUnUsuarioPorEmail(String email){
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        String sql = "SELECT * FROM usuario WHERE correo='"+email+"'";
        List<Usuario> listaDeUsuarios = new ArrayList<Usuario>();
        return jdbcTemplate.query(sql,(rs,rowNum)->
                new Usuario(
                        rs.getLong("id"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("correo"),
                        rs.getString("contrasena"),
                        rs.getInt("privilegio")
                ));
    }


}
