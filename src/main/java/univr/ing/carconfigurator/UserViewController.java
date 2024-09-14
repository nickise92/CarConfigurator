package univr.ing.carconfigurator;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**
 * Vista utente registrato. In questa sezione viene descritta la logica di controllo
 * dell'interfaccia utente dopo l'accesso. Da qui e' possibile recuperare i preventivi
 * pendenti da confermare, visualizzarne il riepilogo, confermarli o annullarli. Inoltre,
 * e' possibile trovare la notifica di ritiro veicolo quando pronto nella concessionaria di
 * riferimento.
 */
public class UserViewController {

    private MainController mainController;
    private Utente user;
    //private ObservableList<String> orderList = FXCollections.observableArrayList(); // -- Deprecated --
    private ObservableList<Preventivo> listaPreventivi = FXCollections.observableArrayList();
    private final String orderPath = "database/";
    private Preventivo preventivo;

    @FXML private AnchorPane rootPane;
    @FXML private Button configureCar;
    @FXML private Button logoutButton;
    @FXML private ChoiceBox<Preventivo> orderListChoice;
    @FXML private GridPane orderBox;
    @FXML private Label userLogged;
    @FXML private Label title;

    public UserViewController () {

    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void setUser(Utente user) {
        this.user = user;
    }

    @FXML
    public void initialize() {

        Platform.runLater(this::updateUserAccessStatus);
        Platform.runLater(this::centerContent);
        Platform.runLater(this::getOrderList);
    }

    private void updateUserAccessStatus() {
        if (userLogged != null && user != null) {
            userLogged.setText(user.getUserName() + " " + user.getUserLastName());
        }
    }

    private void getOrderList() {
        // Vogliamo inserire nella lista dei preventivi confermabili solo
        // quelli fatti negli ultimi 20 giorni.

        try {

            Scanner sc = new Scanner(new File("database/preventivi.csv"));

            while (sc.hasNextLine()) {
                String[] line = sc.nextLine().split(",");
                Auto tmp = new Auto(line[3], line[4]); // Creo l'auto di serie passando brand e modello
                tmp.setColor(line[5]);
                tmp.setEngine(new Engine(line[6]));
                tmp.setCircle(new Optional(line[7], OptTypes.CERCHI));
                tmp.setSensor(new Optional(line[8], OptTypes.SENSORI));
                tmp.setInterior(new Optional(line[9], OptTypes.INTERNI));
                tmp.setPrice(Double.parseDouble(line[10]));
                preventivo = new Preventivo(line[0], line[1], LocalDate.parse(line[2]), tmp, line[12]);
                if (Preventivo.checkOrderValidity(preventivo)) {
                    listaPreventivi.add(preventivo);
                }
                // Altrimenti lo ignoro
            }
            orderListChoice.setItems(listaPreventivi);


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Verifica della data
    private boolean orderValidityCheck(LocalDate orderDate){
        LocalDate currentDate = LocalDate.now();
        return ChronoUnit.DAYS.between(orderDate, currentDate) <= 20;
    }

    private void centerContent() {
        double width = rootPane.getWidth();
        double height = rootPane.getHeight();

        // Centering title and user
        AnchorPane.setLeftAnchor(title, (width - title.getWidth()) / 2);
        AnchorPane.setTopAnchor(title, 30.0);
        AnchorPane.setLeftAnchor(userLogged, (width - userLogged.getWidth()) / 2);
        AnchorPane.setTopAnchor(userLogged, 90.0);
        // Configure Car button pos
        AnchorPane.setTopAnchor(configureCar, (height - configureCar.getHeight()) / 2);
        AnchorPane.setLeftAnchor(configureCar, (width/2 - configureCar.getWidth()) / 2);
        // Order grid position
        AnchorPane.setTopAnchor(orderBox, (height - orderBox.getHeight()) / 2);
        AnchorPane.setRightAnchor(orderBox, (width/2 - orderBox.getWidth()) / 2);
        // Logout button
        AnchorPane.setLeftAnchor(logoutButton, (width - logoutButton.getWidth()) / 2);
    }

    @FXML
    protected void openSelectedOrder() {
        try {
            Scanner sc = new Scanner (new File("database/preventivi.csv"));

            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] content = line.split(",");

                if (content[0].equals(orderListChoice.getValue().getOrderID())) {
                    if (mainController.showConfirmationAlert("Apertura ordine",
                            "Stai aprendo l'ordine #" + preventivo.getOrderID(),
                            "Continuare?")) {
                        SessionManager.getInstance().setOpenOrder(orderListChoice.getValue());
                        mainController.loadUserOrderConfirmationView();
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void startCarConfiguration() {
        SessionManager.getInstance().setAuthenticatedUser(user.getUserID());
        mainController.loadFirstView();
    }

    @FXML
    protected void onLogout() {
        if (mainController.showConfirmationAlert("Attenzione",
                "Stai uscendo dalla tua area riservata, le configurazioni non completate verranno perse",
                "Sei sicuro di voler uscire?")) {
            SessionManager.getInstance().clearSession();
            mainController.loadHomePage();
        }
    }

    @FXML
    protected void onExitButtonClick() {
        Platform.exit();
    }

}
