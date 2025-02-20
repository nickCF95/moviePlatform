package com.vortexBird.moviePlatform.persistence.crud;

import com.vortexBird.moviePlatform.persistence.entity.CinemaRoom;
import com.vortexBird.moviePlatform.persistence.entity.Invoice;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface InvoiceCrudRepository extends CrudRepository<Invoice, Long> {
    Optional<List<Invoice>> findByUserPaymentMethod_idUserPaymentMethod(long idUserPaymentMethod);
    Optional<List<Invoice>> findByDate(Timestamp date);
    Optional<List<Invoice>> findByDateBefore(Timestamp date);
    Optional<List<Invoice>> findByDateAfter(Timestamp date);
    Optional<List<Invoice>> findByDateBetween(Timestamp dateI, Timestamp dateF);
    Optional<List<Invoice>> findByNumberOfTickets(int numberOfTickets);
    Optional<List<Invoice>> findByNumberOfTicketsLessThanEqual(int numberOfTickets);
    Optional<List<Invoice>> findByNumberOfTicketsGreaterThanEqual(int numberOfTickets);
    @Query(value = "SELECT * FROM INVOICES WHERE NUMBER_OF_TICKETS >= :noTicketsI AND NUMBER_OF_TICKETS <= :noTicketsF", nativeQuery = true)
    Optional<List<Invoice>> findByNumberOfTicketsRange(@Param("noTicketsI") int numberOfTicketsI,@Param("noTicketsF")  int numberOfTicketsF);
}
