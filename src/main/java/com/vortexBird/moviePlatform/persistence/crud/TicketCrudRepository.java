package com.vortexBird.moviePlatform.persistence.crud;

import com.vortexBird.moviePlatform.persistence.entity.Movie;
import com.vortexBird.moviePlatform.persistence.entity.Ticket;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import jakarta.persistence.QueryHint;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TicketCrudRepository extends CrudRepository<Ticket,Long>, JpaSpecificationExecutor<Ticket> {
    @Query(value = "SELECT t.* FROM TICKETS t " +
            "INNER JOIN SEATS s ON t.id_seat = s.id_seat " +
            "WHERE t.id_function = :idFunction " +
            "AND s.code_seat IN :ticketCodes FOR UPDATE",
            nativeQuery = true)
    @QueryHints(value = {@QueryHint(name = "javax.persistence.lock.timeout", value = "3000")})
    Optional<List<Ticket>> findByTicketsCodeAndFunctionForUpdate(@Param("ticketCodes") List<String> ticketCodes,
                                                                 @Param("idFunction") long idFunction);
    Optional<List<Ticket>> findBySeat_idSeat(int idSeat);
    Optional<List<Ticket>> findByFunction_idFunction(long idFunction);
    Optional<List<Ticket>> findByAvailable (boolean available);
    Optional<List<Ticket>> findByAvailableTrueAndFunction_idFunction(long idFunction);
}
