package com.example.covoiturage;

import com.example.covoiturage.bd.BD;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        BD bd = new BD();
        bd.getConnection();
    }
}