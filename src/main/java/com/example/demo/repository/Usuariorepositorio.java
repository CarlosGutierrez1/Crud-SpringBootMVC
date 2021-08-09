package com.example.demo.repository;

import com.example.demo.model.Usuario;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Usuariorepositorio extends CrudRepository<Usuario,Long> {
    @Override
    void deleteAll();
}
