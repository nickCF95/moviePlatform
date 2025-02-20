package com.vortexBird.moviePlatform.domain.service;

import com.vortexBird.moviePlatform.domain.Pelicula;
import com.vortexBird.moviePlatform.domain.repository.PeliculaRepository;
import com.vortexBird.moviePlatform.web.utils.SecurityUtils;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PeliculaService {
    @Autowired
    private PeliculaRepository peliculaRepository;

    @Autowired
    private AWSS3Service awss3Service = new AWSS3ServiceImpl();

    public List<Pelicula> obtenerTodas(boolean justEnabled) {
        List<Pelicula> emptyList = new ArrayList<>();
        return peliculaRepository.obtenerTodas(justEnabled).orElse(emptyList);
    }

    public Optional<Pelicula> obtenerPelicula(long peliculaId) {
        return peliculaRepository.obtenerPelicula(peliculaId);
    }

    public Optional<List<Pelicula>> obtenerPorCategoria(int categoriaId) {
        return peliculaRepository.obtenerPorCategoria(categoriaId);
    }

    public Optional<List<Pelicula>> obtenerPorCualquierCoincidencia(String keyword, boolean justEnabled){
        return peliculaRepository.obtenerPorCualquierCoincidencia(keyword, justEnabled);
    }

    public Pelicula save(Pelicula pelicula, MultipartFile imgFile) {
        String fileName = awss3Service.uploadFile(imgFile);
        pelicula.setNombreArchivoImagen(fileName);
        pelicula.setDisponible(true);
        return peliculaRepository.save(pelicula);
    }

    public Pelicula save(Pelicula pelicula) {
        return peliculaRepository.save(pelicula);
    }

    public List<Pelicula> saveAll(List<Pelicula> peliculas) {
        peliculas.forEach(pelicula -> pelicula.setDisponible(true));
        return peliculaRepository.saveAll(peliculas);
    }


    public boolean update(Pelicula pelicula, MultipartFile imgFile){
        try{
            Pelicula peliculaDB = peliculaRepository.obtenerPelicula(pelicula.getPeliculaId()).orElseThrow(EntityNotFoundException::new);
            String fileImgName = peliculaDB.getNombreArchivoImagen();
            if (fileImgName != null && !fileImgName.isEmpty()){
                awss3Service.deleteFile(fileImgName);
            }
        }catch (Exception e) {
            return false;
        }
        String newFileName = awss3Service.uploadFile(imgFile);
        pelicula.setNombreArchivoImagen(newFileName);
        try {
            peliculaRepository.update(pelicula);
            return true;
        } catch (NoResultException e){
            return false;
        }
    }

    public boolean update(Pelicula pelicula){
        try {
            peliculaRepository.update(pelicula);
            return true;
        } catch (NoResultException e){
            return false;
        }
    }

    public boolean inhabilitar(int peliculaId){
        return obtenerPelicula(peliculaId).map(pelicula -> {
            pelicula.setDisponible(false);
            peliculaRepository.update(pelicula);
            return true;
        }).orElse(false);
    }

    public boolean habilitar(int peliculaId){
        return obtenerPelicula(peliculaId).map(pelicula -> {
            pelicula.setDisponible(true);
            peliculaRepository.update(pelicula);
            return true;
        }).orElse(false);
    }

    public boolean delete(int peliculaId) {
        return obtenerPelicula(peliculaId).map(pelicula -> {
            peliculaRepository.delete(peliculaId);
            return true;
        }).orElse(false);
    }

}
