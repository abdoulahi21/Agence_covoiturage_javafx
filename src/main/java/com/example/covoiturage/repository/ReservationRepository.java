package com.example.covoiturage.repository;

import com.example.covoiturage.JpaUtil;
import com.example.covoiturage.model.Reservation;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;


import java.util.List;

public class ReservationRepository {
 public List<Reservation> getAllReservation(){
     EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
     EntityManager entityManager = entityManagerFactory.createEntityManager();
     entityManager.getTransaction().begin();
     List<Reservation> reservation = entityManager.createQuery("from Reservation ", Reservation.class).getResultList();
     entityManager.getTransaction().commit();
     entityManager.close();
    return reservation;
    }
    public void addReservation(Reservation reservation){
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(reservation);
        entityManager.getTransaction().commit();
        entityManager.close();
    }
    public void updateReservation(Reservation reservation){
    }
    public void deleteReservation(Long id){
    }
    public void searchReservation(String search){
    }
}
