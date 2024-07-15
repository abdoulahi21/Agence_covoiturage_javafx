package com.example.covoiturage;

import com.example.covoiturage.model.Utilisateur;
import com.example.covoiturage.model.Vehicule;
import com.example.covoiturage.repository.VehiculeRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class VehiculeController implements Initializable {
    @FXML
    private TextField champMarque;

    @FXML
    private TextField champcherche;
    @FXML
    private TextField champMatricule;

    @FXML
    private TextField champModel;

    @FXML
    private TextField champNombreplace;
    @FXML
    private TableView<Vehicule> tableVehicule;
    @FXML
    private TableColumn<?, ?> colConducteur;
    @FXML
    private TableColumn<?, ?> colMarque;

    @FXML
    private TableColumn<?, ?> colMatricule;

    @FXML
    private TableColumn<?, ?> colModel;

    @FXML
    private TableColumn<?, ?> colNombrePlaces;

    @FXML
    private TableColumn<?, ?> colid;
    @FXML
    void btnAdd(ActionEvent event) {
        Utilisateur loggedInUser = UserSession.getInstance().getLoggedInUser();
        String marque = champMarque.getText();
        String model = champModel.getText();
        String matricule = champMatricule.getText();
        String nombrePlace = champNombreplace.getText();
        //String conducteur = champConducteur.getText();
        // Utilisez les informations ici, y compris l'utilisateur connecté si nécessaire
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        // Implémentez la logique d'ajout
        VehiculeRepository vehiculeRepository = new VehiculeRepository();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            Vehicule vehicule = new Vehicule();
            vehicule.setMarque(marque);
            vehicule.setModele(model);
            vehicule.setImmatriculation(matricule);
            vehicule.setNombrePlaces(Integer.parseInt(nombrePlace));
            vehicule.setConducteur(loggedInUser);
            vehiculeRepository.addVehicule(vehicule);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        btnClear(event);
        afficherVehicule();
    }

    @FXML
    void charge(MouseEvent event) {
        // Implémentez la logique de chargement
        if (event.getClickCount() == 2) {
            Vehicule vehicule = tableVehicule.getSelectionModel().getSelectedItem();
            champMarque.setText(vehicule.getMarque());
            champModel.setText(vehicule.getModele());
            champMatricule.setText(vehicule.getImmatriculation());
            champNombreplace.setText(String.valueOf(vehicule.getNombrePlaces()));
        }
    }
    @FXML
    void btnClear(ActionEvent event) {
        champMarque.clear();
        champModel.clear();
        champMatricule.clear();
        champNombreplace.clear();
    }

    @FXML
    void btnDelete(ActionEvent event) {
        // Implémentez la logique de suppression
        Long id = tableVehicule.getSelectionModel().getSelectedItem().getId();
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        VehiculeRepository vehiculeRepository = new VehiculeRepository();
        try {
            vehiculeRepository.deleteVehicule(id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        afficherVehicule();
    }

    @FXML
    void btnUpdate(ActionEvent event) {
        // Implémentez la logique de mise à jour
        Long id = tableVehicule.getSelectionModel().getSelectedItem().getId();
        String marque = champMarque.getText();
        String model = champModel.getText();
        String matricule = champMatricule.getText();
        String nombrePlace = champNombreplace.getText();
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        VehiculeRepository vehiculeRepository = new VehiculeRepository();
        try {
            Vehicule vehicule = new Vehicule();
            vehicule.setId(id);
            vehicule.setMarque(marque);
            vehicule.setModele(model);
            vehicule.setImmatriculation(matricule);
            vehicule.setNombrePlaces(Integer.parseInt(nombrePlace));
            vehiculeRepository.updateVehicule(vehicule);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        btnClear(event);
        afficherVehicule();
    }

    @FXML
    void onsearch(KeyEvent event) {
    }
    public void afficherVehicule() {
            EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            VehiculeRepository vehiculeRepository = new VehiculeRepository();
            try {
                List<Vehicule> vehicules = vehiculeRepository.getAllVehicule();
                ObservableList<Vehicule> vehiculeObservableList = FXCollections.observableArrayList(vehicules);
                colMarque.setCellValueFactory(new PropertyValueFactory<>("marque"));
                colModel.setCellValueFactory(new PropertyValueFactory<>("modele"));
                colMatricule.setCellValueFactory(new PropertyValueFactory<>("immatriculation"));
                colNombrePlaces.setCellValueFactory(new PropertyValueFactory<>("nombrePlaces"));
                colConducteur.setCellValueFactory(new PropertyValueFactory<>("conducteur"));
                colid.setCellValueFactory(new PropertyValueFactory<>("id"));
                tableVehicule.setItems(vehiculeObservableList);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                entityManager.close();
            }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        afficherVehicule();
        Utilisateur loggedInUser = UserSession.getInstance().getLoggedInUser();
        if (loggedInUser != null) {
            // Vous pouvez maintenant utiliser les informations de l'utilisateur connecté
            System.out.println("Utilisateur connecté: " + loggedInUser);
        } else {
            // Gestion de l'absence d'utilisateur connecté
            System.out.println("Aucun utilisateur connecté");
        }
    }
}
