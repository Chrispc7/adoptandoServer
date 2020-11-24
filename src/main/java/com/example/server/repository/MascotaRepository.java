package com.example.server.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.server.models.Mascota;

// Lo que hace esta interface es agregar un CRUD al modelo 
public interface MascotaRepository extends JpaRepository<Mascota, Long> {
    List<Mascota> findBySexo(char sexo);
    List<Mascota> findByNombre(String nombre);
    
}
