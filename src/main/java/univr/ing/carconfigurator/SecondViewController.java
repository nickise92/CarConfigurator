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


    private final String tirePath = "database/tire.csv";
    private final String interiorPath = "database/interior.csv";
    private final String sensorPath = "database/sensor.csv";


    private ObservableList<String> tireList = FXCollections.observableArrayList();
    private ObservableList<String> interiorList = FXCollections.observableArrayList();
    private ObservableList<String> sensorList = FXCollections.observableArrayList();


    private MainController mainController;
    private Utente user;
    private Auto configCar;
    private Optional cerchi;
    private Optional interior;
    private Optional sensor;

    @FXML private Label userLogged;
    @FXML private Label userLabel;
    @FXML private Label titleText;
    @FXML private GridPane navigationControls;
    @FXML private AnchorPane rootPane;
    @FXML private AnchorPane imageAnchor;
    @FXML private AnchorPane pannelloRiepilogo;
    @FXML private ImageView carImg;
    @FXML private Label partialPrice;
    @FXML private TextArea riepilogo;
    @FXML private Button exitButton;
    @FXML private ChoiceBox carTireChoice;
    @FXML private ChoiceBox carInteriorChoice;
    @FXML private ChoiceBox carSensorChoice;

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


    @FXML
    public void initialize() {
        setAuto(SessionManager.getInstance().getConfiguredAuto());
        /* Inserimento delle scelte di cerchi per l'auto corrente */
        try {
            Scanner sc = new Scanner(new File(tirePath));
            tireList.clear();
            while (sc.hasNextLine()) {
                String[] line = sc.nextLine().split(",");
                if (line[0].equals(configCar.getBrand())) {
                    tireList.add(line[1]);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        carTireChoice.setItems(tireList);

        /* Inserimento interni per l'auto corrente */
        try {
            Scanner sc = new Scanner(new File(interiorPath));
            interiorList.clear();
            while(sc.hasNextLine()) {
                String[] line = sc.nextLine().split(",");
                if (line[0].equals(configCar.getBrand()) && !line[1].equals("")) {
                    interiorList.add(line[1]);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        carInteriorChoice.setItems(interiorList);

        /* Popolamento lista sensori */
        try {
            Scanner sc = new Scanner(new File(sensorPath));
            sensorList.clear();
            while(sc.hasNextLine()) {
                String[] line = sc.nextLine().split(",");
                if (line[0].equals(configCar.getBrand()) && !line[1].equals("")) {
                    sensorList.add(line[1]);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        carSensorChoice.setItems(sensorList);

        Platform.runLater(this::updateUserAccessStatus);
        Platform.runLater(this::centerContent);
        Platform.runLater(this::updateRiepilogo);
    }

    private void centerContent() {
        double width = rootPane.getWidth();
        double height = rootPane.getHeight();

        // Centering del titolo
        AnchorPane.setLeftAnchor(titleText, (width - titleText.getWidth()) / 2);
        // Centering verticale dell'ImageViewer
        AnchorPane.setTopAnchor(imageAnchor, (height - imageAnchor.getHeight()) / 2);
        // Centering pannello riepilogo
        AnchorPane.setLeftAnchor(pannelloRiepilogo, (width - pannelloRiepilogo.getWidth()) / 2);
        AnchorPane.setTopAnchor(pannelloRiepilogo, (height - pannelloRiepilogo.getHeight()) / 2);
        // Centering orizzontale frecce di navigazione
        AnchorPane.setLeftAnchor(navigationControls, (width - navigationControls.getWidth()) / 2);

    }

    private void updateRiepilogo() {
        //TODO: popolare i campi delle choicebox con la configurazione selezionata se presente

        double carPrice = configCar.getPrice();

        String riep = "";
        riep += "Colore: " + configCar.getColor() + " " + configCar.getColorPrice() + "0€\n";
        riep += "Motore: " + configCar.getEngine() + " " + configCar.getEngine().getPrice() + "0€\n";
        if (cerchi != null) {
            riep += "Cerchi: " + cerchi.getName() + " " + cerchi.getPrice() + "0€\n";
            carPrice += cerchi.getPrice();
            configCar.setCircle(cerchi);
        }
        if (interior != null) {
            riep += "Interni: " + interior.getName() + " " + interior.getPrice() + "0€\n";
            carPrice += interior.getPrice();
            configCar.setInterior(interior);
        }
        if (sensor != null) {
            riep += "Sensori: " + sensor.getName() + " " + sensor.getPrice() + "0€\n";
            carPrice += sensor.getPrice();
            configCar.setSensor(sensor);
        }

        partialPrice.setText(String.valueOf(carPrice));
        configCar.setPrice(carPrice);
        riepilogo.setText(riep);

        setCarImg(configCar.getImgPath(0));

    }

    @FXML
    protected void setCarImg(String path) {
        Image img = new Image(new File(path).toURI().toString());
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
    protected void onTireSelection() {
        cerchi = new Optional((String) carTireChoice.getValue(), OptTypes.CERCHI);
        updateRiepilogo();
    }

    @FXML
    protected void onAllestSelection() {
        interior = new Optional((String) carInteriorChoice.getValue(), OptTypes.INTERNI);
        updateRiepilogo();
    }

    @FXML
    protected void onSensorSelection() {
        sensor = new Optional((String) carSensorChoice.getValue(), OptTypes.SENSORI);
        updateRiepilogo();

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
        if (mainController.showBackAlert()) {
            mainController.loadFirstView();
        }
    }

    public void onGoForwardAction() {
        if (configCar != null) {
            SessionManager.getInstance().setConfiguredAuto(configCar);
        }
        mainController.loadThirdView();
    }


}
