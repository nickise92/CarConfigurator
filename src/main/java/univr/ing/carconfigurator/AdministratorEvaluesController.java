package univr.ing.carconfigurator;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.AnchorPane;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class AdministratorEvaluesController {
    
    private MainController mainController;
    private ArrayList<String[]> csvData;
    private int index = 0;

    @FXML private AnchorPane rootPane;
    @FXML private AnchorPane contentPane;
    @FXML private Label titleLabel;
    @FXML private Label utente;
    @FXML private Label date;
    @FXML private Label idPrev;
    @FXML private Label auto;
    @FXML private Label colore;
    @FXML private Label motore;
    @FXML private Label interni;
    @FXML private Label sensori;
    @FXML private Label cerchi;
    @FXML private Label sconto;
    @FXML private Label sedeRitiro;
    @FXML private Label prezzo;
    @FXML private ChoiceBox<String> viewChoiceBox = new ChoiceBox<String>();;
    @FXML private Button nextButton;
    @FXML private Button backButton;
    @FXML private Button exitButton;
    
   
    
    public AdministratorEvaluesController() {
    
    }
    
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    public void initialize(){

        viewChoiceBox.getItems().addAll("Utente", "Marca", "Sede Di Ritiro");
        Platform.runLater(this::centerContent);
    }

    private void centerContent() {

        AnchorPane.setLeftAnchor(titleLabel, (rootPane.getWidth() - titleLabel.getWidth()) / 2);
        AnchorPane.setLeftAnchor(contentPane, (rootPane.getWidth() - contentPane.getWidth()) / 2);
        AnchorPane.setTopAnchor(contentPane, (rootPane.getHeight() - contentPane.getHeight()) / 2);

    }
    
    @FXML
    public void OnNextButton(ActionEvent actionEvent) {
        index = (index+1) % csvData.size();
        loadData(index);
    }

    private void loadData(int index) {
        String[] firstRow = csvData.get(index);
        utente.setText("Utente: " + firstRow[1]);
        date.setText("Data: " + firstRow[2]);
        idPrev.setText("ID Preventivo: "+ firstRow[0]);
        auto.setText( "Auto: "+ firstRow[3]+" "+firstRow[4]);
        colore.setText("Colore: "+ firstRow[5]);
        motore.setText("Motore: "+ firstRow[6]);
        cerchi.setText("Cerchi: "+ firstRow[7]);
        sensori.setText("Sensori: " +firstRow[8]);
        interni.setText("Interni: " +firstRow[9]);
        prezzo.setText("Prezzo: " + String.format("%.2f", firstRow[10]) + "€");
        if (Double.parseDouble(firstRow[12]) > 0) {
            sconto.setText("Sconto: " + String.format("%.2f", firstRow[12]) + "€");
        } else {
            sconto.setText("");
        }
        
        sedeRitiro.setText("Sede di consengna: " +firstRow[13]);
    }

    @FXML
    public void OnBackButton(ActionEvent actionEvent) {
        index--;
        if(index <0)
            index = csvData.size() - 1;
        loadData(index);
    }
    
    @FXML
    public void OnChoiceAction(ActionEvent actionEvent) {
        try {
            // Carica il file CSV in un ArrayList
            csvData = loadCsvToArrayList("database/preventivi.csv");

            // Ottieni la scelta dell'utente dalla ChoiceBox
            String choice = viewChoiceBox.getValue();

            // Ordina l'ArrayList in base alla scelta
            if (choice != null) {
                switch (choice) {
                    case "Utente":
                        csvData.sort(Comparator.comparing(row -> row[1]));// Ordina per nome
                        break;
                    case "Sede Di Ritiro":
                        csvData.sort(Comparator.comparing(row -> row[13])); // Ordina per Sede Di Ritiro
                        break;
                    case "Marca":
                        csvData.sort(Comparator.comparing(row -> row[3])); // Ordina per Marca
                        break;
                }
                loadData(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private ArrayList<String[]> loadCsvToArrayList(String fileName) throws IOException {
        ArrayList<String[]> data = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(Paths.get(fileName))) {
            data.addAll(br.lines().map(line -> line.split(",")).collect(Collectors.toList()));
        }
        return data;
    }
    
    @FXML
    protected void onExitButton() {
        Platform.exit();
    }
    
}