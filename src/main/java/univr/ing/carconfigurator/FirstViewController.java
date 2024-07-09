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

public class FirstViewController {

    private final String carsPath = "database/car.csv";
    private final String enginePath = "database/engine.csv";

    public Label acceleration;
    public Label engineFuel;
    public Label engineName;
    public Label engineEmission;
    public Label engineConsumption;
    public Label engineDisplacement;
    public GridPane engineDesc;
    public ChoiceBox carIntern;
    public ChoiceBox carTireChoice;

    private Auto configCar;
    private double carPrice;
    private double enginePrice;
    private double colorPrice;
    private double optionalPrice;
    private Utente user;


    private ObservableList<String> brandList = FXCollections.observableArrayList();
    private ObservableList<String> colorsList = FXCollections.observableArrayList(
            "Bianco", "Rosso", "Blu", "Nero", "Grigio"
    );

    @FXML private AnchorPane rootPane;
    @FXML private AnchorPane imageAnchor;
    @FXML private AnchorPane pannelloRiepilogo;
    @FXML private GridPane descGrid;
    @FXML private GridPane navigationControls;
    @FXML private Button loginButton;
    @FXML private Button exitButton;
    @FXML private Button nextButton;
    @FXML private Label userLogged;
    @FXML private Label userLabel;
    @FXML private Label titleText;
    @FXML private Button leftImgArrow;
    @FXML private Button rightImgArrow;
    @FXML private Button registerButton;
    @FXML private Button goBackButton;
    @FXML private Button goForwardButton;
    @FXML private ImageView carImg = new ImageView();
    @FXML private ChoiceBox<String> carBrandChoice;
    @FXML private ChoiceBox<String> carModelChoice;


    @FXML private Label carHeight;
    @FXML private Label carWidth;
    @FXML private Label carLength;
    @FXML private Label trunkVol;
    @FXML private Label carWeight;
    @FXML private Label partialPrice;

    @FXML private ChoiceBox<String> carEngineChoice;
    @FXML private ChoiceBox<String> carColorChoice;

    @FXML private TextArea riepilogo;


    private MainController mainController;

    public FirstViewController() {

    }

    public void setUser(Utente user) {
        this.user = user;
    }

    public void setAuto(Auto auto) {
        this.configCar = auto;
    }


