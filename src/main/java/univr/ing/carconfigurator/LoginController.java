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
    protected void handleLoginButtonAction() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        Utente utente = Utente.checkID(username);
        // Se l'utente esiste, e le credenziali sono corrette il sistema verifica a quale
        // categoria di utente appartiene.
        if (utente != null && utente.authenticator(username, password)) {
            // Se l'utente e' un cliente
            if (utente instanceof Cliente) {
                // Imposto l'utente autenticato nella sessione corrente
                // e carico la pagina utente
                SessionManager.getInstance().setAuthenticatedUser(username);
                mainController.loadUserView();
            } else if (utente instanceof Impiegato) {
                SessionManager.getInstance().setAuthenticatedAdmin(username);
                mainController.loadAdministratorView();
            } else if (utente instanceof Venditore) {
                // Se l'utente e' un venditore, imposto il venditore autenticato nella
                // sessione corrente e carico la pagina venditore
                SessionManager.getInstance().setAuthenticatedVendor(username);
                mainController.loadVendorView();
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

    @FXML 
    protected void cancelLoginAction() {
        mainController.loadHomePage();
    }
}
