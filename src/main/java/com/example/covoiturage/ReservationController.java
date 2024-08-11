package com.example.covoiturage;

import com.example.covoiturage.model.Reservation;
import com.example.covoiturage.model.Trajet;
import com.example.covoiturage.model.Utilisateur;
import com.example.covoiturage.repository.ReservationRepository;
import com.example.covoiturage.repository.TrajetRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.hibernate.boot.registry.selector.spi.StrategyCreator;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ReservationController implements Initializable {
    @FXML
    private TextField champPlaceReserve;

    @FXML
    private ComboBox<Trajet> combo;
    @FXML
    private TableColumn<?, ?> colDepart;
    @FXML
    private TableColumn<?, ?> colDepart1;
    @FXML
    private TableColumn<?, ?> colDepart11;

    @FXML
    private TableColumn<?, ?> colDestination;
    @FXML
    private TableColumn<?, ?> colDestination1;
    @FXML
    private TableColumn<?, ?> colDestination11;
    @FXML
    private TableColumn<?, ?> colHeure;
    @FXML
    private TableColumn<?, ?> colHeure1;
    @FXML
    private TableColumn<?, ?> colHeure11;
    @FXML
    private TableColumn<?, ?> colJour;
    @FXML
    private TableColumn<?, ?> colEtat;
    @FXML
    private TableColumn<?, ?> colPassager;

    @FXML
    private TableColumn<?, ?> colTarif;

    @FXML
    private TableColumn<?, ?> colnbPlaces;
    @FXML
    private TableColumn<?, ?> colJour1;
    @FXML
    private TableColumn<?, ?> colEtat1;
    @FXML
    private TableColumn<?, ?> colPassager1;

    @FXML
    private TableColumn<?, ?> colTarif1;

    @FXML
    private TableColumn<?, ?> colnbPlaces1;
    @FXML
    private TableColumn<?, ?> colJour11;
    @FXML
    private TableColumn<?, ?> colEtat11;
    @FXML
    private TableColumn<?, ?> colPassager11;

    @FXML
    private TableColumn<?, ?> colTarif11;

    @FXML
    private TableColumn<?, ?> colnbPlaces11;
    @FXML
    private TableView<Reservation> tableReservation;

    @FXML
    private TableView<Reservation> tableReservationEncours;
    @FXML
    private TableView<Reservation> tableReservationTerminé;

    @FXML
    private Tab encours;

    @FXML
    private Tab passee;
    private int total;
    @FXML
    private Tab termine;
    @FXML
    void btnReservez(ActionEvent event) {
        Utilisateur loggedInUser = UserSession.getInstance().getLoggedInUser();
        String placeReserve = champPlaceReserve.getText();
        Trajet route = combo.getValue(); // Récupère le trajet sélectionné

        if (route == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Erreur de sélection");
            alert.setContentText("Veuillez sélectionner un trajet.");
            alert.showAndWait();
            return;
        }

        int placesDisponibles = route.getPlacesDisponibles();

        if (placeReserve == null || placeReserve.isEmpty() || !placeReserve.matches("\\d+")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Erreur de réservation");
            alert.setContentText("Veuillez entrer un nombre valide de places à réserver.");
            alert.showAndWait();
            return;
        }

        int placesReservees = Integer.parseInt(placeReserve);

        if (placesReservees > placesDisponibles) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Erreur de réservation");
            alert.setContentText("Le nombre de places réservé est supérieur au nombre de places disponibles.");
            alert.showAndWait();
            return;
        }

        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        ReservationRepository reservationRepository = new ReservationRepository();

        try {
            entityManager.getTransaction().begin();
            Reservation reservation = new Reservation();
            reservation.setPlacesReservees(placesReservees);
            reservation.setTotal(placesReservees * route.getTarif());
            reservation.setTrajet(route);
            reservation.setPassager(loggedInUser);
            reservation.setEtat("en cours");
            reservationRepository.addReservation(reservation);
            // Met à jour le nombre de places disponibles dans le trajet
            route.setPlacesDisponibles(placesDisponibles - placesReservees);
            entityManager.merge(route);
            entityManager.getTransaction().commit();

           // Exécuter l'envoi d'email dans un thread séparé
            String recipient = reservation.getPassager().getEmail(); // Assurez-vous que l'email de l'utilisateur est disponible
            String subject = "Confirmation de réservation";
            String content = "Bonjour " + reservation.getPassager().getPrenom() + " " + reservation.getPassager().getNom() + ",\n\n" +
                    "Votre réservation a été confirmée. Voici les détails de votre réservation:\n" +
                    "Date: " + reservation.getTrajetDateDepart().toString() + "\n" +
                    "Trajet: " + reservation.getTrajet().toString() + "\n" +
                    "Nombre de places réservés: " + reservation.getPlacesReservees() + "\n" +
                    "Tarif total: " + reservation.getTotal() + " FCFA\n\n" +
                    "Merci d'utiliser notre service de covoiturage.";
            EmailSender.sendEmail(recipient, subject, content);
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            entityManager.close();
        }

        affiche();
        btnClear(event);
        // Envoyer un email après avoir enregistré la réservation
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Réservation");
        alert.setHeaderText("Réservation effectuée");
        alert.setContentText("Votre réservation a été enregistrée avec succès.");
        alert.showAndWait();

    }

    @FXML
    void btnClear(ActionEvent event) {
        champPlaceReserve.clear();
        combo.setValue(null);
    }
    public void affiche(){
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        ReservationRepository reservationRepository = new ReservationRepository();
        try {
            List<Reservation> reservations = reservationRepository.getAllReservation();
            ObservableList<Reservation> res = FXCollections.observableArrayList(reservations);
            colDepart.setCellValueFactory(new PropertyValueFactory<>("trajetDepart"));
            colDestination.setCellValueFactory(new PropertyValueFactory<>("trajetDestination"));
            colHeure.setCellValueFactory(new PropertyValueFactory<>("trajetHeure"));
            colJour.setCellValueFactory(new PropertyValueFactory<>("trajetDateDepart"));
            colPassager.setCellValueFactory(new PropertyValueFactory<>("passager"));
            colnbPlaces.setCellValueFactory(new PropertyValueFactory<>("placesReservees"));
            colTarif.setCellValueFactory(new PropertyValueFactory<>("total"));
            colEtat.setCellValueFactory(new PropertyValueFactory<>("etat"));
            tableReservation.setItems(res);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
    }

    @FXML
    void btnchangeEtat(ActionEvent event) {
        Utilisateur loggedInUser = UserSession.getInstance().getLoggedInUser();
        if(loggedInUser.getRole().equals("conducteur") || loggedInUser.getRole().equals("admin")){
            Reservation reservation = tableReservation.getSelectionModel().getSelectedItem();
            EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            ReservationRepository reservationRepository = new ReservationRepository();
            try {
                entityManager.getTransaction().begin();
                reservation.setEtat("terminé");
                reservationRepository.updateReservation(reservation);
                entityManager.getTransaction().commit();
            } catch (Exception e) {
                if (entityManager.getTransaction().isActive()) {
                    entityManager.getTransaction().rollback();
                }
                e.printStackTrace();
            } finally {
                entityManager.close();
            }
            affiche();
            afficheEncours();
            afficheTerminé();
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Erreur de changement d'état");
            alert.setContentText("Vous n'avez pas les droits nécessaires pour changer l'état de la réservation.");
            alert.showAndWait();
        }

    }
    public void afficheEncours(){
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        ReservationRepository reservationRepository = new ReservationRepository();
        try {
            List<Reservation> reservations = reservationRepository.getAllReservationStarted();
            ObservableList<Reservation> encours = FXCollections.observableArrayList(reservations);
            colDepart1.setCellValueFactory(new PropertyValueFactory<>("trajetDepart"));
            colDestination1.setCellValueFactory(new PropertyValueFactory<>("trajetDestination"));
            colHeure1.setCellValueFactory(new PropertyValueFactory<>("trajetHeure"));
            colJour1.setCellValueFactory(new PropertyValueFactory<>("trajetDateDepart"));
            colPassager1.setCellValueFactory(new PropertyValueFactory<>("passager"));
            colnbPlaces1.setCellValueFactory(new PropertyValueFactory<>("placesReservees"));
            colTarif1.setCellValueFactory(new PropertyValueFactory<>("trajetTarif"));
            colEtat1.setCellValueFactory(new PropertyValueFactory<>("etat"));
            tableReservationEncours.setItems(encours);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
    }
    public void afficheTerminé(){
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        ReservationRepository reservationRepository = new ReservationRepository();
        try {
            List<Reservation> reservations = reservationRepository.getAllReservationFinished();
            ObservableList<Reservation> terminé = FXCollections.observableArrayList(reservations);
            colDepart11.setCellValueFactory(new PropertyValueFactory<>("trajetDepart"));
            colDestination11.setCellValueFactory(new PropertyValueFactory<>("trajetDestination"));
            colHeure11.setCellValueFactory(new PropertyValueFactory<>("trajetHeure"));
            colJour11.setCellValueFactory(new PropertyValueFactory<>("trajetDateDepart"));
            colPassager11.setCellValueFactory(new PropertyValueFactory<>("passager"));
            colnbPlaces11.setCellValueFactory(new PropertyValueFactory<>("placesReservees"));
            colTarif11.setCellValueFactory(new PropertyValueFactory<>("trajetTarif"));
            colEtat11.setCellValueFactory(new PropertyValueFactory<>("etat"));
            tableReservationTerminé.setItems(terminé);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        affiche();
        afficheEncours();
        afficheTerminé();
        TrajetRepository trajetRepository = new TrajetRepository();
        List<Trajet> trajets = trajetRepository.getAllTrajet();
        ObservableList<Trajet> res = FXCollections.observableArrayList(trajets);
        combo.setItems(res);

    }
}
