package com.tecsup.pulsesafe.service;

import com.tecsup.pulsesafe.model.Notificacion;
import com.tecsup.pulsesafe.repository.NotificacionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificacionService {

    private final NotificacionRepository notificacionRepository;

    public NotificacionService(NotificacionRepository notificacionRepository) {
        this.notificacionRepository = notificacionRepository;
    }

    /**
     * Crea (y persiste) una notificación en la base de datos.
     * Se asume que el objeto Notificacion ya contiene el usuario y los datos básicos.
     */
    public Notificacion crearNotificacionAlerta(Notificacion notificacion) {
        // Opcional: ajusta campos si necesitas valores por defecto
        if (notificacion.getTimestamp() == null) {
            notificacion.setTimestamp(LocalDateTime.now());
        }
        // Si quieres asegurar estado de envío por defecto
        // notificacion.setEstadoEnvio(true);

        return notificacionRepository.save(notificacion);
    }

    /**
     * Retorna todas las notificaciones asociadas a un idUsuario específico.
     */
    public List<Notificacion> obtenerNotificacionesPorUsuario(Long idUsuario) {
        return notificacionRepository.findByUsuarioIdUsuario(idUsuario);
    }
}
