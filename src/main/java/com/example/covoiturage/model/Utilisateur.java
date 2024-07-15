package com.example.covoiturage.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String motDePasse;
    private String role; // 'conducteur' ou 'passager'
    private String telephone;


    @OneToMany(mappedBy = "conducteur", cascade = CascadeType.ALL)
    private List<Vehicule> vehicules;

    @OneToMany(mappedBy = "conducteur", cascade = CascadeType.ALL)
    private List<Trajet> trajets;

    @OneToMany(mappedBy = "passager", cascade = CascadeType.ALL)
    private List<Reservation> reservations;

    @Override
    public String toString() {
        return  nom +" "+ prenom;
    }
}
