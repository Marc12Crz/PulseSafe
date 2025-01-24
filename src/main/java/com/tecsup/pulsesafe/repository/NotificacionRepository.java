package com.tecsup.pulsesafe.repository;

import com.tecsup.pulsesafe.model.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {

    // Método para obtener todas las notificaciones de un usuario específico
    List<Notificacion> findByUsuarioIdUsuario(Long idUsuario);

}
