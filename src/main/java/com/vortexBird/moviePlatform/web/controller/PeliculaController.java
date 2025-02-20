package com.vortexBird.moviePlatform.web.controller;

import com.vortexBird.moviePlatform.domain.Funcion;
import com.vortexBird.moviePlatform.domain.Pelicula;
import com.vortexBird.moviePlatform.domain.Tiquete;
import com.vortexBird.moviePlatform.domain.dto.FuncionYTiquetesDTO;
import com.vortexBird.moviePlatform.domain.service.PeliculaService;
import com.vortexBird.moviePlatform.web.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/public/pelicula")
public class PeliculaController {
    @Autowired
    private PeliculaService peliculaService;

    @GetMapping("/all/{justEnabled}")
    public ResponseEntity<List<Pelicula>> getAll(@PathVariable("justEnabled") boolean justEnabled) {
        boolean filter = !SecurityUtils.hasRole("ADMIN") || justEnabled;//DEPENDE DEL USER
        return new ResponseEntity<>(peliculaService.obtenerTodas(filter), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pelicula> getMovie( @PathVariable("id") int peliculaId) {
        return peliculaService.obtenerPelicula(peliculaId)
                .map(pelicula -> new ResponseEntity<>(pelicula, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<Pelicula>> getByCategory(@PathVariable("categoryId") int categoryId) {
        return peliculaService.obtenerPorCategoria(categoryId)
                .map(peliculas -> new ResponseEntity<>(peliculas, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/anyMatchField/{keyword}")
    public ResponseEntity<List<Pelicula>> getByAnyMatchField(@PathVariable("keyword") String keyword, @RequestParam boolean justEnabled) {
        boolean filter = !SecurityUtils.hasRole("ADMIN") || justEnabled;//DEPENDE DEL USER
        return peliculaService.obtenerPorCualquierCoincidencia(keyword, filter)
                .map(peliculas -> new ResponseEntity<>(peliculas, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @PatchMapping("/enable/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity enable(@PathVariable("id") int peliculaId) {
        if(peliculaService.habilitar(peliculaId)){
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/disable/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity disable(@PathVariable("id") int peliculaId) {
        if(peliculaService.inhabilitar(peliculaId)){
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/saveAll")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Pelicula>> saveAll(@RequestBody List<Pelicula> peliculas) {
        return new ResponseEntity<>(peliculaService.saveAll(peliculas), HttpStatus.CREATED);
    }

    @PostMapping("/save")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Pelicula> save(@RequestBody Pelicula pelicula) {
        return new ResponseEntity<>(peliculaService.save(pelicula), HttpStatus.CREATED);
    }

    @PostMapping(value = "/saveWithImg", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Pelicula> saveWithImg(@RequestPart(value = "data") Pelicula pelicula, @RequestPart(value="file") MultipartFile file){
        return new ResponseEntity<>(peliculaService.save(pelicula, file), HttpStatus.CREATED);
    }

    @PutMapping(value = "/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity update(@RequestBody Pelicula pelicula){
        if(peliculaService.update(pelicula)){
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_MODIFIED);
        }
    }

    @PutMapping(value = "/updateWithImg", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity updateWithImg(@RequestPart(value = "data") Pelicula pelicula, @RequestPart(value="file") MultipartFile file){
        if(peliculaService.update(pelicula, file)){
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_MODIFIED);
        }
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity delete(@PathVariable("id") int peliculaId) {
        if (peliculaService.delete(peliculaId)) {
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
}
