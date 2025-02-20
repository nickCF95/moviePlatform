package com.vortexBird.moviePlatform.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Cinema_Rooms",
        uniqueConstraints = @UniqueConstraint(columnNames = {"roomNumber", "id_location"})
)
@Data
@NoArgsConstructor
public class CinemaRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idRoom;

    @Column(nullable = false)
    private Integer capacity;

    @Column(nullable = false)
    private Short roomNumber;

    @ManyToOne
    @JoinColumn(name = "id_location", referencedColumnName = "idLocation")
    private Location location;

    public CinemaRoom(Short roomNumber, Integer capacity, Location location, Integer basePrice) {
        this.roomNumber = roomNumber;
        this.capacity = capacity;
        this.location = location;
        this.basePrice = basePrice;
    }

    @Column(nullable = false)
    private Boolean enable = true;

    @Column(nullable = false)
    private Integer basePrice;
}
