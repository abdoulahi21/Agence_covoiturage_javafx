module com.example.covoiturage {
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires jasperreports;
    requires static lombok;
    requires java.mail;

    opens com.example.covoiturage.repository;
    opens com.example.covoiturage.model;
    exports com.example.covoiturage;
    opens com.example.covoiturage;

}