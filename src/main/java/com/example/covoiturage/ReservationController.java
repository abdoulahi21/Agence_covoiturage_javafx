package com.example.covoiturage;

import com.example.covoiturage.model.Reservation;
import com.example.covoiturage.model.Trajet;
import com.example.covoiturage.model.Utilisateur;
import com.example.covoiturage.repository.ReservationRepository;
import com.example.covoiturage.repository.TrajetRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

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
    private TableColumn<?, ?> colDestination;

    @FXML
    private TableColumn<?, ?> colHeure;

    @FXML
    private TableColumn<?, ?> colJour;

    @FXML
    private TableColumn<?, ?> colPassager;

    @FXML
    private TableColumn<?, ?> colTarif;

    @FXML
    private TableColumn<?, ?> colnbPlaces;
    @FXML
    private TableView<Reservation> tableReservation;
    @FXML
    private Tab encours;

    @FXML
    private Tab passee;

    @FXML
    private Tab termine;
    @FXML
    void btnReservez(ActionEvent event) {
        Utilisateur loggedInUser = UserSession.getInstance().getLoggedInUser();
        String placeReserve = champPlaceReserve.getText();
        Trajet route = combo.getValue();
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        ReservationRepository reservationRepository = new ReservationRepository();
        try {
            Reservation reservation = new Reservation();
            reservation.setPlacesReservees(Integer.parseInt(placeReserve));
            reservation.setTrajet(route);
            reservation.setPassager(loggedInUser);
            reservation.setEtat("en cours");
            reservationRepository.addReservation(reservation);
        }
        catch (Exception e) {
            e.printStackTrace();
        }finally {
            entityManager.close();
        }
        affiche();
        btnClear(event);
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
            colDepart.setCellValueFactory(new PropertyValueFactory<>("trajet.depart"));
            colDestination.setCellValueFactory(new PropertyValueFactory<>("trajet.destination"));
            colHeure.setCellValueFactory(new PropertyValueFactory<>("trajet.heureDepart"));
            colJour.setCellValueFactory(new PropertyValueFactory<>("trajet.dateHeureDepart"));
            colPassager.setCellValueFactory(new PropertyValueFactory<>("passager"));
            colTarif.setCellValueFactory(new PropertyValueFactory<>("trajet.tarif"));
            colnbPlaces.setCellValueFactory(new PropertyValueFactory<>("placesReservees"));
            tableReservation.setItems(res);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        affiche();
        TrajetRepository trajetRepository = new TrajetRepository();
        List<Trajet> trajets = trajetRepository.getAllTrajet();
        ObservableList<Trajet> res = FXCollections.observableArrayList(trajets);
        combo.setItems(res);

    }
}
