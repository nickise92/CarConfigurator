package univr.ing.carconfigurator;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.util.Optional;

public class ThirdViewController {

    private Utente user;
    private MainController mainController;
    private Auto configCar;
    private ObservableList<String> shopList = FXCollections.observableArrayList(
            "Bari", "Cagliari", "Milano", "Napoli", "Palermo", "Roma", "Torino", "Verona"
    );

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

    @FXML private ChoiceBox shopChoice;

    @FXML public Label acceleration;
    @FXML public Label engineFuel;
    @FXML public Label engineName;
    @FXML public Label engineEmission;
    @FXML public Label engineConsumption;
    @FXML public Label engineDisplacement;


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
        AnchorPane.setRightAnchor(shopGrid, (width/2 - shopGrid.getWidth()) /2);
        AnchorPane.setBottomAnchor(shopGrid, (height - shopGrid.getHeight()) / 2);
        // Centering del prezzo
        AnchorPane.setTopAnchor(priceGrid, (height/2 - priceGrid.getHeight() * 2) / 2);
        AnchorPane.setLeftAnchor(priceGrid, (width - priceGrid.getWidth()) / 2);
    }

    @FXML
    public void initialize() {
        setAuto(SessionManager.getInstance().getConfiguredAuto());
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

        finalPrice.setText(String.valueOf(configCar.getPrice()) + "0€");


    }

    @FXML
    protected void onConfirmButton() {
        // Se l'utente non e' autenticato viene aperta la schermata
        // apposita, che invita l'utente ad effettuare l'accesso oppure
        // a registrarsi, se non ancora registrato.
        if (!SessionManager.getInstance().isAuthenticated()) {
            mainController.loadSignInView();
        } else {
            if (shopChoice.getValue() == null) {
                showAlert("Attenzione!", "Seleziona una sede di ritiro per la tua auto configurata prima di proseguire!");
            }
            //TODO: Sede di ritiro veicolo obbligatoria!
            //TODO: Genera un preventivo in un file csv
        }

    }

    @FXML
    protected void onEvaluationButton() {

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
