package univr.ing.carconfigurator;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class AdministratorAddCarSecondViewController {
    
    private MainController mainController;
    private SessionManager sessionManager;
    private Auto auto;
    private int counter = 0;
    
    @FXML private AnchorPane rootPane;
    @FXML private AnchorPane contentPane;
    @FXML private AnchorPane controlsPane;
    
    @FXML private Button addImageButton;
    @FXML private Button confirmButton;
    
    @FXML private Label titleLabel;
    
    @FXML private ImageView topLeft;
    @FXML private ImageView topRight;
    @FXML private ImageView centerLeft;
    @FXML private ImageView centerRight;
    @FXML private ImageView bottomLeft;
    @FXML private ImageView bottomRight;
    
    
    public AdministratorAddCarSecondViewController() {
    
    }
    
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
    
    
    @FXML
    public void initialize() {
        auto = SessionManager.getInstance().getConfiguredAuto();
        confirmButton.setDisable(true);
    }
    
    
    @FXML
    protected void onOpenNewImage() {
        
        Stage stage = new Stage();
        File selectedFile = new FileChooser().showOpenDialog(stage);
        String imgPath = selectedFile.getPath();
        Path inputPath = Paths.get(imgPath);
        Path outputDirectory = Paths.get("img/"+ auto.getBrand() + "/" + auto.getModel());
        Path outputPath = Paths.get(outputDirectory+"/"+counter+".jpg");
        
        
        try {
            if (!Files.exists(outputDirectory)) {
                Files.createDirectories(outputDirectory);
            }
            
            Files.copy(inputPath, outputPath, StandardCopyOption.REPLACE_EXISTING);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    
        switch (counter) {
            case 0 -> setCarImg(String.valueOf(outputPath), topLeft);
            case 1 -> setCarImg(String.valueOf(outputPath), topRight);
            case 2 -> setCarImg(String.valueOf(outputPath), centerLeft);
            case 3 -> setCarImg(String.valueOf(outputPath), centerRight);
            case 4 -> setCarImg(String.valueOf(outputPath), bottomLeft);
            case 5 -> {
                setCarImg(String.valueOf(outputPath), bottomRight);
                confirmButton.setDisable(false);
            }
            default -> System.out.println("Impossibile caricare immagini.");
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
        mainController.showAlert("Termine procedura","Procedura di inserimento auto completata.");
        mainController.loadAdministratorView();
    }
    
    @FXML
    protected void onExitButton() {
        mainController.loadAdministratorView();
    }
}
