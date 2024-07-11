package univr.ing.carconfigurator;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class SignInController {


    MainController mainController;
    Utente user;

    @FXML private AnchorPane rootPane;
    @FXML private AnchorPane loginPane;
    @FXML private AnchorPane registrationPane;

    @FXML private GridPane loginGrid;

    // loginPane
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;

    // registrationPane
    @FXML private TextField userID;
    @FXML private TextField userName;
    @FXML private TextField userLastName;
    @FXML private PasswordField userPsw1;
    @FXML private PasswordField userPsw2;
    @FXML private Button registrationButton;

    @FXML private Button exitButton;
    @FXML private Label tips;


    public SignInController() {

    }

    @FXML
    public void initialize() {


        Platform.runLater(this::centerContent);
    }


    private void centerContent() {
        double width = rootPane.getWidth();
        double height = rootPane.getHeight();


        // Centering tips label
        AnchorPane.setTopAnchor(tips, 25.0);
        AnchorPane.setLeftAnchor(tips, (width - tips.getWidth()) / 2);
        // Form di login, allineato a sinistra
        AnchorPane.setLeftAnchor(loginPane, (width/2 - loginPane.getWidth()) / 2);
        AnchorPane.setTopAnchor(loginPane, (height - loginPane.getHeight()) / 2);
        // Form di registrazione, allineato a destra
        AnchorPane.setRightAnchor(registrationPane, (width/2 - registrationPane.getWidth()) / 2);
        AnchorPane.setTopAnchor(registrationPane, (height - registrationPane.getHeight()) / 2);

    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void setUser(Utente user) {
        this.user = user;
    }

    @FXML
    public void onCancelButton() {

    }

    @FXML
    public void onConfirmButton() {

    }

    @FXML
    public void onLoginButton() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        Utente utente = Utente.checkID(username);
        // Se l'utente esiste, e le credenziali sono corrette il sistema verifica a quale
        // categoria di utente appartiene.
        if (utente != null && utente.authenticator(username, password)) {
            // Imposto l'utente autenticato nella sessione corrente
            SessionManager.getInstance().setAuthenticatedUser(username);

            if (utente instanceof Cliente) {
                mainController.loadThirdView();
            }

        } else {
            showAlert("Autenticazione fallita", "Username o password errati.");
        }

    }

    @FXML
    public void cancelLoginAction() {

    }

    @FXML
    public void onExitButton() {
        Platform.exit();

    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
