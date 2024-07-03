module com.example.covoiturage {
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires jasperreports;

    opens com.example.covoiturage.model;
    opens com.example.covoiturage to javafx.fxml;
    exports com.example.covoiturage;
}