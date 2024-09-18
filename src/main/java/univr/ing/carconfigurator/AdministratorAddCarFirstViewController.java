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

public class AdministratorAddCarFirstViewController {

    private MainController mainController;
    private Auto auto;
    
    
    @FXML private AnchorPane rootPane;
    @FXML private AnchorPane contentPaneLeft;
    @FXML private AnchorPane contentPaneRight;
    
    @FXML private ChoiceBox<String> brandChoiceBox;
    @FXML private ChoiceBox<Engine> engineChoiceBox;
    @FXML private ChoiceBox<String> colorChoiceBox;
    
    @FXML private Label titleLabel;
    
    @FXML private Button cancelButton;
    @FXML private Button confirmButton;
    
    @FXML private TextField carModel;
    @FXML private TextField carPrice;
    @FXML private TextField carLength;
    @FXML private TextField carWidth;
    @FXML private TextField carHeight;
    @FXML private TextField carWeight;
    @FXML private TextField carTrunk;
    
    private ObservableList<String> colorsList = FXCollections.observableArrayList("Bianco", "Rosso", "Blu", "Nero", "Grigio");
    
    
    
    public AdministratorAddCarFirstViewController () {
    
    }
    
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
    
    @FXML
    public void initialize() {
        
        Platform.runLater(this::centerContent);
        Platform.runLater(this::loadBrandList);
        Platform.runLater(this::loadColorList);
    }
    
    private void centerContent() {
        double height = rootPane.getHeight();
        double width = rootPane.getWidth();
        
        AnchorPane.setLeftAnchor(cancelButton, ((width - cancelButton.getWidth()) / 2) - confirmButton.getWidth());
        AnchorPane.setRightAnchor(confirmButton, ((width - confirmButton.getWidth()) / 2) - cancelButton.getWidth());
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
    
    private void loadColorList() {
        colorChoiceBox.getItems().setAll(colorsList);
    }
    
    @FXML
    protected void onBrandSelection() {
        
        loadEngineList();
    }
    
    private void loadEngineList() {
        try {
            String enginePath = "database/engine.csv";
            BufferedReader reader= new BufferedReader(new FileReader(enginePath));
            List<Engine> engineList = new ArrayList<>();
            String tmp;
            while ( (tmp = reader.readLine()) != null) {
                if (tmp.split(",")[0].equals(brandChoiceBox.getValue())) {
                    engineList.add(new Engine(tmp.split(",")[1]));
                }
            }
            
            engineChoiceBox.getItems().setAll(engineList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    protected void onEngineSelection() {
    
    }
    
    @FXML
    protected void onColorSelection() {
    
    }
    
    @FXML
    protected void onConfirmButton() {
        String regex = "[0-9]+";
    
        if (carLength.getText().matches(regex) && carHeight.getText().matches(regex) &&
            carWidth.getText().matches(regex) && carWeight.getText().matches(regex) &&
            carPrice.getText().matches(regex) && carTrunk.getText().matches(regex) ) {
            // creazione nuova auto
            auto = new Auto(brandChoiceBox.getValue(), carModel.getText(),
                    Double.parseDouble(carLength.getText()), Double.parseDouble(carWidth.getText()),
                    Double.parseDouble(carHeight.getText()), Double.parseDouble(carWeight.getText()),
                    colorChoiceBox.getValue(), Double.parseDouble(carTrunk.getText()),
                    engineChoiceBox.getValue(), Double.parseDouble(carPrice.getText())
            );
        } else {
            mainController.showError("Errore", "Attenzione, sono stati inseriti caratteri non numerici " +
                    "in campi che richiedono delle cifre. Si prega di verificare i dati.");
        }
    
        auto.saveNewAutoToDb();
        SessionManager.getInstance().setConfiguredAuto(auto);
        mainController.loadAdministratorAddCarSecondView();
    }
    
    @FXML
    protected void onCancelButton() {
        mainController.loadAdministratorView();
    }
    
    @FXML
    protected void onExitButton() {
        Platform.exit();
    }
}
