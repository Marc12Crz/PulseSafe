package com.tecsup.pulsesafe.repository;

import com.tecsup.pulsesafe.model.FrecuenciaCardiaca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FrecuenciaCardiacaRepository extends JpaRepository<FrecuenciaCardiaca, Long> {

    List<FrecuenciaCardiaca> findByUsuarioIdUsuario(Long idUsuario);

}
