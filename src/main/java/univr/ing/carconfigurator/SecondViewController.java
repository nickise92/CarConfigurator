package univr.ing.carconfigurator;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class SecondViewController {


    public Label partialPrice;
    public TextArea riepilogo;
    public ImageView fourth;
    public ImageView fifth;
    public ImageView sixth;
    public ImageView seventh;
    public ImageView eighth;
    public ImageView ninth;
    public Button exitButton;
    private MainController mainController;
    private Utente user;
    private Auto configCar;

    @FXML private Label userLogged;
    @FXML private Label userLabel;
    @FXML private Label titleText;
    @FXML private GridPane descGrid;
    @FXML private GridPane engineDesc;
    @FXML private GridPane tireChoice;
    @FXML private GridPane navigationControls;
    @FXML private AnchorPane rootPane;
    @FXML private AnchorPane imageAnchor;
    @FXML private AnchorPane pannelloRiepilogo;
    @FXML private ImageView first;
    @FXML private ImageView second;
    @FXML private ImageView third;
    @FXML private ImageView carImg;

    public SecondViewController() {

    }

    private void updateUserAccessStatus() {
        if (userLogged != null && user != null) {
            userLogged.setText("Connesso");
            userLogged.setStyle("-fx-text-fill: #009900");
            userLabel.setText(user.getUserName() + " " + user.getUserLastName());

        } else {
            userLogged.setText("Non connesso");
            userLogged.setStyle("-fx-text-fill: #FF0000");
            userLabel.setText("Ospite");
        }
    }

    private void setFit(ImageView img, double width, double height) {
        img.setFitWidth(width);
        img.setFitHeight(height);

    }

    @FXML
    public void initialize() {
        setFit(first, 150,150);
        setFit(second, 150,150);
        setFit(third, 150,150);
        setFit(fourth, 150,150);
        setFit(fifth, 150,150);
        setFit(sixth, 150,150);
        setFit(seventh, 150,150);
        setFit(eighth, 150,150);
        setFit(ninth, 150,150);

        if (first != null) {
            first.setImage(new Image(new File("img/Audi/tires/tire1.jpg").toURI().toString()));
        }
        if (second != null) {
            second.setImage(new Image(new File("img/Audi/tires/tire2.jpg").toURI().toString()));
        }
        if (third != null) {
            third.setImage(new Image(new File("img/Audi/tires/tire3.jpg").toURI().toString()));
        }

        Platform.runLater(this::updateUserAccessStatus);
        Platform.runLater(this::centerContent);
        Platform.runLater(this::updateRiepilogo);
    }

    private void centerContent() {
        double width = rootPane.getWidth();
        double height = rootPane.getHeight();

        // Centering del titolo
        AnchorPane.setLeftAnchor(titleText, (width - titleText.getWidth()) / 2);
        // Centering solo verticale della descrizione dimensioni macchina
        AnchorPane.setTopAnchor(descGrid, (height - descGrid.getHeight()) / 2);
        // Centering verticale dell'ImageViewer
        AnchorPane.setTopAnchor(imageAnchor, (height - imageAnchor.getHeight()) / 2);
        // Centering pannello riepilogo
        AnchorPane.setLeftAnchor(pannelloRiepilogo, (width - pannelloRiepilogo.getWidth()) / 2);
        AnchorPane.setTopAnchor(pannelloRiepilogo, (height - pannelloRiepilogo.getHeight()) / 2);
        // Centering orizzontale freccie di navigazione
        AnchorPane.setLeftAnchor(navigationControls, (width - navigationControls.getHeight()) / 2);
        // Posizionamento descrizione motore
        AnchorPane.setTopAnchor(engineDesc, ((height - engineDesc.getHeight()) / 2) - (descGrid.getHeight() * 1.5));

    }

    private void updateRiepilogo() {
        if (!SessionManager.getInstance().getBackFlag()) {
            double carPrice = configCar.getPrice();
            partialPrice.setText(String.valueOf(carPrice));
            String riep = "";
            riep += "Colore: " + configCar.getColor() + " " + configCar.getColorPrice() + "0€\n";
            riep += "Motore: " + configCar.getEngine() + " " + configCar.getEngine().getPrice() + "0€\n";

            riepilogo.setText(riep);

            setCarImg(configCar.getImgPath(0));
        }
    }

    @FXML
    protected void setCarImg(String path) {
        Image img = new Image(new File(path).toURI().toString());
        System.out.println(new File(path).toURI().toString());
        carImg.setImage(img);
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void setUser(Utente user) {
        this.user = user;
    }

    public void setAuto(Auto auto) {
        this.configCar = auto;
    }

    @FXML
    protected void onExitButtonClick() {
        Platform.exit();
    }

    public void onGoBackAction() {
        // Segnala che stiamo ricaricando la pagina
        SessionManager.getInstance().setBackFlag(true);
        if (configCar != null) {
            SessionManager.getInstance().setConfiguredAuto(configCar);
        }
        mainController.loadFirstView();
    }

    public void onGoForwardAction() {

    }
}
