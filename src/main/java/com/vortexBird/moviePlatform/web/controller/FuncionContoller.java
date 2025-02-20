package com.vortexBird.moviePlatform.web.controller;


import com.vortexBird.moviePlatform.domain.Funcion;
import com.vortexBird.moviePlatform.domain.Tiquete;
import com.vortexBird.moviePlatform.domain.dto.FuncionYTiquetesDTO;
import com.vortexBird.moviePlatform.domain.service.FuncionService;
import com.vortexBird.moviePlatform.domain.service.TiqueteService;
import com.vortexBird.moviePlatform.web.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/public/funcion")
public class FuncionContoller {
    @Autowired
    private FuncionService funcionService;

    @Autowired
    private TiqueteService tiqueteService;

    @GetMapping("/all/{justEnabled}")
    public ResponseEntity<List<Funcion>> getAll(@PathVariable("justEnabled") boolean justEnabled) {
        boolean filter = !SecurityUtils.hasRole("ADMIN") || justEnabled;//DEPENDE DEL USER
        return new ResponseEntity<>(funcionService.obtenerTodas(filter), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Funcion> getFunction( @PathVariable("id") int funcionId) {
        return funcionService.obtenerfuncion(funcionId)
                .map(funcion -> new ResponseEntity<>(funcion, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/movie/{movieId}")
    public ResponseEntity<List<Funcion>> getFunctionByMovie(@PathVariable("movieId") long movieId, @RequestParam boolean justEnabled) {
        boolean filter = !SecurityUtils.hasRole("ADMIN") || justEnabled;//DEPENDE DEL USER
        return funcionService.obtenerPorPelicula(movieId, filter)
                .map(funciones -> new ResponseEntity<>(funciones, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/room/{roomId}")
    public ResponseEntity<List<Funcion>> getFunctionByRoom(@PathVariable("roomId") int roomId, @RequestParam boolean justEnabled) {
        boolean filter = !SecurityUtils.hasRole("ADMIN") || justEnabled;//DEPENDE DEL USER
        return funcionService.obtenerPorSalaCine(roomId, filter)
                .map(funciones -> new ResponseEntity<>(funciones, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @PatchMapping("/enable/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity enable(@PathVariable("id") int funcionId) {
        if(funcionService.habilitar(funcionId)){
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/disable/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity disable(@PathVariable("id") int funcionId) {
        if(funcionService.inhabilitar(funcionId)){
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/save")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<FuncionYTiquetesDTO> save(@RequestBody Funcion funcion) {
        Funcion savedFuncion = funcionService.save(funcion);
        List<Tiquete> tiquetes = tiqueteService.createTicketsFromFunction(savedFuncion.getFuncionId());
        FuncionYTiquetesDTO funcionYTiquetesDTO = new FuncionYTiquetesDTO();
        funcionYTiquetesDTO.setFuncion(funcion);
        funcionYTiquetesDTO.setTiquetes(tiquetes);
        return new ResponseEntity<>(funcionYTiquetesDTO, HttpStatus.CREATED);
    }

    @PostMapping("/saveAll")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<FuncionYTiquetesDTO>> saveAll(@RequestBody List<Funcion> funciones) {
        Funcion savedFuncion;
        List<Tiquete> tiquetes;
        FuncionYTiquetesDTO funcionYTiquetesDTO;
        List<FuncionYTiquetesDTO> funcionesYTiquetes = new ArrayList<>();
        for(Funcion funcion : funciones){
            savedFuncion = funcionService.save(funcion);
            tiquetes = tiqueteService.createTicketsFromFunction(savedFuncion.getFuncionId());
            funcionYTiquetesDTO = new FuncionYTiquetesDTO();
            funcionYTiquetesDTO.setFuncion(funcion);
            funcionYTiquetesDTO.setTiquetes(tiquetes);
            funcionesYTiquetes.add(funcionYTiquetesDTO);
        }
        return new ResponseEntity<>(funcionesYTiquetes, HttpStatus.CREATED);
    }

    @PutMapping(value = "/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity update(@RequestBody Funcion funcion){
        if(funcionService.update(funcion)){
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_MODIFIED);
        }
    }


    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity delete(@PathVariable("id") int funcionId) {
        if (funcionService.delete(funcionId)) {
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

}
