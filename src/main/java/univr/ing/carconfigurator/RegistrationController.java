package univr.ing.carconfigurator;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class RegistrationController {

    public AnchorPane rootPane;
    public AnchorPane registrationForm;
    private MainController mainController;

    @FXML private Button registrationButton;
    @FXML private TextField userID;
    @FXML private TextField userName;
    @FXML private TextField userLastName;
    @FXML private PasswordField userPsw1;
    @FXML private PasswordField userPsw2;


    public RegistrationController() {

    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    public void initialize() {
        rootPane.widthProperty().addListener((obs, oldVal, newVal) -> centerContent());
        rootPane.heightProperty().addListener((obs, oldVal, newVal) -> centerContent());



        Platform.runLater(this::centerContent);
    }

    private void centerContent() {
        double width = rootPane.getWidth();
        double height = rootPane.getHeight();

        AnchorPane.setLeftAnchor(registrationForm, (width - registrationForm.getWidth())/2);
        AnchorPane.setTopAnchor(registrationForm, (height - registrationForm.getHeight())/2);
    }

    @FXML
    protected void onConfirmButton() {

        if (userPsw1.getText().equals(userPsw2.getText())) {
            Utente user = new Utente(userID.getText(), userName.getText(), userLastName.getText(), userPsw1.getText());
            showAlert("Registrazione confermata!", "Il cliente " + user.getUserName() + " " + user.getUserLastName() + " è stato registrato " +
                    "con successo.", 1);
            mainController.loadHomePage();
        } else {
            showAlert("Attenzione!", "Le password non corrispondono, si prega di reinserirle.", 2);
        }
    }

    @FXML
    protected void onCancelButton() {
        mainController.loadHomePage();
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
