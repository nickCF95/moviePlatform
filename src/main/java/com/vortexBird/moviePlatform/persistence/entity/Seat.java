package com.vortexBird.moviePlatform.persistence.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Seats",
        uniqueConstraints = @UniqueConstraint(columnNames = {"codeSeat", "id_room"})
)
@Data
@NoArgsConstructor
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idSeat;

    @Column(nullable = false)
    private String codeSeat;

    @ManyToOne
    @JoinColumn(name = "id_room", referencedColumnName = "idRoom" )
    private CinemaRoom cinemaRoom;

    public Seat(String codeSeat, CinemaRoom cinemaRoom) {
        this.codeSeat = codeSeat;
        this.cinemaRoom = cinemaRoom;
    }
}
