package com.vortexBird.moviePlatform.persistence;

import com.vortexBird.moviePlatform.domain.Tiquete;
import com.vortexBird.moviePlatform.domain.repository.TiqueteRepository;
import com.vortexBird.moviePlatform.persistence.crud.TicketCrudRepository;
import com.vortexBird.moviePlatform.persistence.entity.Ticket;
import com.vortexBird.moviePlatform.persistence.entity.User;
import com.vortexBird.moviePlatform.persistence.mapper.TiqueteMapper;
import jakarta.persistence.NoResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TicketRepository implements TiqueteRepository {
    @Autowired
    private TicketCrudRepository ticketCrudRepository;

    @Autowired
    private TiqueteMapper tiqueteMapper;

    @Override
    public Optional<List<Tiquete>> obtenerTodos() {
        return Optional.of(tiqueteMapper.toTiquetes( (List<Ticket>) ticketCrudRepository.findAll()));
    }

    @Override
    public Optional<List<Tiquete>> obtenerPorSilla(int sillaId) {
        return ticketCrudRepository.findBySeat_idSeat(sillaId).map(tickets -> tiqueteMapper.toTiquetes(tickets));
    }

    @Override
    public Optional<List<Tiquete>> obtenerPorFuncion(long funcionId) {
        return ticketCrudRepository.findByFunction_idFunction(funcionId).map(tickets -> tiqueteMapper.toTiquetes(tickets));
    }

    @Override
    public Optional<List<Tiquete>> obtenerPorDisponibilidad(boolean disponible) {
        return ticketCrudRepository.findByAvailable(disponible).map(tickets -> tiqueteMapper.toTiquetes(tickets));
    }

    @Override
    public Optional<List<Tiquete>> obtenerDisponiblesPorFuncion(long funcionId) {
        return ticketCrudRepository.findByAvailableTrueAndFunction_idFunction(funcionId).map(tickets -> tiqueteMapper.toTiquetes(tickets));
    }

    @Override
    public Optional<List<Tiquete>> obtenerPorCodigoSillasYFuncionConBloqueo(List<String> sillas, long funcionId) {
        return ticketCrudRepository.findByTicketsCodeAndFunctionForUpdate(sillas, funcionId).map(tickets -> tiqueteMapper.toTiquetes(tickets));
    }

    @Override
    public Optional<Tiquete> obtenerTiquete(long tiqueteId) {
        return ticketCrudRepository.findById(tiqueteId).map(ticket -> tiqueteMapper.toTiquete(ticket));
    }

    @Override
    public List<Tiquete> saveAll(List<Tiquete> tiquetes) {
        return tiqueteMapper.toTiquetes((List<Ticket>) ticketCrudRepository.saveAll(tiqueteMapper.toTickets(tiquetes)));
    }

    @Override
    public Tiquete save(Tiquete tiquete) {
        return tiqueteMapper.toTiquete(ticketCrudRepository.save(tiqueteMapper.toTicket(tiquete)));
    }

    @Override
    public void update(Tiquete tiquete) {
        Ticket ticket = ticketCrudRepository.findById(tiquete.getTiqueteId()).orElseThrow(NoResultException::new);
        tiqueteMapper.updateTicketFromTiquete(tiquete, ticket);
        ticketCrudRepository.save(ticket);
    }

    @Override
    public void delete(long tiqueteId) {
        ticketCrudRepository.deleteById(tiqueteId);
    }
}
