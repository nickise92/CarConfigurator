package univr.ing.carconfigurator;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

public class UsedCarController {

    private Utente user;
    private MainController mainController;
    private Auto auto;

    @FXML private AnchorPane rootPane;
    @FXML private AnchorPane textPane;
    @FXML private AnchorPane imagePane;
    @FXML private Label title;
    @FXML private Button cancelButton;
    @FXML private TextArea imgPaths;

    public UsedCarController() {

    }

    public void setUser(Utente user) {
        this.user = user;
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void setAuto(Auto auto) {
        this.auto = auto;
    }

    @FXML
    public void initialize() {

        Platform.runLater(this::centerContent);
    }

    private void centerContent() {
        double width = rootPane.getWidth();
        double height = rootPane.getHeight();

        // centering title
        AnchorPane.setTopAnchor(title, 50.0);
        AnchorPane.setLeftAnchor(title, (width - title.getWidth()) / 2);
        // Positioning text area
        AnchorPane.setTopAnchor(textPane, (height - textPane.getHeight()) /  2);
        AnchorPane.setLeftAnchor(textPane, (width/2 - textPane.getWidth()) / 2);
        // Positioning image area
        AnchorPane.setTopAnchor(imagePane, (height - imagePane.getHeight()) / 2);
        AnchorPane.setRightAnchor(imagePane, (width/2 - imagePane.getWidth()) / 2);
        // Centering Cancel button
        AnchorPane.setLeftAnchor(cancelButton, (width - cancelButton.getWidth()) / 2);

    }

    @FXML
    protected void prevImage() {

    }

    @FXML
    protected void nextImage() {

    }



    @FXML
    protected void clearAllPaths() {

    }

    @FXML
    protected void onCancelButton() {
        mainController.loadThirdView();
    }

    @FXML
    protected void onExitButton() {
        Platform.exit();
    }

}
