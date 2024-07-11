package com.example.covoiturage;

import com.example.covoiturage.model.Utilisateur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class VehiculeController implements Initializable {
    @FXML
    private TextField champMarque;
    @FXML
    private TextField champConducteur;
    @FXML
    private TextField champMatricule;

    @FXML
    private TextField champModel;

    @FXML
    private TextField champNombreplace;

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
    void btnAdd(ActionEvent event) {
        Utilisateur loggedInUser = UserSession.getInstance().getLoggedInUser();
        String nomcomplet=loggedInUser.getPrenom()+ " "+loggedInUser.getNom();
        String marque = champMarque.getText();
        String model = champModel.getText();
        String matricule = champMatricule.getText();
        String nombrePlace = champNombreplace.getText();

        // Utilisez les informations ici, y compris l'utilisateur connecté si nécessaire
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
    }

    @FXML
    void btnUpdate(ActionEvent event) {
        // Implémentez la logique de mise à jour
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Utilisateur loggedInUser = UserSession.getInstance().getLoggedInUser();
        if (loggedInUser != null) {
            // Vous pouvez maintenant utiliser les informations de l'utilisateur connecté
            System.out.println("Utilisateur connecté: " + loggedInUser.getPrenom());
        } else {
            // Gestion de l'absence d'utilisateur connecté
            System.out.println("Aucun utilisateur connecté");
        }
    }
}
