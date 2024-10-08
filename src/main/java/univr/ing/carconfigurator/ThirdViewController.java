package univr.ing.carconfigurator;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;

public class ThirdViewController {

    private String orderPath = "database/";
    private Utente user;
    private MainController mainController;
    private Auto configCar;
    private ObservableList<String> shopList = FXCollections.observableArrayList(
            "Bari", "Cagliari", "Milano", "Napoli", "Palermo", "Roma", "Torino", "Verona"
    );

    private Preventivo preventivo;

    @FXML private AnchorPane rootPane;
    @FXML private AnchorPane contentPane;
    @FXML private Label titleText;
    @FXML private Label userLogged;
    @FXML private Label userLabel;
    @FXML private Button exitButton;
    @FXML private Button goBackButton;
    @FXML private Button confirmButton;
    @FXML private Button evaluationButton;
    @FXML private GridPane descGrid;
    @FXML private GridPane engineGrid;
    @FXML private GridPane optionalGrid;
    @FXML private GridPane requestButton;
    @FXML private GridPane shopGrid;
    @FXML private GridPane priceGrid;

    @FXML private ChoiceBox<String> shopChoice;

    @FXML private Label acceleration;
    @FXML private Label engineFuel;
    @FXML private Label engineName;
    @FXML private Label engineEmission;
    @FXML private Label engineConsumption;
    @FXML private Label engineDisplacement;
    @FXML private Label enginePower;


    @FXML private Label carHeight;
    @FXML private Label carWidth;
    @FXML private Label carLength;
    @FXML private Label trunkVol;
    @FXML private Label carWeight;

    @FXML private Label carColor;
    @FXML private Label carSensorPack;
    @FXML private Label carTire;
    @FXML private Label carInterior;

    @FXML private Label finalPrice;




    public ThirdViewController() {

    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void setUser(Utente user) {
        this.user = user;
    }

    public void setAuto(Auto configCar) {
        this.configCar = configCar;
    }

    private void updateUserAccessStatus() {
        if (userLogged != null && user != null) {
            userLogged.setText("Connesso");
            userLogged.setStyle("-fx-text-fill: #009900");
            userLabel.setText(user.getUserName() + " " + user.getUserLastName());
        } else {
            userLogged.setText("Non connesso");
            userLogged.setStyle("-fx-text-fill: #FF0000");
            userLabel.setText("Ospite");
        }
    }

    private void centerContent() {
        double width = rootPane.getWidth();
        double height = rootPane.getHeight();

        // Centering del titolo
        AnchorPane.setLeftAnchor(titleText, (width - titleText.getWidth()) / 2);
        // Centering del bottone
        AnchorPane.setLeftAnchor(goBackButton, (width - goBackButton.getWidth()) / 2);
        // Centering contentPane
        AnchorPane.setTopAnchor(contentPane, (height - contentPane.getHeight()) / 2);
        AnchorPane.setLeftAnchor(contentPane, (width - contentPane.getWidth()) / 2);
        // Centering confirmation button gridPane
        AnchorPane.setBottomAnchor(requestButton, (height - requestButton.getHeight()) / 6);
        AnchorPane.setLeftAnchor(requestButton, (width - requestButton.getWidth()) / 2);
        // Centering shopPane
        AnchorPane.setRightAnchor(shopGrid, (contentPane.getWidth()/2 - shopGrid.getWidth()) / 2);
        AnchorPane.setBottomAnchor(shopGrid, (contentPane.getHeight()/2 - shopGrid.getHeight()) / 2);
        // Centering del prezzo
        AnchorPane.setTopAnchor(priceGrid, (height/2 - priceGrid.getHeight() * 2) / 2);
        AnchorPane.setLeftAnchor(priceGrid, (width - priceGrid.getWidth()) / 2);
    }

    @FXML
    public void initialize() {
        setAuto(SessionManager.getInstance().getConfiguredAuto());
        setUser(SessionManager.getInstance().getAuthenticatedUser());
        shopChoice.setItems(shopList);


        Platform.runLater(this::centerContent);
        Platform.runLater(this::updateUserAccessStatus);
        Platform.runLater(this::showConfiguredCar);
    }

    public void showConfiguredCar() {
        carLength.setText(String.valueOf(configCar.getLength()));
        carWidth.setText(String.valueOf(configCar.getWidth()));
        carHeight.setText(String.valueOf(configCar.getHeight()));
        trunkVol.setText(String.valueOf(configCar.getTrunkVol()));
        carWeight.setText(String.valueOf(configCar.getWeight()));

        engineName.setText(String.valueOf(configCar.getEngine().getName()));
        engineFuel.setText(String.valueOf(configCar.getEngine().getFuelType()));
        acceleration.setText(String.valueOf(configCar.getEngine().getAccelerationTime()));
        engineConsumption.setText(String.valueOf(configCar.getEngine().getConsumption()));
        engineEmission.setText(String.valueOf(configCar.getEngine().getGramsCO2perKm()));
        engineDisplacement.setText(String.valueOf(configCar.getEngine().getDisplacement()));
        enginePower.setText(configCar.getEngine().getPower());

        if (configCar.getColor() == null) {
            carColor.setText("Bianco");
        } else {
            carColor.setText(configCar.getColor());
        }
        if (configCar.getSensor() != null) {
            carSensorPack.setText(configCar.getSensor().toString());
        }
        if (configCar.getCircle() != null) {
            carTire.setText(configCar.getCircle().toString());
        } else {
            carTire.setText("Cerchi di serie");
        }
        if (configCar.getInterior() != null) {
            carInterior.setText(configCar.getInterior().toString());
        } else {
            carInterior.setText("Interni di serie");
        }
        finalPrice.setText(configCar.getPrice() + "0€");
    }

    @FXML
    protected void onConfirmButton() {
        // Se l'utente non e' autenticato viene aperta la schermata
        // apposita, che invita l'utente ad effettuare l'accesso oppure
        // a registrarsi, se non ancora registrato.
        if (!SessionManager.getInstance().isUserAuthenticated()) {
            mainController.loadSignInView();
        } else {
            if (shopChoice.getValue() == null) {
                showAlert("Attenzione!", "Seleziona una sede di ritiro per la tua auto configurata prima di proseguire!");
            } else {
                // Creo l'ID del preventivo: giorno, ora, minuti, secondi
                String orderID = LocalDate.now().getDayOfMonth() + "" + LocalTime.now().getHour() + "" +
                        LocalTime.now().getMinute() + "" + LocalTime.now().getSecond();
                preventivo = new Preventivo(orderID, user.getUserID(), LocalDate.now(), configCar, shopChoice.getValue());
                try {
                    preventivo.saveToDb();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                SessionManager.getInstance().setOpenQuotation(preventivo);
                String message = "Complimenti, " + user.getUserName() + " " + user.getUserLastName() + ", " +
                        "la Sua auto è stata configurata con successo. Può trovare il riepilogo nell'area utente.";
                showAlert("Configurazione salvata.", message);
                mainController.loadUserView();
            }
        }
    }

    @FXML
    protected void onEvaluationButton() {
        if (SessionManager.getInstance().isUserAuthenticated()) {
            mainController.loadUsedCarView();
        } else {
            mainController.loadSignInView();
        }
    }

    @FXML
    protected void onGoBackButton() {

        if (mainController.showBackAlert()) {
            mainController.loadSecondView();
        }
    }

    @FXML
    protected void onExitButtonClick() {
        Platform.exit();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
