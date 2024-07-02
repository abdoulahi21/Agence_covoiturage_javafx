module com.example.covoiturage {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.persistence;
    requires jakarta.persistence;


    opens com.example.covoiturage to javafx.fxml;
    exports com.example.covoiturage;
}