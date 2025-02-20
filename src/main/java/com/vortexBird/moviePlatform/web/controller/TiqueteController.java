package com.vortexBird.moviePlatform.web.controller;

import com.vortexBird.moviePlatform.domain.Funcion;
import com.vortexBird.moviePlatform.domain.Tiquete;
import com.vortexBird.moviePlatform.domain.service.TiqueteService;
import com.vortexBird.moviePlatform.web.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/tiquete")
public class TiqueteController {

    @Autowired
    private TiqueteService tiqueteService;

    @GetMapping("/all")
    public ResponseEntity<List<Tiquete>> getAll() {
        return new ResponseEntity<>(tiqueteService.obtenerTodos(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tiquete> getTicket( @PathVariable("id") int tiqueteId) {
        return tiqueteService.obtenerTiquete(tiqueteId)
                .map(tiquete -> new ResponseEntity<>(tiquete, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/funcion/{funcionId}")
    public ResponseEntity<List<Tiquete>> getByFunction(@PathVariable("funcionId") long funcionId) {
        return tiqueteService.obtenerPorFuncion(funcionId)
                .map(tiquetes -> new ResponseEntity<>(tiquetes, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/funcion/disponibles/{funcionId}")
    public ResponseEntity<List<Tiquete>> getAvailableByFunction(@PathVariable("funcionId") int funcionId) {
        return tiqueteService.obtenerDisponiblesPorFuncion(funcionId)
                .map(tiquetes -> new ResponseEntity<>(tiquetes, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @PatchMapping("/enable/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity enable(@PathVariable("id") int tiqueteId) {
        if(tiqueteService.habilitar(tiqueteId)){
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/disable/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity disable(@PathVariable("id") int tiqueteId) {
        if(tiqueteService.inhabilitar(tiqueteId)){
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/save")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Tiquete> save(@RequestBody Tiquete tiquete) {
        return new ResponseEntity<>(tiqueteService.save(tiquete), HttpStatus.CREATED);
    }


    @PutMapping(value = "/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity update(@RequestBody Tiquete tiquete){
        if(tiqueteService.update(tiquete)){
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_MODIFIED);
        }
    }


    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity delete(@PathVariable("id") int tiqueteId) {
        if (tiqueteService.delete(tiqueteId)) {
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
}
