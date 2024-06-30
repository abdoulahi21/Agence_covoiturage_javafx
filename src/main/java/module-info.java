module com.example.covoiturage {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.covoiturage to javafx.fxml;
    exports com.example.covoiturage;
}