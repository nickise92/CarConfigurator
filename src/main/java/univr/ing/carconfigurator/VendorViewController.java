package univr.ing.carconfigurator;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

public class VendorViewController {

    private final String orderPath = "database/preventivi.csv";
    private ObservableList<String> orderList = FXCollections.observableArrayList();


    private MainController mainController;
    private Venditore vendor;

    @FXML private AnchorPane rootPane;
    @FXML private AnchorPane orderPane;
    @FXML private AnchorPane descriptionPane;
    @FXML private AnchorPane dimensionPane;
    @FXML private AnchorPane enginePane;
    @FXML private AnchorPane optionalPane;
    @FXML private AnchorPane portraitPane;
    @FXML private ChoiceBox<String> orderChoiceBox;
    @FXML private Button logoutButton;
    @FXML private Label vendorLogged;
    @FXML private Label shopLocation;
    @FXML private Label shortDesc;
    @FXML private Label clientName;
    @FXML private Label dimensionTitle;
    @FXML private Label orderLabel;
    @FXML private Label panelTitle;
    @FXML private Label lungLabel;
    @FXML private Label largLabel;
    @FXML private Label altLabel;
    @FXML private Label trunkLabel;
    @FXML private Label pesoLabel;
    @FXML private Label carLength;
    @FXML private Label carWidth;
    @FXML private Label carHeight;
    @FXML private Label carTrunkVolume;
    @FXML private Label carWeight;
    @FXML private Label engineTitle;
    @FXML private Label engineName;
    @FXML private Label fuelLabel;
    @FXML private Label speedLabel;
    @FXML private Label emissionLabel;
    @FXML private Label consumptionLabel;
    @FXML private Label displacementLabel;
    @FXML private Label engineFuel;
    @FXML private Label engineSpeed;
    @FXML private Label engineEmission;
    @FXML private Label engineConsumption;
    @FXML private Label engineDisplacement;
    @FXML private Label optionalTitle;
    @FXML private Label colorLabel;
    @FXML private Label tireLabel;
    @FXML private Label sensorLabel;
    @FXML private Label interiorLabel;
    @FXML private Label carColor;
    @FXML private Label carTire;
    @FXML private Label carSensor;
    @FXML private Label carInterior;
    @FXML private ImageView portraitCar;


