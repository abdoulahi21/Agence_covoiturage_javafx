package com.example.covoiturage.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "trajet_id")
    private Trajet trajet;

    @ManyToOne
    @JoinColumn(name = "passager_id")
    private Utilisateur passager;

    private int placesReservees;
    private String etat;
    private int total;
    // Getters and setters

    public Utilisateur getPassagerId() {
        return passager;
    }
    public String getTrajetDepart(){
            return trajet.getDepart();
      }
    public String getTrajetDestination(){
        return trajet.getDestination();
    }
    public String getTrajetHeure(){
        return trajet.getHeureDepart();
    }
    public String getTrajetDateDepart(){
        return trajet.getDateHeureDepart().toString();
    }
    public int getTrajetTarif(){
        return trajet.getTarif();
    }
    public int getplacesDisponibles(){
        return trajet.getPlacesDisponibles();
    }
    public Long getTrajetId(){
        return trajet.getId();
    }
}

