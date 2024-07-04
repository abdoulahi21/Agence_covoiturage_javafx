package com.example.covoiturage.repository;

import com.example.covoiturage.JpaUtil;
import com.example.covoiturage.model.Utilisateur;
import jakarta.persistence.EntityManagerFactory;


public class UtilisateurRepository {
    public Utilisateur getconn(String login, String password) {
        EntityManagerFactory entityManagerFactory= JpaUtil.getEntityManagerFactory();
        Utilisateur utilisateur = entityManagerFactory.createEntityManager().createQuery("SELECT u FROM Utilisateur u WHERE u.email = :login AND u.motDePasse = :password", Utilisateur.class)
                .setParameter("login", login)
                .setParameter("password", password)
                .getSingleResult();
        return utilisateur;
    }
}
