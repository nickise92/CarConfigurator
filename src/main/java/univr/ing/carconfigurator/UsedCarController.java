package univr.ing.carconfigurator;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class UsedCarController {

    private Utente user;
    private MainController mainController;
    private Auto auto;
    private int counter = 0;

    @FXML private AnchorPane rootPane;
    @FXML private AnchorPane imgPane;
    @FXML private Label title;
    @FXML private Label loadedLabel;
    @FXML private Button cancelButton;
    @FXML private TextArea imgPaths;
    @FXML private Button deleteSelected;

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
        // Positioning img area - Gridbox contenente gli image viewer
        AnchorPane.setTopAnchor(imgPane, (height - imgPane.getHeight()) /  2);
        AnchorPane.setLeftAnchor(imgPane, (width/2 - imgPane.getWidth()) / 2);
        // Centering Cancel button
        AnchorPane.setLeftAnchor(cancelButton, (width - cancelButton.getWidth()) / 2);
        // Pulsanti caricamento immagini
        AnchorPane.setLeftAnchor(deleteSelected, (imgPane.getWidth() - deleteSelected.getWidth()) / 2);
    }

    @FXML
    protected void prevImage() {

    }

    @FXML
    protected void nextImage() {

    }

    /**
     * Apre la finestra di dialogo per inserire le immagini dell'auto usata
     * che si vuol far valutare. L'utente può inserire fino a 6 immagini.
     * Il programma crea una cartella con nome = IDUtente+DDHHmmss e all'interno
     * salva (copia) le immagini inserite dall'utente.
     */
    @FXML
    protected void onOpenNewImage() {
        double separator = 10.0;
        Stage stage = new Stage();
        File selectedFile = new FileChooser().showOpenDialog(stage);
        String imgPath = selectedFile.getPath();
        Path inputPath = Paths.get(imgPath);
        Path outputDirectory = Paths.get("img/Usato/"+user.getUserID()+"/");
        Path outputPath = Paths.get(outputDirectory+"/"+user.getUserName()+counter+".jpg");
        counter++;
        try {
            if (!Files.exists(outputDirectory)) {
                Files.createDirectories(outputDirectory);
            }

            Files.copy(inputPath, outputPath, StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Get img name

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
