package com.vortexBird.moviePlatform.domain.service;

import com.vortexBird.moviePlatform.domain.*;
import com.vortexBird.moviePlatform.domain.repository.FuncionRepository;
import com.vortexBird.moviePlatform.domain.repository.SillaRepository;
import com.vortexBird.moviePlatform.domain.repository.TiqueteRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TiqueteService {
    @Autowired
    private TiqueteRepository tiqueteRepository;

    @Autowired
    private FuncionRepository funcionRepository;

    @Autowired
    private SillaRepository sillaRepository;

    public Optional<Tiquete> obtenerTiquete(long tiqueteId) {
        return tiqueteRepository.obtenerTiquete(tiqueteId);
    }

    public List<Tiquete> obtenerTodos(){
        List<Tiquete> emptyList = new ArrayList<>();
        return  tiqueteRepository.obtenerTodos().orElse(emptyList);
    }

    public Optional<List<Tiquete>> obtenerPorFuncion(long funcionId){
        return tiqueteRepository.obtenerPorFuncion(funcionId);
    }

    public Optional<List<Tiquete>> obtenerDisponiblesPorFuncion(long funcionId){
        return tiqueteRepository.obtenerDisponiblesPorFuncion(funcionId);
    }

    public List<Tiquete> createTicketsFromFunction(long funcionId){
        Funcion funcion = funcionRepository.obtenerFuncion(funcionId).orElseThrow(EntityNotFoundException::new);
        LocalDateTime localDateTime = funcion.getFecha().toLocalDateTime();
        Integer precioBase = funcion.getSalaCine().getPrecioBase();
        List<Silla> sillasSala = sillaRepository.obtenerPorSalaCine(funcion.getSalaCine().getSalaId()).orElseThrow(EntityNotFoundException::new);
        List<Tiquete> tiquetesAGenerar = new ArrayList<>();
        Tiquete tiquete;
        for(Silla silla : sillasSala){
            tiquete = new Tiquete();
            tiquete.setFuncion(funcion);
            tiquete.setSilla(silla);
            tiquete.setPrecioIndividual(calculateTicketPrice(localDateTime, BigDecimal.valueOf(precioBase)));
            tiquete.setDisponible(true);
            tiquetesAGenerar.add(tiquete);
        }
        return tiqueteRepository.saveAll(tiquetesAGenerar);
    }

    public Tiquete save(Tiquete tiquete){return tiqueteRepository.save(tiquete);}

    public boolean update(Tiquete tiquete){
        try {
            tiqueteRepository.update(tiquete);
            return true;
        } catch (NoResultException e){
            return false;
        }
    }

    public boolean inhabilitar(long tiqueteId){
        return obtenerTiquete(tiqueteId).map(tiquete -> {
            tiquete.setDisponible(false);
            tiqueteRepository.update(tiquete);
            return true;
        }).orElse(false);
    }

    public boolean habilitar(int tiqueteId){
        return obtenerTiquete(tiqueteId).map(tiquete -> {
            tiquete.setDisponible(true);
            tiqueteRepository.update(tiquete);
            return true;
        }).orElse(false);
    }

    public boolean delete(int tiqueteId) {
        return obtenerTiquete(tiqueteId).map(tiquete -> {
            tiqueteRepository.delete(tiqueteId);
            return true;
        }).orElse(false);
    }

    private Double calculateTicketPrice(LocalDateTime functionDateTime, BigDecimal basePrice) {
        BigDecimal multiplier = BigDecimal.ONE;
        BigDecimal multiplier2 = BigDecimal.ONE;
        DayOfWeek day = functionDateTime.getDayOfWeek();
        LocalTime time = functionDateTime.toLocalTime();

        if (day == DayOfWeek.TUESDAY || day == DayOfWeek.WEDNESDAY) {
            multiplier = multiplier.multiply(new BigDecimal("0.5")); // half-price on Tuesday & Wednesday
        } else if (day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY) {
            multiplier = multiplier.multiply(new BigDecimal("1.25")); // 25% increase on weekends
        }

        if (time.isBefore(LocalTime.of(18, 0))) { // before 6 PM
            multiplier2 = multiplier2.multiply(new BigDecimal("0.75")); // 25% discount
        }

        BigDecimal finalPrice = basePrice.multiply(multiplier).multiply(multiplier2);
        return finalPrice.doubleValue();
    }
}
