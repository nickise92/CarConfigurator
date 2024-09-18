package univr.ing.carconfigurator;


import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class AdministratorViewController {

    private MainController mainController;
    private Impiegato impiegato;

    @FXML private AnchorPane root;
    @FXML private AnchorPane contentPane;
    @FXML private Button logoutButton;
    @FXML private Button exitButton;
    @FXML private Button addModel;
    @FXML private Button addOpt;
    @FXML private Button viewPrev;
    @FXML private Label adminLogged;

    public AdministratorViewController () {

    }

    @FXML
    public void initialize() {
        Platform.runLater(this::centerContent);
        Platform.runLater(this::updateAdminLogged);
    }

    private void centerContent() {
        double width = root.getWidth();
        double height = root.getHeight();

        // Contenuto
        AnchorPane.setTopAnchor(contentPane, (height - contentPane.getHeight()) / 2);
        AnchorPane.setLeftAnchor(contentPane, (width - contentPane.getWidth()) / 2);
    }

    private void updateAdminLogged() {
        adminLogged.setText("Amministratore: " + SessionManager.getInstance().getAuthenticatedAdmin());
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void setImpiegato(Impiegato impiegato) {
        this.impiegato = impiegato;
    }

    public Impiegato getImpiegato() {
        return this.impiegato;
    }

    @FXML
    protected void onLogoutButton() {
        SessionManager.getInstance().clearSession();
        mainController.loadHomePage();
    }

    public void onExitButton() {
        Platform.exit();
    }

    public void OnAddModelAction() {
        mainController.loadAdministratorAddCarFirstView();
    }

    public void OnAddOpt() {
        mainController.loadAdministratorAddOptionalView();
    }

    public void OnViewPrev() {
        mainController.loadAdministratorEvaluesViewController();
    }
}
