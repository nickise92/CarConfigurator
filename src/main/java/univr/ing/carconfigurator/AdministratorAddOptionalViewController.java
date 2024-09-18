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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


public class AdministratorAddOptionalViewController {
    
    
    private final String carsPath = "database/car.csv";
    private final String brandsPath = "database/brand.csv";
    private final String interiorsPath = "database/interior.csv";
    private final String tiresPath = "database/tire.csv";
    private final String sensorsPath = "database/sensor.csv";
    private final String enginePath = "database/engine.csv";
    
    
    @FXML private AnchorPane rootPane;
    @FXML private AnchorPane contentPane;
    
    @FXML Label titleLabel;
    
    @FXML private Button engineButton;
    @FXML private Button exitButton;
    @FXML private Button addButton;
    
    @FXML private TextField name;
    @FXML private TextField price;
    
    @FXML private ChoiceBox<OptTypes> Type = new ChoiceBox<>();
    @FXML private ChoiceBox modelChoiceBox;
    @FXML private ChoiceBox brandChoiceBox;
    @FXML  private ObservableList<String> MarcaLista = FXCollections.observableArrayList();
    
    private MainController mainController;
    private String prezzo;
    private String nome;
    
    public AdministratorAddOptionalViewController() {
    
    }
    
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
    
    @FXML
    public void initialize(){
        
        Platform.runLater(this::centerContent);
        
        try {
            Scanner sc = new Scanner(new File(brandsPath));
            for (String car : sc.nextLine().split(",")) {
                if (!car.equals("")) {
                    MarcaLista.add(car);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Impossibile inizializzare le marche di auto.");
        }
        Type.getItems().addAll(OptTypes.values());
        brandChoiceBox.setItems(MarcaLista);
    }

    private void centerContent() {
        double width = rootPane.getWidth();
        double height = rootPane.getHeight();
        
        AnchorPane.setLeftAnchor(titleLabel, (width - titleLabel.getWidth()) / 2);
        AnchorPane.setLeftAnchor(contentPane, (width - contentPane.getWidth()) / 2);
        AnchorPane.setTopAnchor(contentPane, (height - contentPane.getHeight()) / 2);
    }
    
    @FXML
    protected void MarcaChoiceBox() {
        String Brand = (String) brandChoiceBox.getValue();
        ObservableList<String> ModelloLista = FXCollections.observableArrayList();
        File db = new File(carsPath);
        try {
            Scanner sc = new Scanner(db);
            while(sc.hasNextLine()) {
                String[] tmp = sc.nextLine().split(",");
                if (Brand.equals(tmp[0])) {
                    ModelloLista.add(tmp[1]);
                }
            }
            modelChoiceBox.setItems(ModelloLista);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    @FXML
    protected void addOptOnAction() {
        String tipo = String.valueOf(Type.getValue());
        String marca = (String) brandChoiceBox.getValue();
        nome = name.getText();
        prezzo = price.getText();
        if(tipo.equals("CERCHI")){
            try{
                FileWriter fwr = new FileWriter(tiresPath,true );

                String tire =  marca +","+ nome +","+prezzo + ",";
                System.out.println(tire);
                fwr.append(tire + "\n");
                fwr.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(tipo.equals("SENSORI")) {
            try{
                FileWriter fwr = new FileWriter(sensorsPath,true);
                String tire = marca +","+ nome +","+prezzo + ",";
                System.out.println();
                fwr.append(tire + "\n");
                fwr.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
            try{
                FileWriter fwr = new FileWriter(interiorsPath,true);
                String tire = marca +","+ nome +","+prezzo + ",";
                System.out.println();
                fwr.append(tire + "\n");
                fwr.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    @FXML
    protected void AddEngine() {
        mainController.loadAdministratorAddEngine();
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