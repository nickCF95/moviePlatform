package com.vortexBird.moviePlatform.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Invoice_Details",
        uniqueConstraints = @UniqueConstraint(columnNames = {"id_ticket", "id_invoice"})
)
@Data
public class InvoiceDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idInvoiceDetail;

    @ManyToOne
    @JoinColumn(name = "id_ticket", referencedColumnName = "idTicket")
    private Ticket ticket;

    @ManyToOne
    @JoinColumn(name = "id_invoice", referencedColumnName = "idInvoice")
    private Invoice invoice;
}
