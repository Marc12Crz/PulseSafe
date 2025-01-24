package com.tecsup.pulsesafe.service;

import com.tecsup.pulsesafe.model.FrecuenciaCardiaca;
import com.tecsup.pulsesafe.model.Notificacion;
import com.tecsup.pulsesafe.model.Usuario;
import com.tecsup.pulsesafe.repository.FrecuenciaCardiacaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FrecuenciaCardiacaService {

    private final FrecuenciaCardiacaRepository frecuenciaCardiacaRepository;
    private final NotificacionService notificacionService;

    public FrecuenciaCardiacaService(FrecuenciaCardiacaRepository frecuenciaCardiacaRepository,
                                     NotificacionService notificacionService) {
        this.frecuenciaCardiacaRepository = frecuenciaCardiacaRepository;
        this.notificacionService = notificacionService;
    }

    public FrecuenciaCardiaca guardarYDetectarAnomalias(FrecuenciaCardiaca freq) {

        FrecuenciaCardiaca saved = frecuenciaCardiacaRepository.save(freq);

        float pulso = saved.getValorPulso();
        Usuario usuario = saved.getUsuario();

        if (pulso < 60) {
            crearNotificacion(usuario, "Bradicardia", "Pulso demasiado bajo: " + pulso + " BPM");
        } else if (pulso > 120) {
            crearNotificacion(usuario, "Taquicardia", "Pulso demasiado alto: " + pulso + " BPM");
        } else if (pulso >= 90 && pulso <= 120) {
            crearNotificacion(usuario, "Estrés elevado", "Pulso en rango de estrés: " + pulso + " BPM");
        }

        return saved;
    }

    private void crearNotificacion(Usuario usuario, String tipoAlerta, String mensaje) {
        // 1. Crear una instancia de Notificacion
        Notificacion noti = new Notificacion();
        noti.setUsuario(usuario);
        noti.setTipoAlerta(tipoAlerta);
        noti.setMensaje(mensaje);

        // 2. Llama al método del servicio que recibe Notificacion
        notificacionService.crearNotificacionAlerta(noti);
    }

    public List<FrecuenciaCardiaca> listarPorUsuario(Long idUsuario) {
        return frecuenciaCardiacaRepository.findByUsuarioIdUsuario(idUsuario);
    }
}
