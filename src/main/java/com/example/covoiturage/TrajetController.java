package com.example.covoiturage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;

public class TrajetController {
    @FXML
    private DatePicker Date;

    @FXML
    private TextField champDepart;

    @FXML
    private TextField champDestibation;

    @FXML
    private TextField champNombreplacedispo;

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

    }

    @FXML
    void btnClear(ActionEvent event) {

    }

    @FXML
    void btnDelete(ActionEvent event) {

    }

    @FXML
    void btnUpdate(ActionEvent event) {

    }
}
