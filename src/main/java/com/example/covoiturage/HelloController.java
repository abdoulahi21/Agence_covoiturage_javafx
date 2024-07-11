package com.example.covoiturage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class HelloController {
    @FXML
    private AnchorPane dynamiquePage;


    @FXML
    void pageVehicule(ActionEvent event) throws Exception {
        Parent fxml =  FXMLLoader.load(getClass().getResource("vehicule-view.fxml"));
        dynamiquePage.getChildren().removeAll();
        dynamiquePage.getChildren().setAll(fxml);
    }
}