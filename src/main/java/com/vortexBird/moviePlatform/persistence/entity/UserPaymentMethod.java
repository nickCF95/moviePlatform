package com.vortexBird.moviePlatform.persistence.entity;



import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "User_Payments")
@Data
public class UserPaymentMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUserPaymentMethod;

    @Column(nullable = false, unique = true)
    private String cardNumber;

    @Column(nullable = false)
    private String expirationDate;

    @Column(nullable = false)
    private String cvc;

    @ManyToOne
    @JoinColumn(name = "id_user", referencedColumnName = "nickname")
    private User user;

    @Column(nullable = false, columnDefinition = "boolean default true")
    private Boolean enable;

}
