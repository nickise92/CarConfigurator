package univr.ing.carconfigurator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

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
    public Button EngineButton;
    public Button ExitButton;
    public Button AddButton;
    @FXML
    public TextField Name;
    @FXML
    public TextField price;
    private String prezzo;
    private String nome;
    public ChoiceBox<OptTypes> Type = new ChoiceBox<>();
    public ChoiceBox Model;
    public ChoiceBox Marca;
    @FXML
    public ObservableList<String> MarcaLista = FXCollections.observableArrayList();
    private MainController mainController;

    public AdministratorAddOptionalViewController() {}
    public void setMainController(MainController mainController) {this.mainController = mainController; }
    @FXML
    public void initialize(){
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
        Marca.setItems(MarcaLista);
    }

    public void MarcaChoiceBox(ActionEvent actionEvent) {
        String Brand = (String) Marca.getValue();
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
            Model.setItems(ModelloLista);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void addOptOnAction() {
        String tipo = String.valueOf(Type.getValue());
        String marca = (String) Marca.getValue();
        nome = Name.getText();
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
    protected void AddEngine(ActionEvent actionEvent) {
        mainController.loadAdministratorAddEngine();
    }

    public void onExitButton(ActionEvent actionEvent) {
        mainController.loadAdministratorview();
    }
}