package com.example.covoiturage;

import com.example.covoiturage.model.Trajet;
import com.example.covoiturage.model.Utilisateur;
import com.example.covoiturage.model.Vehicule;
import com.example.covoiturage.repository.TrajetRepository;
import com.example.covoiturage.repository.VehiculeRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class TrajetController implements Initializable{
    @FXML
    private DatePicker Date;
    @FXML
    private TextField champHeure;
    @FXML
    private TextField champDepart;
    @FXML
    private TextField champTarif;
    @FXML
    private TextField champDestibation;
    @FXML
    private TableColumn<?, ?> colHeuredepart;
    @FXML
    private TextField champNombreplacedispo;
    @FXML
    private TableView<Trajet> tableTrajet;
    @FXML
    private TableColumn<?, ?> colConducteur;
    @FXML
    private TableColumn<?, ?> colDestination;
    @FXML
    private TableColumn<?, ?> colDate;
    @FXML
    private TextField champcherche;
    @FXML
    private TableColumn<?, ?> colNombrePlacesDispo;

    @FXML
    private TableColumn<?, ?> colTrajet;
    @FXML
    private TableColumn<?, ?> colTarif;
    @FXML
    void btnAdd(ActionEvent event) {
        Utilisateur loggedInUser = UserSession.getInstance().getLoggedInUser();
        String date = Date.getValue().toString();
        String heure = champHeure.getText();
        String depart = champDepart.getText();
        String destination = champDestibation.getText();
        String nombrePlaceDispo = champNombreplacedispo.getText();
        String tarif=champTarif.getText();
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        TrajetRepository trajetRepository = new TrajetRepository();
        try {
            Trajet trajet = new Trajet();
            trajet.setDateHeureDepart(LocalDate.parse(date));
            trajet.setDepart(depart);
            trajet.setDestination(destination);
            trajet.setPlacesDisponibles(Integer.parseInt(nombrePlaceDispo));
            trajet.setHeureDepart(heure);
            trajet.setTarif(Integer.parseInt(tarif));
            trajet.setConducteur(loggedInUser);
            trajetRepository.addTrajet(trajet);
            afficherTrajet();
            btnClear(event);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            entityManager.close();
        }
    }
    @FXML
    void charge(MouseEvent event) {
           if(event.getClickCount() == 2){
            Trajet trajet = tableTrajet.getSelectionModel().getSelectedItem();
            Date.setValue(trajet.getDateHeureDepart());
            champHeure.setText(trajet.getHeureDepart());
            champDepart.setText(trajet.getDepart());
            champDestibation.setText(trajet.getDestination());
            champNombreplacedispo.setText(String.valueOf(trajet.getPlacesDisponibles()));
            champTarif.setText(String.valueOf(trajet.getTarif()));
        };
    }
    public void afficherTrajet() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        TrajetRepository trajetRepository = new TrajetRepository();
        try {
            // Implémentez la logique d'affichage
                List<Trajet> trajets = trajetRepository.getAllTrajet();
                ObservableList<Trajet> trajetObservableList = FXCollections.observableArrayList(trajets);
                colConducteur.setCellValueFactory(new PropertyValueFactory<>("conducteur"));
                colDate.setCellValueFactory(new PropertyValueFactory<>("dateHeureDepart"));
                colHeuredepart.setCellValueFactory(new PropertyValueFactory<>("heureDepart"));
                colNombrePlacesDispo.setCellValueFactory(new PropertyValueFactory<>("placesDisponibles"));
                colTrajet.setCellValueFactory(new PropertyValueFactory<>("depart"));
                colDestination.setCellValueFactory(new PropertyValueFactory<>("destination"));
                colTarif.setCellValueFactory(new PropertyValueFactory<>("tarif"));
                tableTrajet.setItems(trajetObservableList);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
    }
    @FXML
    void btnClear(ActionEvent event) {
        champDepart.clear();
        champDestibation.clear();
        champHeure.clear();
        champNombreplacedispo.clear();
        Date.getEditor().clear();
        champTarif.clear();
    }

    @FXML
    void btnDelete(ActionEvent event) {
        Long id = tableTrajet.getSelectionModel().getSelectedItem().getId();
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        TrajetRepository trajetRepository = new TrajetRepository();
        try {
            trajetRepository.deleteTrajet(id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        afficherTrajet();
    }

    @FXML
    void btnUpdate(ActionEvent event) {
        Long id = tableTrajet.getSelectionModel().getSelectedItem().getId();
        String depart = champDepart.getText();
        String destination = champDestibation.getText();
        String heure = champHeure.getText();
        String tarif=champTarif.getText();
        int nombrePlacedispo = Integer.parseInt(champNombreplacedispo.getText());
        String date = Date.getValue().toString();
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        TrajetRepository trajetRepository = new TrajetRepository();
        try {
            Trajet trajet = new Trajet();
            trajet.setId(id);
            trajet.setDepart(depart);
            trajet.setDestination(destination);
            trajet.setPlacesDisponibles(nombrePlacedispo);
            trajet.setHeureDepart(heure);
            trajet.setDateHeureDepart(LocalDate.parse(date));
            trajet.setTarif(Integer.parseInt(tarif));
            trajetRepository.updateTrajet(trajet);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        btnClear(event);
        afficherTrajet();
    }
    @FXML
    void onsearch(KeyEvent event) {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        TrajetRepository trajetRepository = new TrajetRepository();
        try{
            List<Trajet> list = trajetRepository.searchVehicule(champcherche.getText());
            ObservableList<Trajet> trajetObservableList = FXCollections.observableArrayList(list);
            colConducteur.setCellValueFactory(new PropertyValueFactory<>("conducteur"));
            colDate.setCellValueFactory(new PropertyValueFactory<>("dateHeureDepart"));
            colHeuredepart.setCellValueFactory(new PropertyValueFactory<>("heureDepart"));
            colNombrePlacesDispo.setCellValueFactory(new PropertyValueFactory<>("placesDisponibles"));
            colTrajet.setCellValueFactory(new PropertyValueFactory<>("depart"));
            colDestination.setCellValueFactory(new PropertyValueFactory<>("destination"));
            colTarif.setCellValueFactory(new PropertyValueFactory<>("tarif"));
            tableTrajet.setItems(trajetObservableList);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            entityManager.close();
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        afficherTrajet();
    }
}
