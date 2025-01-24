package com.tecsup.pulsesafe.controller;

import com.tecsup.pulsesafe.model.Notificacion;
import com.tecsup.pulsesafe.service.NotificacionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notificaciones")
public class NotificacionController {

    private final NotificacionService notificacionService;

    public NotificacionController(NotificacionService notificacionService) {
        this.notificacionService = notificacionService;
    }

    /**
     * Crea una nueva notificación en el sistema.
     * (Por ejemplo, puedes mandar un body con
     *  {
     *    "tipoAlerta": "...",
     *    "mensaje": "...",
     *    "usuario": { "idUsuario": X }
     *  } )
     */
    @PostMapping
    public ResponseEntity<Notificacion> crearNotificacion(@RequestBody Notificacion notificacion) {
        Notificacion nuevaNotificacion = notificacionService.crearNotificacionAlerta(notificacion);
        return ResponseEntity.ok(nuevaNotificacion);
    }

    /**
     * Obtiene todas las notificaciones de un usuario,
     * dado su idUsuario.
     */
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<Notificacion>> obtenerNotificacionesPorUsuario(@PathVariable Long idUsuario) {
        List<Notificacion> notificaciones = notificacionService.obtenerNotificacionesPorUsuario(idUsuario);
        return ResponseEntity.ok(notificaciones);
    }
}
