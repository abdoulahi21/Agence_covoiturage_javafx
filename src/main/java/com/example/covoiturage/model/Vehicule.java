package com.example.covoiturage.model;

import jakarta.persistence.*;

@Entity
public class Vehicule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String marque;
    private String modele;
    private String immatriculation;

    @ManyToOne
    @JoinColumn(name = "conducteur_id")
    private Utilisateur conducteur;

    // Getters and setters
}
