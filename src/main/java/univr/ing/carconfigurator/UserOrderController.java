package univr.ing.carconfigurator;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class UserOrderController {

    private MainController mainController;
    private Utente user;
    private final String preventiviPath = "database/preventivi.csv";
    private final String oriniPath = "database/ordini.csv";
    @FXML private Button logoutButton;
    @FXML private Button exitButton;
    @FXML private Button confirmAndPay;
    @FXML private Button goBackButton;

    @FXML private AnchorPane rootPane;
    @FXML private AnchorPane orderPane;
    @FXML private AnchorPane descriptionPane;
    @FXML private AnchorPane dimensionPane;
    @FXML private AnchorPane enginePane;
    @FXML private AnchorPane optionalPane;
    @FXML private AnchorPane portraitPane;
    @FXML private AnchorPane carAndShopPane;

    @FXML private Label panelTitle;
    @FXML private Label carLength;
    @FXML private Label carWidth;
    @FXML private Label carHeight;
    @FXML private Label carTrunkVolume;
    @FXML private Label carWeight;
    @FXML private Label carName;
    @FXML private Label shopLocation;;
    @FXML private Label engineName;
    @FXML private Label engineFuel;
    @FXML private Label engineSpeed;
    @FXML private Label engineEmission;
    @FXML private Label engineConsumption;
    @FXML private Label engineDisplacement;
    @FXML private Label enginePower;
    @FXML private Label optionalTitle;
    @FXML private Label carColor;
    @FXML private Label carTire;
    @FXML private Label carSensor;
    @FXML private Label carInterior;

    @FXML private ImageView portraitCar;

    public void userOrderController() {

    }

    public void setUser(Utente user) {
        this.user = user;
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void initialize() {

        Platform.runLater(this::centerContent);
        Platform.runLater(this::getPreventivoData);
    }

    private void centerContent() {
        double width = rootPane.getWidth();
        double height = rootPane.getHeight();

        // Logout on the right
        AnchorPane.setRightAnchor(logoutButton, (width / 3 - logoutButton.getWidth()) / 2);
        AnchorPane.setBottomAnchor(logoutButton, 25.0);
        // GoBack on the left
        AnchorPane.setLeftAnchor(goBackButton, (width/3 - goBackButton.getWidth()) / 2);
        AnchorPane.setBottomAnchor(goBackButton, 25.0);
        // ExitButton
        AnchorPane.setRightAnchor(exitButton, 25.0);
        AnchorPane.setBottomAnchor(exitButton, 25.0);
        // Titolo
        AnchorPane.setTopAnchor(panelTitle, 25.0);
        AnchorPane.setLeftAnchor(panelTitle, (width - panelTitle.getWidth()) / 2);
        // Pannello di riepilogo
        AnchorPane.setTopAnchor(descriptionPane, 25.0 + ((height - descriptionPane.getHeight()) / 2));
        AnchorPane.setRightAnchor(descriptionPane, (width - descriptionPane.getWidth()) / 2);
        // Titolo e sottotitolo del pannello di riepilogo
        AnchorPane.setLeftAnchor(panelTitle, (width - panelTitle.getWidth()) / 2);
        AnchorPane.setLeftAnchor(carAndShopPane, (width - carAndShopPane.getWidth()) / 2);
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
        // Conferma e paga
        AnchorPane.setLeftAnchor(confirmAndPay, (width - confirmAndPay.getWidth()) / 2);
        AnchorPane.setBottomAnchor(confirmAndPay, 25.0);
    }

    private void getPreventivoData() {
        // Recupero il preventivo scelto
        Preventivo preventivo = SessionManager.getInstance().getOpenOrder();

        // Popolo i campi del riepilogo
        carName.setText("Automobile: " + preventivo.getConfiguredCar().getName());
        shopLocation.setText("Sede: " + preventivo.getShopLocation());

        // Dimension panel
        carLength.setText("Lunghezza: " + String.valueOf(preventivo.getConfiguredCar().getLength()));
        carWidth.setText("Larghezza: " + String.valueOf(preventivo.getConfiguredCar().getWidth()));
        carHeight.setText("Altezza: " + String.valueOf(preventivo.getConfiguredCar().getHeight()));
        carWeight.setText("Peso: " + String.valueOf(preventivo.getConfiguredCar().getWeight()));
        carTrunkVolume.setText("Volume bagagliaio: " + String.valueOf(preventivo.getConfiguredCar().getTrunkVol()));

        // Optional panel
        carColor.setText("Colore: " + preventivo.getConfiguredCar().getColor());

        if (preventivo.getConfiguredCar().getCircle() != null ||
                !preventivo.getConfiguredCar().getCircle().toString().equals("null")) {
            carTire.setText("Cerchi: " + preventivo.getConfiguredCar().getCircle().toString());
        } else {
            carTire.setText("Cerchi di serie");
        }
        if (preventivo.getConfiguredCar().getSensor() != null ||
                !preventivo.getConfiguredCar().getSensor().toString().equals("null")) {
            carSensor.setText("Sensori: " + preventivo.getConfiguredCar().getSensor().toString());
        } else {
            carSensor.setText("Nessuno sensore selezionato");
        }
        if (preventivo.getConfiguredCar().getInterior() != null ||
                !preventivo.getConfiguredCar().getInterior().toString().equals("null")) {
            carInterior.setText("Interni: " + preventivo.getConfiguredCar().getInterior().toString());
        } else {
            carInterior.setText("Interni di serie");
        }

        // Engine panel
        engineName.setText("Motore: " + preventivo.getConfiguredCar().getEngine().getName());
        engineFuel.setText("Alimentazione: " + preventivo.getConfiguredCar().getEngine().getFuelType());
        engineSpeed.setText("0-100 km/h: " + preventivo.getConfiguredCar().getEngine().getAccelerationTime());
        engineEmission.setText("Emissioni: " + preventivo.getConfiguredCar().getEngine().getGramsCO2perKm());
        engineConsumption.setText("Consumi: " + preventivo.getConfiguredCar().getEngine().getConsumption());
        engineDisplacement.setText("Cilindrata: " + preventivo.getConfiguredCar().getEngine().getDisplacement());
        enginePower.setText("Potenza: " + preventivo.getConfiguredCar().getEngine().getPower());


        // Se il preventivo ha una richiesta di valutazione correlata
        // il tasto conferma e paga e' disabilitato, in quanto e' necessario
        // l'intervento di un impiegato del negozio per la valutazione.
        confirmAndPay.setDisable(preventivo.isOldCarDiscount());
    }


    /**
     * Questo metodo copia il preventivo correntemente aperto e lo copia nel
     * database degli ordini confermati (dal cliente) e lo rimuove dalla lista
     * dei preventivi.
     * Alla conferma il sistema genera una possibile data di consegna: 30 giorni + 10 per
     * ogni optional selezionato.
     *
     */
    @FXML
    protected void onConfirmAndPay() {
        try {
            // Apro il file dei preventivi in lettura
            Scanner quotationReader = new Scanner(new File(preventiviPath));
            // Apro il file ordini in scrittura
            FileWriter orderWriter = new FileWriter(oriniPath, true);
            // Stringa di salvataggio linee del preventivo da mantenere
            String quotationToPreserve = "";

            while(quotationReader.hasNextLine()) {
                String line = quotationReader.nextLine();
                String quotationID = line.split(",")[0];

                if (quotationID.equals(SessionManager.getInstance().getOpenOrder().getOrderID())) {
                    System.out.println("Linea trovata: \n" + line);
                    orderWriter.append("\n" + line);
                } else {
                    quotationToPreserve += line + "\n";
                }
            }
            // Chiudo lo Scanner e il Writer
            quotationReader.close();
            orderWriter.close();

            // Elimino dal file preventivi.csv la riga corrispondente all'ordine
            // confermato dal cliente
            quotationUpdate(quotationToPreserve);

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Calcolo della data di consegna dell'auto
        // 30gg + 10 per ogni optional
        LocalDate current = LocalDate.now();
        int dayToAdd = 30;
        Auto orderedCar = SessionManager.getInstance().getOpenOrder().getConfiguredCar();
        dayToAdd += orderedCar.getOptionalCount();
        LocalDate deliveryDate = current.plusDays(dayToAdd);
        String message = "La sua auto sara' disponibile per il ritiro dal " + deliveryDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                + "\nPresso la sede di " + SessionManager.getInstance().getOpenOrder().getShopLocation();

        mainController.showAlert("Consegna veicolo", message);
    }

    private void quotationUpdate(String quotation) throws IOException {
        FileWriter wc = new FileWriter(preventiviPath);
        wc.write(quotation);
        wc.close();
    }


    @FXML
    protected void onLogoutButton() {
        if (mainController.showConfirmationAlert("Attenzione",
                "Stai uscendo dalla tua area riservata, le configurazioni non completate verranno perse",
                "Sei sicuro di voler uscire?")) {
            SessionManager.getInstance().clearSession();
            mainController.loadHomePage();
        }
    }

    @FXML
    protected void onGoBackButton() {
        mainController.loadUserView();
    }

    @FXML
    protected void onExitButton() {
        Platform.exit();
    }
}
