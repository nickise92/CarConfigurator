package univr.ing.carconfigurator;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class HomeController {

    public Button registerButton;
    @FXML private Button configuratorStartButton;
    @FXML private Button loginButton;
    @FXML private AnchorPane rootPane;
    @FXML private VBox welcomeVBox;
    @FXML private Label userLabel;

    @FXML private Label titleLabel;
    @FXML private Text descriptionText;



    private Utente user;
    private MainController mainController;

    public HomeController() {
    }

    public void setUser(Utente user) {
        this.user = user;
        updateUserLabel();
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    private void updateUserLabel() {
        if (userLabel != null && user != null) {
            userLabel.setText("Utente: "+ user.getUserName() + " " + user.getUserLastName());
        }

        if (userLabel.getText().equals("Accesso non eseguito")) {
            userLabel.setStyle("-fx-text-fill: #FF0000;");
        } else {
            userLabel.setStyle("-fx-text-fill: #009900;");
        }
    }

    @FXML
    public void initialize() {
        // Aggiungo listener per centrare il VBox
        rootPane.widthProperty().addListener((obs, oldVal, newVal) -> centerContent());
        rootPane.heightProperty().addListener((obs, oldVal, newVal) -> centerContent());

        Platform.runLater(this::centerContent);
    }

    private void centerContent() {
        double width = rootPane.getWidth();
        double height = rootPane.getHeight();
        double vboxWidth = welcomeVBox.getWidth();
        double vboxHeight = welcomeVBox.getHeight();

        // VBox centering
        AnchorPane.setLeftAnchor(welcomeVBox, (width - vboxWidth) / 2);
        AnchorPane.setTopAnchor(welcomeVBox, (height - vboxHeight) / 2);
        // Title and description Label centering
        AnchorPane.setLeftAnchor(titleLabel, (width - titleLabel.getWidth()) / 2);
        AnchorPane.setLeftAnchor(descriptionText, (width - descriptionText.getWrappingWidth()) / 2);
        // User Label centering
        AnchorPane.setLeftAnchor(userLabel, (width - userLabel.getWidth()) / 2);
    }

    @FXML
    protected void onLoginButtonAction() {
        mainController.loadLoginPage();
    }

    @FXML
    protected void onConfigurationStartAction() {
        mainController.loadFirstView();
    }

    @FXML
    protected void exitButtonHandler() {
        Platform.exit();
    }


    public void onRegisterButton() {
        mainController.loadRegistrationView();
    }
}
