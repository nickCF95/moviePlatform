package com.vortexBird.moviePlatform.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Tickets",
        uniqueConstraints = @UniqueConstraint(columnNames = {"id_function", "id_seat"})
)
@Data
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTicket;

    @ManyToOne
    @JoinColumn(name = "id_function", referencedColumnName = "idFunction")
    private Function function;

    @ManyToOne
    @JoinColumn(name = "id_seat", referencedColumnName = "idSeat")
    private Seat seat;

    @Column(nullable = false, columnDefinition = "boolean default true")
    private Boolean available;

    @Column(nullable = false)
    private Double individualPrice;
}
