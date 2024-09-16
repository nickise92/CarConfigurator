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

public class AdministratorAddEngineViewController {
    private MainController mainController;
    public AdministratorAddEngineViewController () {}
    public void setMainController(MainController mainController) {this.mainController = mainController; }

    private final String enginePath = "database/engine.csv";
    private final String brandsPath = "database/brand.csv";
    @FXML public TextField enginePrezzo;
    String price;
    String Model;
    @FXML public TextField engineName;
    String nome;
    @FXML public TextField enginePower;
    String potenza;
    @FXML public TextField engineEmission;
    String emissione;
    @FXML public TextField engineConsuption;
    String Consumi;
    @FXML public TextField accelleration;
    String Accelerazione;
    @FXML public TextField engineDisplacement;
    String Cilindrata;
    @FXML public TextField engineFuel;
    String Tipologia_Carburante;
    public ChoiceBox MarcaChoiceBox;
    public ObservableList<String> MarcaLista = FXCollections.observableArrayList();
    @FXML public Button AddButton;
    @FXML public Button ExitButton;

    public void initialize() {
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
        MarcaChoiceBox.setItems(MarcaLista);
    }

    public void OnAddButton(ActionEvent actionEvent) {
        nome = engineName.getText();
        potenza = enginePower.getText();
        emissione = engineEmission.getText();
        Accelerazione = accelleration.getText();
        Tipologia_Carburante = engineFuel.getText();
        Cilindrata = engineDisplacement.getText();
        Consumi = engineConsuption.getText();
        Model = (String) MarcaChoiceBox.getValue();
        price = enginePrezzo.getText();
        String stringa = Model + "," + nome + ","
                + Tipologia_Carburante + "," + Accelerazione + "s" +","
                + emissione + "g/km" +"," + Consumi + "l/100km" +"," + Cilindrata +"," + potenza + "kw"+"," + price +",";
        try{
            FileWriter fwr = new FileWriter(enginePath,true );
            System.out.println(stringa);
            fwr.append(stringa + "\n");
            fwr.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void OnExitButton(ActionEvent actionEvent) {
        mainController.loadAdministratorAddOptionalView();
    }
}
