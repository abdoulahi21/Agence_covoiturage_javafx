package com.example.covoiturage.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Trajet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String depart;
    private String destination;
    private LocalDateTime dateHeureDepart;
    private int placesDisponibles;

    @ManyToOne
    @JoinColumn(name = "conducteur_id")
    private Utilisateur conducteur;

    @OneToMany(mappedBy = "trajet", cascade = CascadeType.ALL)
    private List<Reservation> reservations;

    // Getters and setters
}

