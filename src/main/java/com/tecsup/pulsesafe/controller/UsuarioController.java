package com.tecsup.pulsesafe.controller;

import com.tecsup.pulsesafe.model.Usuario;
import com.tecsup.pulsesafe.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<Usuario> registrarUsuario(@RequestBody Usuario usuario) {
        Usuario nuevoUsuario = usuarioService.registrarUsuario(usuario);
        return ResponseEntity.ok(nuevoUsuario);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerUsuarioPorId(@PathVariable Long id) {
        return usuarioService.obtenerUsuarioPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarUsuario(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        Optional<Usuario> optionalUsuario = usuarioService.obtenerUsuarioPorId(id);
        if (optionalUsuario.isPresent()) {
            Usuario usuario = optionalUsuario.get();

            // Actualizar campos según lo que se reciba
            if (updates.containsKey("nombre")) {
                usuario.setNombre((String) updates.get("nombre"));
            }
            if (updates.containsKey("email")) {
                usuario.setEmail((String) updates.get("email"));
            }
            if (updates.containsKey("password")) {
                usuario.setPassword((String) updates.get("password"));
            }
            if (updates.containsKey("telefonoEmergencia")) {
                usuario.setTelefonoEmergencia((String) updates.get("telefonoEmergencia"));
            }

            Usuario usuarioGuardado = usuarioService.registrarUsuario(usuario);
            return ResponseEntity.ok(usuarioGuardado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Usuario credenciales) {
        Usuario usuario = usuarioService.autenticarUsuario(
                credenciales.getEmail(),
                credenciales.getPassword()
        );

        if (usuario != null) {
            // Creamos la respuesta que queremos enviar al Frontend
            Map<String, Object> response = new HashMap<>();
            response.put("idUsuario", usuario.getIdUsuario());
            response.put("nombre", usuario.getNombre());
            response.put("email", usuario.getEmail());

            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(401).body("Credenciales incorrectas");
        }
    }

}