    public VendorViewController() {

    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void setVendor(Venditore vendor) {
        this.vendor = vendor;
    }

    public void initialize() {

        Platform.runLater(this::updateVendorAccessStatus);
        Platform.runLater(this::centerContent);
        Platform.runLater(this::getOrderList);
    }

    private void updateVendorAccessStatus() {
        if (vendorLogged != null && vendor != null) {
            vendorLogged.setText(vendor.getUserName() + " " + vendor.getUserLastName() + " (" + vendor.getUserID() + ")");
            shopLocation.setText(vendor.getShop());
        }
    }

    private void centerContent() {
        double width = rootPane.getWidth();
        double height = rootPane.getHeight();

        // Centering logout button
        AnchorPane.setLeftAnchor(logoutButton, (width - logoutButton.getWidth()) / 2);
        // Posizionamento del pannello di selezione preventivi
        AnchorPane.setTopAnchor(orderPane, (height - orderPane.getHeight()) / 6);
        // Posizionamento del pannello di riepilogo
        AnchorPane.setTopAnchor(descriptionPane, (height - descriptionPane.getHeight()) / 3);
        AnchorPane.setRightAnchor(descriptionPane, (width - descriptionPane.getWidth()) / 2);
        // Titolo e sottotitolo del pannello di riepilogo
        AnchorPane.setLeftAnchor(panelTitle, (descriptionPane.getWidth() - panelTitle.getWidth()) / 2);
        AnchorPane.setLeftAnchor(shortDesc, (descriptionPane.getWidth() - shortDesc.getWidth()) / 4);
        AnchorPane.setLeftAnchor(clientName, (descriptionPane.getWidth() - clientName.getWidth()) / 4);
        // Posizionamento del pannello dimensioni all'interno del riepilogo
        //AnchorPane.setLeftAnchor(dimensionPane, (descriptionPane.getWidth() - dimensionPane.getWidth()) / 4);
        AnchorPane.setTopAnchor(dimensionPane, (descriptionPane.getHeight() - dimensionPane.getHeight()) / 3);
        // Posizionamento del pannello motore all'interno del riepilogo
        //AnchorPane.setRightAnchor(enginePane, (descriptionPane.getWidth() - enginePane.getWidth()) / 4);
        AnchorPane.setTopAnchor(enginePane, (descriptionPane.getHeight() - enginePane.getHeight()) / 3);
        // Posizionamento del pannello optional all'interno del riepilogo
        //AnchorPane.setLeftAnchor(optionalPane, (descriptionPane.getWidth() - optionalPane.getWidth()) / 2);
        AnchorPane.setBottomAnchor(optionalPane, (descriptionPane.getHeight()/2 - optionalPane.getHeight()) / 3);
        // Posizionamento dell'immagine nel riepilogo
        AnchorPane.setBottomAnchor(portraitPane, (descriptionPane.getHeight()/2 - optionalPane.getHeight()) / 3);
    }

    private void getOrderList() {

        try {
            Scanner sc = new Scanner(new File(orderPath));

            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] words = line.split(",");
                // Aggiungiamo alla lista solo i preventivi fatti entro 20 giorni dal giorno corrente.
                String date = words[0].split("=")[0]; // Escludiamo l'ora dalla data
                int year = Integer.valueOf(date.split("-")[0]);
                int month = Integer.valueOf(date.split("-")[1]);
                int day = Integer.valueOf(date.split("-")[2]);
                LocalDate orderDate = LocalDate.of(year, month, day);
                if (orderValidityCheck(orderDate)) {
                    // Se la data e' di piu' di 20 giorni prima del
                    // giorno corrente, ignoro la linea
                } else

                if (words.length > 2 && words[2].equals(vendor.getShop())) {
                    // Nella choice box mostriamo la data alla fine, serve per recuperare il corretto
                    // preventivo dal database
                    // Cliente marca ora
                    orderList.add(words[1] + " " + words[3] + " " + words[0].split("=")[0] + " (" +
                            words[0].split("=")[1] + ")"
                    );
                }
            }
            orderChoiceBox.setItems(orderList);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Quando viene selezionato un preventivo dal venditore vengono caricati tutti i
     * dati relativi alla configurazione e quelli dell'utente.
     * Se il preventivo e' stato presentato con una valutazione dell'usato per uno sconto
     * il tasto "Valuta usato" sara' attivo, altrimenti no.
     *
     * Inoltre, viene visualizzata la data di invio del preventivo.
     *
     * Tutti i preventivi non confermati e che hanno superato i 20 giorni non vengono
     * visualizzati
     */
    @FXML
    protected void onOrderSelection() {

        String time = orderChoiceBox.getValue().split(" \\(" )[1].split("\\)")[0];
        String date = orderChoiceBox.getValue().split(" ")[2];
        String dateString = date+"="+time;
        String client = orderChoiceBox.getValue().split(" ")[0];

        try {
            Scanner sc = new Scanner(new File(orderPath));

            while (sc.hasNextLine()) {
                String[] line = sc.nextLine().split(",");

                if (line[0].equals(dateString) && line[1].equals(client)) {
                    // Estrapola il nome del cliente
                    Cliente user = new Cliente(line[1]);
                    clientName.setText(user.getUserName() + " " + user.getUserLastName());

                    // popola i campi del riepilogo
                    Auto tmp = new Auto(line[3], line[4]);
                    tmp.setEngine(new Engine(line[6]));
                    tmp.setColor(line[5]);
                    tmp.setCircle(new Optional(line[7], OptTypes.CERCHI));
                    tmp.setInterior(new Optional(line[8], OptTypes.INTERNI));
                    tmp.setSensor(new Optional(line[9], OptTypes.SENSORI));

                    carLength.setText(String.valueOf(tmp.getLength()));
                    carWidth.setText(String.valueOf(tmp.getWidth()));
                    carHeight.setText(String.valueOf(tmp.getHeight()));
                    carTrunkVolume.setText(String.valueOf(tmp.getTrunkVol()));
                    carWeight.setText(String.valueOf(tmp.getWeight()));
                    shortDesc.setText(tmp.getBrand() + " " + tmp.getModel());

                    engineName.setText(tmp.getEngine().getName());
                    engineFuel.setText(tmp.getEngine().getFuelType());
                    engineSpeed.setText(tmp.getEngine().getAccelerationTime());
                    engineEmission.setText(tmp.getEngine().getGramsCO2perKm());
                    engineConsumption.setText(tmp.getEngine().getConsumption());
                    engineDisplacement.setText(tmp.getEngine().getDisplacement());

                    carColor.setText(tmp.getColor());
                    carTire.setText(tmp.getCircle().toString());
                    carSensor.setText(tmp.getSensor().toString());
                    carInterior.setText(tmp.getSensor().toString());

                    // Setting the car image
                    portraitCar.setImage(new Image( new File(tmp.getImgPath(0)).toURI().toString()));

                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Funzione di verifica se sono trascorsi 20 giorni dalla generazione del
    // preventivo
    private boolean orderValidityCheck(LocalDate orderDate){
        LocalDate currentDate = LocalDate.now();
        long daysPassed =  ChronoUnit.DAYS.between(orderDate, currentDate);
        if (daysPassed == 20) {

        }

        return daysPassed > 20;
    }

    @FXML
    protected void onLogoutButton() {
        SessionManager.getInstance().clearSession();
        mainController.loadHomePage();
    }

    @FXML
    protected void onExitButton() {
        Platform.exit();
    }
}
