package univr.ing.carconfigurator;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class AdministratorViewController {

    private MainController mainController;
    private Impiegato impiegato;

    public AdministratorViewController () {

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

    public void onExitButton(ActionEvent actionEvent) {
        Platform.exit();
    }

    public void OnAddModelAction(ActionEvent actionEvent) {
        mainController.loadAdministratorAddCarFirstView();
    }

    public void OnAddOpt(ActionEvent actionEvent) {
        mainController.loadAdministratorAddOptionalView();
    }

    public void OnViewPrev(ActionEvent actionEvent) {
        mainController.loadAdministratorEstimatesViewController();
    }
}
