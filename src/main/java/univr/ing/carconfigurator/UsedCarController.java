package univr.ing.carconfigurator;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    @FXML private Button cancelButton;
    @FXML private Button confirmImg;
    @FXML private ImageView topLeft;
    @FXML private ImageView topRight;
    @FXML private ImageView centerLeft;
    @FXML private ImageView centerRight;
    @FXML private ImageView bottomLeft;
    @FXML private ImageView bottomRight;

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
        confirmImg.setDisable(true);
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
        // Centering pulsante conferma
        AnchorPane.setRightAnchor(confirmImg, (width / 2 - confirmImg.getWidth()) / 2);
        AnchorPane.setTopAnchor(confirmImg, (height - confirmImg.getHeight())/2);
    }

    /**
     * Apre la finestra di dialogo per inserire le immagini dell'auto usata
     * che si vuol far valutare. L'utente può inserire fino a 6 immagini.
     * Il programma crea una cartella con nome = IDUtente+DDHHmmss e all'interno
     * salva (copia) le immagini inserite dall'utente.
     */
    @FXML
    protected void onOpenNewImage() {

        Stage stage = new Stage();
        File selectedFile = new FileChooser().showOpenDialog(stage);
        String imgPath = selectedFile.getPath();
        Path inputPath = Paths.get(imgPath);
        Path outputDirectory = Paths.get("img/Usato/"+user.getUserID()+"/");
        Path outputPath = Paths.get(outputDirectory+"/"+user.getUserName()+counter+".jpg");


        try {
            if (!Files.exists(outputDirectory)) {
                Files.createDirectories(outputDirectory);
            }

            Files.copy(inputPath, outputPath, StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException e) {
            e.printStackTrace();
        }

        switch (counter) {
            case 0:
                setCarImg(String.valueOf(outputPath), topLeft);
                break;
            case 1:
                setCarImg(String.valueOf(outputPath), topRight);
                break;
            case 2:
                setCarImg(String.valueOf(outputPath), centerLeft);
                break;
            case 3:
                setCarImg(String.valueOf(outputPath), centerRight);
                break;
            case 4:
                setCarImg(String.valueOf(outputPath), bottomLeft);
                break;
            case 5:
                setCarImg(String.valueOf(outputPath), bottomRight);
                confirmImg.setDisable(false);
                break;
            default:
                System.out.println("Impossibile caricare immagini.");
                break;
        }
        counter++;
    }

    @FXML
    protected void setCarImg(String path, ImageView carImg) {
        Image img = new Image(new File(path).toURI().toString());
        carImg.setImage(img);
    }

    @FXML
    protected void submitLoadedImg() {
        mainController.showAlert("Termine procedura",
                "Procedura di inserimento usato completata, il referente del " +
                        "negozio da Lei scelto prenderà in carico la richiesta il prima possibile");
        // Imposta la flag che determina la richiesta di valutazione
        SessionManager.getInstance().setUsedEvaluationRequested(true);
        mainController.loadThirdView();
    }

    // Torna indietro alla vista di riepilogo
    @FXML
    protected void onCancelButton() {
        mainController.loadThirdView();
    }

    @FXML
    protected void onExitButton() {
        Platform.exit();
    }

}
