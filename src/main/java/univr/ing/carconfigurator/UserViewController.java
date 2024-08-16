package univr.ing.carconfigurator;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
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
    private ObservableList<String> orderList = FXCollections.observableArrayList();
    private final String orderPath = "database/";

    @FXML private AnchorPane rootPane;
    @FXML private Button configureCar;
    @FXML private Button logoutButton;
    @FXML private ChoiceBox<String> orderListChoice;
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
        try {
            Scanner sc = new Scanner(new File(orderPath + user.getUserID() +".csv"));

            while (sc.hasNextLine()) {
                String[] line = sc.nextLine().split(",");
                String tmp = line[3] + " " + line[4] + " - " + line[0];
                    orderList.add(tmp);
            }
            orderListChoice.setItems(orderList);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
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
            Scanner sc = new Scanner (new File(orderPath + user.getUserID() + ".csv"));

            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] content = line.split(",");

                if (content[0].equals(orderListChoice.getValue().split(" - ")[1])) {
                    if (showConfirmationDialog( line)) {
                        // TODO: aprire preventivo selezionato
                    } else {
                        // restare sulla view
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
        SessionManager.getInstance().clearSession();
        mainController.loadHomePage();
    }

    @FXML
    protected void onExitButtonClick() {
        Platform.exit();
    }

    public boolean showConfirmationDialog(String header) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Apertura ordine");
        alert.setHeaderText(header);
        alert.setContentText("Vuoi proseguire?");

        Optional<ButtonType> option = alert.showAndWait();

        if (option.get() == ButtonType.OK) {
            return true;
        }
        return false;
    }
}
