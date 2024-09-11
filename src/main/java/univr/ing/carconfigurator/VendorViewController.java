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
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class VendorViewController {

    private final String quotationPath = "database/preventivi.csv";
    private ObservableList<Preventivo> quotationList = FXCollections.observableArrayList();
    private ObservableList<Ordine> orderList = FXCollections.observableArrayList();
    private final String orderPath = "database/ordini.csv";

    private MainController mainController;
    private Venditore vendor;

    private Valutazione valutazione;

    @FXML private AnchorPane rootPane;
    @FXML private AnchorPane orderPane;
    @FXML private AnchorPane descriptionPane;
    @FXML private AnchorPane dimensionPane;
    @FXML private AnchorPane enginePane;
    @FXML private AnchorPane optionalPane;
    @FXML private AnchorPane portraitPane;

    @FXML private ChoiceBox<Preventivo> quotationChoiceBox;
    @FXML private ChoiceBox<Ordine> orderChoiceBox;

    @FXML private Button logoutButton;
    @FXML private Button exitButton;
    @FXML private Button notifyReadyCar;
    @FXML private Button oldCarEvaluation;
    @FXML private Button confirmEvaluated;

    @FXML private Label vendorLogged;
    @FXML private Label shopLocation;
    @FXML private Label carName;
    @FXML private Label clientName;
    @FXML private Label carPrice;
    @FXML private Label discountValue;

    @FXML private Label dimensionTitle;
    @FXML private Label quotationLabel;
    @FXML private Label orderLabel;
    @FXML private Label deliveryDateLabel;
    @FXML private Label panelTitle;
    @FXML private Label carLength;
    @FXML private Label carWidth;
    @FXML private Label carHeight;
    @FXML private Label carTrunkVolume;
    @FXML private Label carWeight;
    @FXML private Label engineName;
    @FXML private Label engineFuel;
    @FXML private Label engineSpeed;
    @FXML private Label engineEmission;
    @FXML private Label engineConsumption;
    @FXML private Label engineDisplacement;
    @FXML private Label enginePower;
    @FXML private Label carColor;
    @FXML private Label carTire;
    @FXML private Label carSensor;
    @FXML private Label carInterior;
    @FXML private ImageView portraitCar;


    NumberFormat formatter = new DecimalFormat("#0.00");

    public VendorViewController() {

    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void setVendor(Venditore vendor) {
        this.vendor = vendor;
    }

    public void initialize() {

        notifyReadyCar.setDisable(true);
        oldCarEvaluation.setDisable(true);
        confirmEvaluated.setDisable(true);
        Platform.runLater(this::updateVendorAccessStatus);
        Platform.runLater(this::centerContent);
        Platform.runLater(this::getQuotationList);
        Platform.runLater(this::getOrderList);
        Platform.runLater(this::restoreQuotation);
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

        // Posizionamento del pannello di selezione preventivi
        AnchorPane.setTopAnchor(orderPane, (height - orderPane.getHeight()) / 6);
        // Posizionamento del pannello di riepilogo
        AnchorPane.setTopAnchor(descriptionPane, (height - descriptionPane.getHeight()) / 3);
        AnchorPane.setRightAnchor(descriptionPane, (width - descriptionPane.getWidth()) / 2);
        // Titolo e sottotitolo del pannello di riepilogo
        AnchorPane.setLeftAnchor(panelTitle, (descriptionPane.getWidth() - panelTitle.getWidth()) / 2);
        AnchorPane.setLeftAnchor(carName, (descriptionPane.getWidth() - carName.getWidth()) / 4);
        AnchorPane.setLeftAnchor(clientName, (descriptionPane.getWidth() - clientName.getWidth()) / 4);
        AnchorPane.setLeftAnchor(carPrice, (descriptionPane.getWidth() - carPrice.getWidth()) / 4);
        AnchorPane.setLeftAnchor(discountValue, (descriptionPane.getWidth() - discountValue.getWidth()) / 4);
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
        // Posizionamento dei bottoni
        AnchorPane.setLeftAnchor(notifyReadyCar, (descriptionPane.getWidth() - notifyReadyCar.getWidth() - oldCarEvaluation.getWidth() - 15) / 2);
        AnchorPane.setRightAnchor(oldCarEvaluation, (descriptionPane.getWidth() - oldCarEvaluation.getWidth() - notifyReadyCar.getWidth() - 15) / 2);
        AnchorPane.setLeftAnchor(confirmEvaluated, (descriptionPane.getWidth() - confirmEvaluated.getWidth()) / 2);

    }


    // Crea la lista di preventivi utente con una richiesta di valutazione dell'usato
    // assegnata
    private void getQuotationList() {

        try {
            Scanner sc = new Scanner(new File(quotationPath));
            while (sc.hasNextLine()) {
                Preventivo quotation = new Preventivo(sc.nextLine());
                // Aggiungiamo alla lista solo i preventivi fatti entro 20 giorni dal giorno corrente e
                // appartenenti al negozio del venditore loggato
                if (Preventivo.checkQuotationValidity(quotation) &&
                        quotation.getShopLocation().equals(vendor.getShop()) &&
                        quotation.getOldCarDiscount()) {
                    quotationList.add(quotation);
                }
            }
            quotationChoiceBox.setItems(quotationList);
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Crea la lista degli ordini in attesa di notifica al cliente
    private void getOrderList() {

        deliveryDateLabel.setText("");

        try {
            Scanner sc = new Scanner(new File(orderPath));
            while (sc.hasNextLine()) {
                Ordine order = new Ordine(sc.nextLine());
                // Se l'ordine aperto e' del negozio corrente, viene aggiunto alla lista
                if (order.getShopLocation().equals(vendor.getShop())) {
                    orderList.add(order);
                }
            }
            orderChoiceBox.setItems(orderList);
            sc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void restoreQuotation() {
        if (SessionManager.getInstance().getOpenQuotation() != null) {
            quotationChoiceBox.setValue(SessionManager.getInstance().getOpenQuotation());
            valutazione = SessionManager.getInstance().getValutazione();
            // Se la valutazione e' stata effettuata disabilito il pulsante e abilito quello di conferma
            oldCarEvaluation.setDisable(SessionManager.getInstance().getOldCarEvaluated());
            confirmEvaluated.setDisable(!SessionManager.getInstance().getOldCarEvaluated());
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
    protected void onQuotationSelection() {

        Preventivo quotation = quotationChoiceBox.getValue();
        SessionManager.getInstance().setOpenQuotation(quotation);

        // Estrapola il nome del cliente
        Cliente user = new Cliente(quotation.getUserID());
        clientName.setText("Cliente: " + user.getUserName() + " " + user.getUserLastName());

        // popola i campi del riepilogo

        // DETTAGLI TECNICI AUTO
        carLength.setText("Lunghezza: " + String.valueOf(quotation.getConfiguredCar().getLength()));
        carWidth.setText("Larghezza: " + String.valueOf(quotation.getConfiguredCar().getWidth()));
        carHeight.setText("Altezza: " + String.valueOf(quotation.getConfiguredCar().getHeight()));
        carTrunkVolume.setText("Volume bagagliaio: " + String.valueOf(quotation.getConfiguredCar().getTrunkVol()));
        carWeight.setText("Peso: " + String.valueOf(quotation.getConfiguredCar().getWeight()));
        carName.setText("Automobile: " + quotation.getConfiguredCar().getBrand() + " " + quotation.getConfiguredCar().getModel());
        carPrice.setText("Prezzo: " + formatter.format(quotation.getConfiguredCar().getPrice()) + "€");
        if (quotation.getOldCarValue() > 0.0) {
            discountValue.setText("Sconto: " + formatter.format(quotation.getOldCarValue()));
        }



        // DETTAGLI TECNICI MOTORE
        engineName.setText("Motore: " + quotation.getConfiguredCar().getEngine().getName());
        engineFuel.setText("Alimentazione: " + quotation.getConfiguredCar().getEngine().getFuelType());
        engineSpeed.setText("0-100km/h: " + quotation.getConfiguredCar().getEngine().getAccelerationTime());
        engineEmission.setText("Emissioni: " + quotation.getConfiguredCar().getEngine().getGramsCO2perKm());
        engineConsumption.setText("Consumi: " + quotation.getConfiguredCar().getEngine().getConsumption());
        engineDisplacement.setText("Cilindrata: " + quotation.getConfiguredCar().getEngine().getDisplacement());
        enginePower.setText("Potenza: " + quotation.getConfiguredCar().getEngine().getPower());

        // DETTAGLI OPTIONAL
        carColor.setText("Colore: " + quotation.getConfiguredCar().getColor());
        carTire.setText("Cerchi: " + quotation.getConfiguredCar().getCircle().toString());
        carSensor.setText("Sensori: " + quotation.getConfiguredCar().getSensor().toString());
        carInterior.setText("Interni: "+ quotation.getConfiguredCar().getInterior().toString());

        // Setting the car image
        portraitCar.setImage(new Image( new File(quotation.getConfiguredCar().getImgPath(0)).toURI().toString()));

        // disabilita il tasto conferma ordine, in quanto si tratta di un preventivo
        notifyReadyCar.setDisable(quotation.getOldCarDiscount());
        // e abilita il tasto di valutazione dell'usato.
        oldCarEvaluation.setDisable(!quotation.getOldCarDiscount());

    }

    /**
     * Quando viene selezionato un ordine dal venditore vengono caricati tutti i
     * dati relativi alla configurazione e quelli dell'utente corrispondente.
     * Se l'ordine e' pronto viene visualizzata la data IN VERDE e il pulsante
     * "Conferma ritiro" sara' attivo.
     */
    @FXML
    protected void onOrderSelection() {

        Ordine order = orderChoiceBox.getValue();
        if (order.getDeliveryDate().isAfter(LocalDate.now())) {
            deliveryDateLabel.setStyle("-fx-text-fill: #990000");
            notifyReadyCar.setDisable(true);
        } else {
            deliveryDateLabel.setStyle("-fx-text-fill: #009900");
            notifyReadyCar.setDisable(false);
        }
        deliveryDateLabel.setText("Consegna prevista: " + order.getDeliveryDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

        // Estrapola il nome del cliente
        Cliente user = new Cliente(order.getUserID());
        clientName.setText("Cliente: " + user.getUserName() + " " + user.getUserLastName());

        // popola i campi del riepilogo

        // DETTAGLI TECNICI AUTO
        carLength.setText("Lunghezza: " + String.valueOf(order.getConfiguredCar().getLength()));
        carWidth.setText("Larghezza: " + String.valueOf(order.getConfiguredCar().getWidth()));
        carHeight.setText("Altezza: " + String.valueOf(order.getConfiguredCar().getHeight()));
        carTrunkVolume.setText("Volume bagagliaio: " + String.valueOf(order.getConfiguredCar().getTrunkVol()));
        carWeight.setText("Peso: " + String.valueOf(order.getConfiguredCar().getWeight()));
        carName.setText("Automobile: " + order.getConfiguredCar().getBrand() + " " + order.getConfiguredCar().getModel());
        carPrice.setText("Prezzo: " + String.format("%.2f", order.getConfiguredCar().getPrice()));
        if (order.getOldCarValue() > 0.0) {
            discountValue.setText("Sconto: " + formatter.format(order.getOldCarValue()));
        }

        // DETTAGLI TECNICI MOTORE
        engineName.setText("Motore: " + order.getConfiguredCar().getEngine().getName());
        engineFuel.setText("Alimentazione: " + order.getConfiguredCar().getEngine().getFuelType());
        engineSpeed.setText("0-100km/h: " + order.getConfiguredCar().getEngine().getAccelerationTime());
        engineEmission.setText("Emissioni: " + order.getConfiguredCar().getEngine().getGramsCO2perKm());
        engineConsumption.setText("Consumi: " + order.getConfiguredCar().getEngine().getConsumption());
        engineDisplacement.setText("Cilindrata: " + order.getConfiguredCar().getEngine().getDisplacement());
        enginePower.setText("Potenza: " + order.getConfiguredCar().getEngine().getPower());

        // DETTAGLI OPTIONAL
        carColor.setText("Colore: " + order.getConfiguredCar().getColor());
        carTire.setText("Cerchi: " + order.getConfiguredCar().getCircle().toString());
        carSensor.setText("Sensori: " + order.getConfiguredCar().getSensor().toString());
        carInterior.setText("Interni: "+ order.getConfiguredCar().getInterior().toString());

        // Setting the car image
        portraitCar.setImage(new Image( new File(order.getConfiguredCar().getImgPath(0)).toURI().toString()));

    }

    // Conferma & Notifica
    @FXML
    protected void onOrderConfirmation() {
        // Notify customer his car is ready and move the order
        // to new database entry "ritiri.csv"
        Ordine order = orderChoiceBox.getValue();
        mainController.showConfirmationAlert("Notifica di ritiro al cliente.",
                "Proseguendo, l'ordine selezionato verrà notificato come pronto per il ritiro al cliente.",
                "Proseguire?");
        order.moveToReady();
    }

    // Visualizza l'auto usata da valutare e aggiorna il prezzo con lo sconto
    @FXML
    protected void onOldCarEvaluation() {
        SessionManager.getInstance().setAuthenticatedVendor(vendor);
        SessionManager.getInstance().setOpenQuotation(quotationChoiceBox.getValue());
        mainController.loadCarEvaluationView();
    }

    @FXML
    protected void onConfirmEvaluated() {
        valutazione.save();
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
