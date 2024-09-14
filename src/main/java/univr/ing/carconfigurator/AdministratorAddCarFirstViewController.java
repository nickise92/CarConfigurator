package univr.ing.carconfigurator;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class AdministratorAddCarFirstViewController {

    private MainController mainController;
    public AdministratorAddCarFirstViewController () {}
    public void setMainController(MainController mainController) {this.mainController = mainController; }
    private String AutoCsv;
    @FXML
    private ChoiceBox<String> BrandChoiceBox;
    @FXML
    private ChoiceBox<String> ColorChoiceBox ;
    @FXML
    private ChoiceBox<String> EngineChoiceBox;

    @FXML
    public TextField Name;
    @FXML
    public TextField Prezzo;

    private String nome;
    private String color;
    private String engine;
    private String brand;
    private String prezzo;

    private final String brandpath = "database/brand.csv";
    private final String enginePath = "database/engine.csv";
    private final String carsPath = "database/car.csv";
    private ObservableList<String> brandList = FXCollections.observableArrayList();
    private ObservableList<String> colorsList = FXCollections.observableArrayList("Bianco", "Rosso", "Blu", "Nero", "Grigio");
    private ObservableList<String> engineList = FXCollections.observableArrayList();
    private void updateCarConfig(){

         AutoCsv = SessionManager.getInstance().getCarCsv();
    }
    public void initialize() {
        Platform.runLater(this::updateCarConfig);
        ColorChoiceBox.setItems(colorsList);


        ColorChoiceBox.setValue("Bianco");
        try {
            Scanner sc = new Scanner(new File(brandpath));
            for (String car : sc.nextLine().split(",")) {
                if (!car.equals("")) {
                    brandList.add(car);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Impossibile inizializzare le marche di auto.");
        }
        BrandChoiceBox.setItems(brandList);

    }




    public void onEngineSelection(ActionEvent actionEvent) {
        engine = EngineChoiceBox.getValue();
    }

    public void onBrandSelection(ActionEvent actionEvent) {

        try {
            Scanner sc = new Scanner(new File(enginePath));
            engineList.clear();
            while (sc.hasNextLine()) {
                String[] line = sc.nextLine().split(",");
                if (line[0].equals(BrandChoiceBox.getValue()) && !line[1].equals("")) {
                    engineList.add(line[1]);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        EngineChoiceBox.setItems(engineList);

        brand = BrandChoiceBox.getValue();
    }

    public void onColorSelection(ActionEvent actionEvent) {
         color = ColorChoiceBox.getValue();
    }
    public void OnNextSelection(ActionEvent actionEvent) {
        nome = Name.getText();
        prezzo = Prezzo.getText();
        String info = nome + ","+brand+","+engine+","+color + ", " + prezzo + ",";
        /// DEBUG
        System.out.println(info);
        SessionManager.getInstance().setCarCsv(info);
        mainController.loadAdministratorAddCarSecondView();
    }

    public void OnExitButton(ActionEvent actionEvent) {
        mainController.loadAdministratorview();
    }
}
