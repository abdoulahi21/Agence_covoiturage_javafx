package com.example.covoiturage;

import com.example.covoiturage.model.Utilisateur;
import com.example.covoiturage.repository.TrajetRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.*;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.Month;
import java.util.Map;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    @FXML
    private BarChart<String, Number> barchart;

    @FXML
    private PieChart piechart;
    /*public void chargerBarChart() {
        TrajetRepository trajetRepository = new TrajetRepository();
        Map<Month, Integer> trajetsParMois = trajetRepository.getTrajetsParMois();
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        for (Map.Entry<Month, Integer> entry : trajetsParMois.entrySet()) {
            series.getData().add(new XYChart.Data<>(entry.getKey().toString(), entry.getValue()));
        }
        barchart.getData().add(series);
    }
*/

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //chargerBarChart();
    }

}
