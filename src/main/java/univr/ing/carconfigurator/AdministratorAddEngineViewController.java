package univr.ing.carconfigurator;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class AdministratorAddEngineViewController {
    
    private MainController mainController;
    private SessionManager sessionManager;
    private Engine engine;
    
    
    @FXML private AnchorPane rootPane;
    @FXML private AnchorPane contentPane;
    
    @FXML private Label titleLabel;
    
    @FXML private ChoiceBox<String> brandChoiceBox;
    
    @FXML private TextField engineName;
    @FXML private TextField enginePower;
    @FXML private TextField engineEmission;
    @FXML private TextField engineConsumption;
    @FXML private TextField engineAcceleration;
    @FXML private TextField engineDisplacement;
    @FXML private TextField engineFuel;
    @FXML private TextField enginePrice;
    
    @FXML private Button addEngineButton;
    @FXML private Button confirmButton;
    @FXML private Button exitButton;
    
    public AdministratorAddEngineViewController () {
    
    }
    
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
    
    
    @FXML
    public void initialize() {
        
        Platform.runLater(this::centerContent);
        Platform.runLater(this::loadBrandList);
    }
    
    private void centerContent() {
        double width = rootPane.getWidth();
        
        AnchorPane.setLeftAnchor(titleLabel, (width - titleLabel.getWidth()) / 2);
    }
    
    private void loadBrandList() {
        try {
            String brandpath = "database/brand.csv";
            BufferedReader reader = new BufferedReader(new FileReader(brandpath));
            List<String> brandList = new ArrayList<>();
            String tmp;
            while ((tmp = reader.readLine()) != null) {
                brandList.addAll(Arrays.stream(tmp.split(",")).toList());
            }
        
            brandChoiceBox.getItems().setAll(brandList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void onAddNewEngine() {
            String regex = "[0-9]+";
    
       /* if (engineAcceleration.getText().matches(regex) && engineEmission.getText().matches(regex) &&
                engineConsumption.getText().matches(regex) && engineDisplacement.getText().matches(regex) &&
                enginePower.getText().matches(regex) && enginePrice.getText().matches(regex)) {
    */
            engine = new Engine(brandChoiceBox.getValue(), engineName.getText(), engineFuel.getText(),
                    engineAcceleration.getText(), engineEmission.getText(), engineConsumption.getText(),
                    engineDisplacement.getText(), enginePower.getText(), Double.parseDouble(enginePrice.getText()));
        /*} else {
            mainController.showError("Errore", "Attenzione, sono stati inseriti caratteri non numerici " +
                    "in campi che richiedono delle cifre. Si prega di verificare i dati.");
        }*/

        engine.saveNewEngineToDb();
    }
    
    @FXML
    protected void onConfirm() {
        engine.saveNewEngineToDb();
        mainController.showAlert("Termine procedura","Procedura di inserimento di un motore completata.");
        mainController.loadAdministratorView();
    }
    
    @FXML
    protected void OnExitButton() {
        Platform.exit();
    }
}
