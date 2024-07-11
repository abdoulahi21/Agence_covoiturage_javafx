package com.example.covoiturage;

import com.example.covoiturage.model.Utilisateur;
import com.example.covoiturage.repository.UtilisateurRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {
    @FXML
    private TextField champEmail;

    @FXML
    private PasswordField champMdp;

    @FXML
    private TextField champNom;

    @FXML
    private TextField champPrenom;

    @FXML
    private TextField champTelephone;

    @FXML
    private ComboBox<?> combo;

    @FXML
    void btnAnnuler(ActionEvent event) throws IOException {
        Parent fxml= FXMLLoader.load(getClass().getResource("login-view.fxml"));
        Scene scene = new Scene(fxml);
        Stage stage=(Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Connexion");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void btnValider(ActionEvent event) {
        String nom = champNom.getText();
        String prenom = champPrenom.getText();
        String email = champEmail.getText();
        String motDePasse = champMdp.getText();
        String role = combo.getValue().toString();
        String telephone = champTelephone.getText();
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        UtilisateurRepository utilisateurRepository = new UtilisateurRepository();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            Utilisateur utilisateur = new Utilisateur();
            utilisateur.setNom(nom);
            utilisateur.setPrenom(prenom);
            utilisateur.setEmail(email);
            utilisateur.setMotDePasse(motDePasse);
            utilisateur.setRole(role);
            utilisateur.setTelephone(telephone);
            utilisateurRepository.addUtilisateur(utilisateur);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Validation");
            alert.setHeaderText(null);
            alert.setContentText("Enreistrement reussi");
            alert.showAndWait();

            Parent fxml= FXMLLoader.load(getClass().getResource("login-view.fxml"));
            Scene scene = new Scene(fxml);
            Stage stage=(Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Connexion");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Enregistrement echoué");
            alert.showAndWait();
        } finally {
            entityManagerFactory.close();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList items = combo.getItems();
        items.add("conducteur");
        items.add("passager");

    }
}
