package com.vortexBird.moviePlatform.persistence.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Table(name = "Invoices",
        uniqueConstraints = @UniqueConstraint(columnNames = {"date", "id_user_payment_method"})
)
@Data
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idInvoice;

    @Column(nullable = false)
    private Timestamp date;

    @Column(nullable = false)
    private Integer numberOfTickets;

    @Column(nullable = false)
    private Double price;

    @ManyToOne
    @JoinColumn(name = "id_user_payment_method", referencedColumnName = "idUserPaymentMethod")
    private UserPaymentMethod userPaymentMethod;
}
