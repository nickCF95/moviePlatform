package com.vortexBird.moviePlatform.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;

@Entity
@Table(name = "Users_Details")
@Data
public class UserDetails {

    @Id
    private String nickname;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    User user;

    private String fullName;

    private Date dateOfBirth;

    private String address;

    private String phoneNumber;
}
