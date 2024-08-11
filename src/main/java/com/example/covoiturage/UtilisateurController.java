package com.example.covoiturage;

import com.example.covoiturage.model.Utilisateur;
import com.example.covoiturage.repository.UtilisateurRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

 public class UtilisateurController implements Initializable {
    @FXML
    private TableColumn<?, ?> cemail;

    @FXML
    private TableColumn<?, ?> cid;

    @FXML
    private TableColumn<?, ?> cnom;

    @FXML
    private TableColumn<?, ?> cprenom;

    @FXML
    private TableColumn<?, ?> crole;

    @FXML
    private TableColumn<?, ?> ctel;

    @FXML
    private TableView<Utilisateur> tableUtilisateur;



    public void afficherUtilisateur() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        UtilisateurRepository utilisateurRepository = new UtilisateurRepository();
        try {
            List<Utilisateur> utilisateurs = utilisateurRepository.getAllUtilisateur();
            ObservableList<Utilisateur> res = FXCollections.observableArrayList(utilisateurs);
            cid.setCellValueFactory(new PropertyValueFactory<>("id"));
            cnom.setCellValueFactory(new PropertyValueFactory<>("nom"));
            cprenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
            cemail.setCellValueFactory(new PropertyValueFactory<>("email"));
            crole.setCellValueFactory(new PropertyValueFactory<>("role"));
            ctel.setCellValueFactory(new PropertyValueFactory<>("telephone"));
            tableUtilisateur.setItems(res);
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        afficherUtilisateur();
    }
}
