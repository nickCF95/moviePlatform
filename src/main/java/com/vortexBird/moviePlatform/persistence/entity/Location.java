package com.vortexBird.moviePlatform.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Locations",
        uniqueConstraints = @UniqueConstraint(columnNames = {"name", "id_city"}))
@Data
@NoArgsConstructor
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idLocation;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @ManyToOne
    @JoinColumn(name = "id_city", referencedColumnName = "idCity")
    private City city;

    public Location(String name, String address, City city) {
        this.name = name;
        this.address = address;
        this.city = city;
    }
}
