package univr.ing.carconfigurator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.text.Text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Scanner;

public class AdministratorEstimatesViewController {


    private MainController mainController;
    public AdministratorEstimatesViewController (){}

    public void setMainController(MainController mainController) { this.mainController = mainController;}

    @FXML public Text Utente;
    @FXML public Text DataPrev;
    @FXML public Text Marca;
    @FXML public Text Model;
    @FXML public Text Colour;
    @FXML public Text Fuel;
    @FXML public Text tire;
    @FXML public Text used;
    @FXML public Text price;
    public ChoiceBox ViewChoiceBox;
    private String View;
    private ObservableList<String> view = FXCollections.observableArrayList("Cliente", "Marca",  "Sede di Ritiro");
    public void initialize() {
        ViewChoiceBox.setItems(view);
    }
    public void OnIndietro(ActionEvent actionEvent) {
    }

    public void OnAvanti(ActionEvent actionEvent) {
    }

    public void OnExitButton(ActionEvent actionEvent) {
        mainController.loadAdministratorview();
    }

    public void OnChoiceBoxAction(ActionEvent actionEvent) {
        if (ViewChoiceBox.getValue().equals("Cliente")) {
            System.out.println("Cliente");
            try {
                // ERRORE CHE DICE "Index 1 out of bounds for length 1"
                LinkedList<Preventivo> preventivoLinkedList = new LinkedList<>();
                Scanner sc = new Scanner(new File("database/preventivi.csv"));

                // Ignora l'intestazione (se presente)
                sc.nextLine();

                while (sc.hasNextLine()) {
                    String line = sc.nextLine();  // Leggi la nuova linea
                    Preventivo prev = new Preventivo(line);
                    preventivoLinkedList.add(prev);
                }

                Collections.sort(preventivoLinkedList, new Comparator<Preventivo>() {
                    public int compare(Preventivo o1, Preventivo o2) {
                        return o1.getUserID().compareTo(o2.getUserID());
                    }
                });

                System.out.println(preventivoLinkedList);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        else if (ViewChoiceBox.getValue().equals("Marca")) {
            System.out.println("Marca");
            // TODO: DA FINIRE LA VISUALIZZAZIONE
        }
        else {
            System.out.println("Sede di ritiro");
            // TODO : DA FINIRE LA VISUALIZZAZIONE
        }
    }
}
