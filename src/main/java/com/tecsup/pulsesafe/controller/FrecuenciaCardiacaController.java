package com.tecsup.pulsesafe.controller;

import com.tecsup.pulsesafe.model.FrecuenciaCardiaca;
import com.tecsup.pulsesafe.model.Usuario;
import com.tecsup.pulsesafe.service.FrecuenciaCardiacaService;
import com.tecsup.pulsesafe.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/frecuencia")
public class FrecuenciaCardiacaController {

    private final FrecuenciaCardiacaService frecuenciaService;
    private final UsuarioService usuarioService;

    public FrecuenciaCardiacaController(FrecuenciaCardiacaService frecuenciaService,
                                        UsuarioService usuarioService) {
        this.frecuenciaService = frecuenciaService;
        this.usuarioService = usuarioService;
    }

    /**
     * Endpoint para registrar una nueva medición de frecuencia cardíaca
     * y, de paso, verificar si hay alguna anomalía (taquicardia, bradicardia, estrés).
     */
    @PostMapping("/{idUsuario}")
    public ResponseEntity<FrecuenciaCardiaca> registrarFrecuencia(
            @PathVariable Long idUsuario,
            @RequestBody FrecuenciaCardiaca freqRequest
    ) {
        // 1. Verificar que el usuario exista
        Usuario usuario = usuarioService.obtenerUsuarioPorId(idUsuario)
                .orElse(null);

        if (usuario == null) {
            return ResponseEntity.badRequest().build();
        }

        // 2. Asignar el usuario a la entidad FrecuenciaCardiaca
        freqRequest.setUsuario(usuario);

        // 3. Guardar y detectar anomalías en el servicio
        FrecuenciaCardiaca guardada = frecuenciaService.guardarYDetectarAnomalias(freqRequest);

        // 4. Retornar la frecuencia cardíaca que se guardó
        return ResponseEntity.ok(guardada);
    }

    /**
     * Endpoint para listar todas las mediciones de frecuencia cardíaca
     * de un usuario específico.
     */
    @GetMapping("/{idUsuario}")
    public ResponseEntity<List<FrecuenciaCardiaca>> obtenerFrecuenciasPorUsuario(@PathVariable Long idUsuario) {
        List<FrecuenciaCardiaca> lista = frecuenciaService.listarPorUsuario(idUsuario);
        return ResponseEntity.ok(lista);
    }
}
