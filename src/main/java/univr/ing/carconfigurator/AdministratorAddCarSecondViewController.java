package univr.ing.carconfigurator;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class AdministratorAddCarSecondViewController {

    @FXML
    private TextField Larghezza;
    private String larghezza;
    @FXML
    private TextField Lunghezza;
    private String lunghezza;
    @FXML
    private TextField Volume_Bagagliaio;
    private String VolumeBagagliaio;
    @FXML
    private TextField Altezza;
    private String altezza;
    @FXML
    private TextField Peso;
    private String peso;
    private String AutoCsv;
    private MainController mainController;
    public AdministratorAddCarSecondViewController () { }
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
    private void updateCarConfig(){

        AutoCsv = SessionManager.getInstance().getCarCsv();
    }
    private void initialize(){
        Platform.runLater(this::updateCarConfig);
    }
    @FXML
    public void onGoForwardAction() {
        larghezza = Larghezza.getText();
        lunghezza = Lunghezza.getText();
        altezza = Altezza.getText();
        peso = Peso.getText();
        VolumeBagagliaio = Volume_Bagagliaio.getText();
        String Info = larghezza + "," + lunghezza + "," + altezza + "," + VolumeBagagliaio+ ","+ peso + ",";


        SessionManager.getInstance().setCarCsv(Info);
        /// DEBUG
        System.out.println(Info);
        mainController.loadAdministratorAddCarthirdView();
    }

    public void onExitButton(ActionEvent actionEvent) { mainController.loadAdministratorview();}
}
