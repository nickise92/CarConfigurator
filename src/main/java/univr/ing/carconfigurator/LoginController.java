package univr.ing.carconfigurator;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class LoginController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private GridPane loginGrid;
    @FXML private AnchorPane rootPane;

    private MainController mainController;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    public void initialize() {
        // Listener dimensini finestra principale
        rootPane.widthProperty().addListener((obs, oldVal, newVal) -> centerContent());
        rootPane.heightProperty().addListener((obs, oldVal, newVal) -> centerContent());

        Platform.runLater(this::centerContent);
    }

    private void centerContent() {
        double width = rootPane.getWidth();
        double height = rootPane.getHeight();

        AnchorPane.setLeftAnchor(loginGrid, (width - loginGrid.getWidth()) / 2);
        AnchorPane.setTopAnchor(loginGrid, (height - loginGrid.getHeight()) / 2);

    }

    @FXML
    private void handleLoginButtonAction(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();
        Utente utente = Utente.checkID(username);
        // Se l'utente esiste, e le credenziali sono corrette il sistema verifica a quale
        // categoria di utente appartiene.
        if (utente != null && utente.authenticator(username, password)) {
            // Imposto l'utente autenticato nella sessione corrente
            SessionManager.getInstance().setAuthenticatedUser(username);

            if (utente instanceof Cliente) {
                mainController.loadUserView();
            } else if (utente instanceof Impiegato) {
                //TODO: vista impiegato e logica conseguente
            } else if (utente instanceof Venditore) {
                mainController.loadGestioneOrdini();
            }

        } else {
            showAlert("Autenticazione fallita", "Username o password errati.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void cancelLoginAction() {
        mainController.loadHomePage();
    }
}
