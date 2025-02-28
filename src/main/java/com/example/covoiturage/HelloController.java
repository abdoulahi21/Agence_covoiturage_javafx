package com.example.covoiturage;

import com.example.covoiturage.model.Utilisateur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    @FXML
    private AnchorPane dynamiquePage;
    @FXML
    private Label user;

    @FXML
    void pageUtilisateur(ActionEvent event) throws Exception {
        Utilisateur loggedInUser = UserSession.getInstance().getLoggedInUser();
        if (loggedInUser.getRole().equals("admin")) {
            Parent fxml = FXMLLoader.load(getClass().getResource("utilisateur-view.fxml"));
            dynamiquePage.getChildren().removeAll();
            dynamiquePage.getChildren().setAll(fxml);
        } else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Accès refusé");
            alert.setHeaderText("Vous n'avez pas les droits nécessaires pour accéder à cette page");
            alert.showAndWait();
        }
    }
    @FXML
    void pageVehicule(ActionEvent event) throws Exception {
        Utilisateur loggedInUser = UserSession.getInstance().getLoggedInUser();
        if(loggedInUser.getRole().equals("conducteur")|| loggedInUser.getRole().equals("admin")) {
            Parent fxml = FXMLLoader.load(getClass().getResource("vehicule-view.fxml"));
            dynamiquePage.getChildren().removeAll();
            dynamiquePage.getChildren().setAll(fxml);
        } else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Accès refusé");
            alert.setHeaderText("Vous n'avez pas les droits nécessaires pour accéder à cette page");
            alert.showAndWait();
        }
    }
    @FXML
    void pageTrajet(ActionEvent event) throws Exception {
        Utilisateur loggedInUser = UserSession.getInstance().getLoggedInUser();
       if(loggedInUser.getRole().equals("conducteur") || loggedInUser.getRole().equals("passager") || loggedInUser.getRole().equals("admin")) {
           Parent fxml = FXMLLoader.load(getClass().getResource("trajet-view.fxml"));
           dynamiquePage.getChildren().removeAll();
           dynamiquePage.getChildren().setAll(fxml);
       } else{
           Alert alert = new Alert(Alert.AlertType.ERROR);
           alert.setTitle("Accès refusé");
           alert.setHeaderText("Vous n'avez pas les droits nécessaires pour accéder à cette page");
           alert.showAndWait();
       }
    }
    @FXML
    void pageDashboard(ActionEvent event) throws Exception {
        Utilisateur loggedInUser = UserSession.getInstance().getLoggedInUser();
        if(loggedInUser.getRole().equals("admin")) {
            Parent fxml = FXMLLoader.load(getClass().getResource("dashboard-view.fxml"));
            dynamiquePage.getChildren().removeAll();
            dynamiquePage.getChildren().setAll(fxml);
        } else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Accès refusé");
            alert.setHeaderText("Vous n'avez pas les droits nécessaires pour accéder à cette page");
            alert.showAndWait();
        }
    }

    @FXML
    void pageReservations(ActionEvent event) {
        try {
            Parent fxml =  FXMLLoader.load(getClass().getResource("reservation-view.fxml"));
            dynamiquePage.getChildren().removeAll();
            dynamiquePage.getChildren().setAll(fxml);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void Logout(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Quitter");
        alert.setHeaderText("Voulez-vous vraiment quitter l'application?");
        alert.showAndWait();
        if(alert.getResult().getText().equals("OK"))
        {
            Parent fxml= FXMLLoader.load(getClass().getResource("login-view.fxml"));
            Scene scene = new Scene(fxml);
            Stage stage=(Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Login");
            stage.setScene(scene);
            stage.show();
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Utilisateur loggedInUser = UserSession.getInstance().getLoggedInUser();
        if (loggedInUser != null) {
            // Vous pouvez maintenant utiliser les informations de l'utilisateur connecté
            System.out.println("Utilisateur connecté: " + loggedInUser);
        } else {
            // Gestion de l'absence d'utilisateur connecté
            System.out.println("Aucun utilisateur connecté");
        }
        //affiche la page vehicule par defaut
        if(loggedInUser.getRole().equals("admin")) {
            try {
                Parent fxml = FXMLLoader.load(getClass().getResource("dashboard-view.fxml"));
                dynamiquePage.getChildren().removeAll();
                dynamiquePage.getChildren().setAll(fxml);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (loggedInUser.getRole().equals("conducteur")) {
            try {
                Parent fxml = FXMLLoader.load(getClass().getResource("vehicule-view.fxml"));
                dynamiquePage.getChildren().removeAll();
                dynamiquePage.getChildren().setAll(fxml);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (loggedInUser.getRole().equals("passager")) {
            try {
                Parent fxml = FXMLLoader.load(getClass().getResource("reservation-view.fxml"));
                dynamiquePage.getChildren().removeAll();
                dynamiquePage.getChildren().setAll(fxml);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //user.setText("Bienvenue "+loggedInUser.toString());
    }
}