package com.example.covoiturage.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Vehicule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String marque;
    private String modele;
    private String immatriculation;
    private int nombrePlaces;
    @ManyToOne
    @JoinColumn(name = "conducteur_id")
    private Utilisateur conducteur;
    // Getters and setters

}
