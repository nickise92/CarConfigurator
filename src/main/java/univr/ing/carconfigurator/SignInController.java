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
    Auto auto;

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

    public void setAuto(Auto auto) {
        this.auto = auto;
    }

    @FXML
    public void onConfirmButton() {
        if (userPsw1.getText().equals(userPsw2.getText())) {
            user = new Utente(userID.getText(), userName.getText(), userLastName.getText(), userPsw1.getText());
            showAlert("Registrazione confermata!", "Il cliente " + user.getUserName() + " " + user.getUserLastName() + " è stato registrato " +
                    "con successo.", 1);
            SessionManager.getInstance().setAuthenticatedUser(user.getUserID());
            mainController.loadThirdView();
        } else {
            showAlert("Attenzione!", "Le password non corrispondono, si prega di reinserirle.", 2);
        }
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
            showAlert("Autenticazione fallita", "Username o password errati.", 1);
        }

    }

    @FXML
    public void onExitButton() {
        Platform.exit();

    }

    public void showAlert(String title, String message, int type) {
        if (type == 1) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        } else if (type == 2) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        }


    }
}
