package com.example.covoiturage.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Trajet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String depart;
    private String destination;
    private LocalDate dateHeureDepart;
    private int placesDisponibles;
    private String heureDepart;
    private int tarif;
    @ManyToOne
    @JoinColumn(name = "conducteur_id")
    private Utilisateur conducteur;

    @OneToMany(mappedBy = "trajet", cascade = CascadeType.ALL)
    private List<Reservation> reservations;

    @Override
    public String toString() {
        return depart +"-"+ destination + "-"+ dateHeureDepart +"-"+ heureDepart +"-"+ placesDisponibles +"place(s)-"+ tarif+"FCFA"
                ;
    }
    // Getters and setters
}