    public void setMainController(MainController mainController) {
        this.mainController = mainController;
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

    private void centerContent() {
        double width = rootPane.getWidth();
        double height = rootPane.getHeight();

        // Centering del titolo
        AnchorPane.setLeftAnchor(titleText, (width - titleText.getWidth()) / 2);
        // Centering solo verticale della descrizione dimensioni macchina
        AnchorPane.setTopAnchor(descGrid, 230.0);
        // Centering verticale dell'ImageViewer
        AnchorPane.setTopAnchor(imageAnchor, (height - imageAnchor.getHeight()) / 2);
        // Centering pannello riepilogo
        AnchorPane.setLeftAnchor(pannelloRiepilogo, (width - pannelloRiepilogo.getWidth()) / 2);
        AnchorPane.setTopAnchor(pannelloRiepilogo, (200.0));
        // Centering orizzontale freccie di navigazione
        AnchorPane.setLeftAnchor(navigationControls, (width - navigationControls.getHeight()) / 2);
        // Posizionamento descrizione motore
        AnchorPane.setBottomAnchor(engineDesc, ((height - engineDesc.getHeight() - 75.0) / 3));
    }

    @FXML
    public void initialize() {
        // Listener per gestire il ridimensionamento
        rootPane.widthProperty().addListener((obs, oldVal, newVal) -> centerContent());
        rootPane.heightProperty().addListener((obs, oldVal, newVal) -> centerContent());

        // in questa pagina goBackButton e' disabilitato
        goBackButton.setDisable(true);
        goForwardButton.setDisable(true);

        try {
            Scanner sc = new Scanner(new File("database/brand.csv"));
            for (String car : sc.nextLine().split(",")) {
                if (!car.equals("")) {
                    brandList.add(car);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Impossibile inizializzare le marche di auto.");
        }

        carBrandChoice.setItems(brandList);
        carColorChoice.setItems(colorsList);
        Platform.runLater(this::updateUserAccessStatus);
        Platform.runLater(this::centerContent);
        Platform.runLater(this::retriveCarInfo);
    }

    @FXML
    protected void onBrandSelection() {
        String brand = (String) carBrandChoice.getValue();

        ObservableList<String> modelList = FXCollections.observableArrayList();
        File db = new File(carsPath);
        try {
            Scanner sc = new Scanner(db);
            while(sc.hasNextLine()) {
                String[] tmp = sc.nextLine().split(",");
                if (brand.equals(tmp[0])) {
                    modelList.add(tmp[1]);
                }
            }
            carModelChoice.setItems(modelList);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        ObservableList<String> engineList = FXCollections.observableArrayList();
        try {
            Scanner sc = new Scanner(new File(enginePath));
            engineList.clear();
            while (sc.hasNextLine()) {
                String[] line = sc.nextLine().split(",");
                if (line[0].equals(carBrandChoice.getValue()) && !line[1].equals("")) {
                    engineList.add(line[1]);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        carEngineChoice.setItems(engineList);
    }

    @FXML
    protected void onModelSelection() {
        if (!SessionManager.getInstance().getBackFlag()) {
            configCar = new Auto(carBrandChoice.getValue(), carModelChoice.getValue());
        }

        carPrice = configCar.getPrice();
        enginePrice = configCar.getDefaultEngine().getPrice();
        colorPrice = 0;

        setCarImg(configCar.getImgPath(0));

        carHeight.setText(String.valueOf(configCar.getHeight()));
        carWidth.setText(String.valueOf(configCar.getWidth()));
        carLength.setText(String.valueOf(configCar.getLength()));
        trunkVol.setText(String.valueOf(configCar.getTrunkVol()));
        partialPrice.setText(String.valueOf(carPrice));
        carWeight.setText(String.valueOf(configCar.getWeight()));
        if (!SessionManager.getInstance().getBackFlag()) {
            carColorChoice.setValue(configCar.getDefaultColor());
            carEngineChoice.setValue(configCar.getEngine().toString());
        }

    }

    @FXML
    protected void setCarImg(String path) {
        Image img = new Image(new File(path).toURI().toString());
        carImg.setImage(img);
    }

    @FXML
    protected void onEngineSelection() {
        // Recuperiamo il nome del motore che vogliamo inserire nella vettura
        // e creiamo l'oggetto Engine corrispondente
        Engine engine = new Engine(carEngineChoice.getValue());
        configCar.setEngine(engine);
        enginePrice = engine.getPrice();

        engineName.setText(engine.getName());
        engineFuel.setText(engine.getFuelType());
        acceleration.setText(engine.getAccelerationTime());
        engineEmission.setText(engine.getGramsCO2perKm());
        engineConsumption.setText(engine.getConsumption());
        engineDisplacement.setText(engine.getDisplacement());

        updateRiepilogo();
    }

    // Questo metodo gestisce la scelta del colore delle automobili
    @FXML
    protected void onColorSelection() {
        String color = carColorChoice.getValue();
        configCar.setColor(color);
        colorPrice = configCar.getColorPrice();
        updateRiepilogo();
        goForwardButton.setDisable(false);
    }

    private void updateRiepilogo() {
        String riep = "";

        carPrice = colorPrice + enginePrice + optionalPrice;
        configCar.setPrice(carPrice);
        partialPrice.setText(String.valueOf(carPrice));

        if (carColorChoice.getValue() != null) {
            riep += "Colore: " + carColorChoice.getValue() + " " + colorPrice + "0€\n";
        }

        if (carEngineChoice.getValue() != null) {
            riep += "Motore: " + carEngineChoice.getValue() + " " + enginePrice + "0€\n";
        }

        riepilogo.setText(riep);
    }

    private void retriveCarInfo() {
        if (SessionManager.getInstance().getBackFlag()) {

            configCar = SessionManager.getInstance().getConfiguredAuto();
            carPrice = configCar.getPrice();
            partialPrice.setText(String.valueOf(carPrice));
            String riep = "";
            riep += "Colore: " + configCar.getColor() + " " + configCar.getColorPrice() + "0€\n";
            riep += "Motore: " + configCar.getEngine() + " " + configCar.getEngine().getPrice() + "0€\n";

            riepilogo.setText(riep);

            carBrandChoice.setValue(configCar.getBrand());
            carModelChoice.setValue(configCar.getModel());
            carEngineChoice.setValue(configCar.getEngine().toString());
            carColorChoice.setValue(configCar.getColor());
        }
    }

    @FXML
    protected void onGoBackAction() {

    }

    @FXML
    protected void onGoForwardAction() {
        if (configCar != null) {
            SessionManager.getInstance().setConfiguredAuto(configCar);
        }
        mainController.loadSecondView();
    }

    @FXML
    protected void onExitButtonClick() {
        Platform.exit();
    }

    public void onTireSelection(ActionEvent actionEvent) {
        System.out.println("Selezionati i cerchi: " + carTireChoice.getValue());
    }

    public void onAllestSelection(ActionEvent actionEvent) {
        System.out.println("Selezionati gli interni: " + carIntern.getValue());
    }
}
