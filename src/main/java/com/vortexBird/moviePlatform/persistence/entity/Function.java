package com.vortexBird.moviePlatform.persistence.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Table(name = "Functions",
        uniqueConstraints = @UniqueConstraint(columnNames = {"date", "id_movie", "id_room"})
)
@Data
public class Function {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFunction;

    @Column(nullable = false)
    private Timestamp date;

    @ManyToOne
    @JoinColumn(name = "id_movie", referencedColumnName = "idMovie")
    private Movie movie;

    @ManyToOne
    @JoinColumn(name = "id_room", referencedColumnName = "idRoom")
    private CinemaRoom cinemaRoom;

    @Column(nullable = false)
    private Boolean enable = true;
}
