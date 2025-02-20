package com.vortexBird.moviePlatform.web.controller;

import com.vortexBird.moviePlatform.domain.MetodoPagoUsuario;
import com.vortexBird.moviePlatform.domain.Usuario;
import com.vortexBird.moviePlatform.domain.service.MetodoPagoUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/metodoPagoUsuario")
public class MetodoPagoUsuarioController {

    @Autowired
    private MetodoPagoUsuarioService metodoPagoUsuarioService;

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<MetodoPagoUsuario>> getAll() {
        return new ResponseEntity<>(metodoPagoUsuarioService.obtenerTodo(), HttpStatus.OK);
    }

    @GetMapping("/all/{nickname}")
    public ResponseEntity<List<MetodoPagoUsuario>> getUser(@PathVariable("nickname") String nickname) {
        return metodoPagoUsuarioService.obtenerMetodosPagoPorUsuario(nickname)
                .map(metodosPagoUsuario -> new ResponseEntity<>(metodosPagoUsuario, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/{metodoPagoUsuarioId}")
    public ResponseEntity<MetodoPagoUsuario> getUser(@PathVariable("metodoPagoUsuarioId") int id) {
        return metodoPagoUsuarioService.obtenerMetodoPagoUsuario(id)
                .map(metodoPagoUsuario -> new ResponseEntity<>(metodoPagoUsuario, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/save")
    public ResponseEntity<MetodoPagoUsuario> save(@RequestBody MetodoPagoUsuario metodoPagoUsuario) {
        return new ResponseEntity<>(metodoPagoUsuarioService.save(metodoPagoUsuario), HttpStatus.CREATED);
    }

    @PutMapping(value = "/update")
    public ResponseEntity update(@RequestBody MetodoPagoUsuario metodoPagoUsuario){
        if(metodoPagoUsuarioService.update(metodoPagoUsuario)){
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_MODIFIED);
        }
    }

    @DeleteMapping("/delete/{metodoPagoUsuarioId}")
    public ResponseEntity delete(@PathVariable("metodoPagoUsuarioId") int id) {
        if (metodoPagoUsuarioService.delete(id)) {
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

}
