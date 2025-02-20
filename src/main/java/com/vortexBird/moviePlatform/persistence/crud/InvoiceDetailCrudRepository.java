package com.vortexBird.moviePlatform.persistence.crud;

import com.vortexBird.moviePlatform.persistence.entity.InvoiceDetail;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface InvoiceDetailCrudRepository extends CrudRepository<InvoiceDetail, Long> {

    Optional<List<InvoiceDetail>> findByTicket_idTicket(Long idTicket);
    Optional<List<InvoiceDetail>> findByInvoice_idInvoice(Long idInvoice);
}
