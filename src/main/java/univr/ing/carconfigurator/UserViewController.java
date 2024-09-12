package univr.ing.carconfigurator;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Vista utente registrato. In questa sezione viene descritta la logica di controllo
 * dell'interfaccia utente dopo l'accesso. Da qui e' possibile recuperare i preventivi
 * pendenti da confermare, visualizzarne il riepilogo, confermarli o annullarli. Inoltre,
 * e' possibile trovare la notifica di ritiro veicolo quando pronto nella concessionaria di
 * riferimento.
 */
public class UserViewController {

    private MainController mainController;
    private Utente user;
    private ObservableList<Preventivo> listaPreventivi = FXCollections.observableArrayList();
    private Preventivo quotation;

    @FXML private AnchorPane rootPane;
    @FXML private AnchorPane readyBox;
    @FXML private TabPane readyRecap;
    @FXML private Button configureCar;
    @FXML private Button logoutButton;
    @FXML private ChoiceBox<Preventivo> quotationChoiceBox;
    @FXML private GridPane orderBox;
    @FXML private Label userLogged;
    @FXML private Label title;

    public UserViewController () {

    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void setUser(Utente user) {
        this.user = user;
    }

    @FXML
    public void initialize() {

        Platform.runLater(this::updateUserAccessStatus);
        Platform.runLater(this::centerContent);
        Platform.runLater(this::getOrderList);
        Platform.runLater(this::getReadyList);
    }

    private void updateUserAccessStatus() {
        if (userLogged != null && user != null) {
            userLogged.setText(user.getUserName() + " " + user.getUserLastName());
        }
    }

    private void getOrderList() {
        // Vogliamo inserire nella lista dei preventivi confermabili solo
        // quelli fatti negli ultimi 20 giorni, e che non presentano
        // una richiesta di valutazione usato.
        try {
            Scanner quotationSc = new Scanner(new File("database/preventivi.csv"));
            while (quotationSc.hasNextLine()) {
                String line = quotationSc.nextLine();
                quotation = new Preventivo(line);
                if (!quotation.getOldCarDiscount() && Preventivo.checkQuotationValidity(quotation)) {
                    String message = quotation.checkEvaluation();
                    if (message != null) {
                        mainController.showAlert("Valutazione usato", message);
                    }
                    listaPreventivi.add(quotation);
                }
                // Altrimenti lo ignoro
            }
            quotationChoiceBox.setItems(listaPreventivi);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void getReadyList() {
        // Apro il database degli ordini pronti per il ritiro
        // e creo la tab corrispondente.
        List<String> readyCarList = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("database/ritiri.csv"));
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                readyCarList.add(currentLine);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Ora creo una tab per ogni riga della lista di automobili pronte per il ritiro
        for (String item : readyCarList) {
            Ordine order = new Ordine(item);

            Tab tab = new Tab(order.getConfiguredCar().getName());
            tab.setContent(readyCarInfo(order));

            readyRecap.getTabs().add(tab);
        }
    }

    private Node readyCarInfo(Ordine order) {

        AnchorPane tmp = new AnchorPane();
        tmp.getStyleClass().add("readyCarRecap");
        tmp.setMinSize(500.0, 200.0);
        Label engine = new Label("Motore: " + order.getConfiguredCar().getEngine().getName());
        Label colour = new Label("Colore: " + order.getConfiguredCar().getColor());
        Label tyre = new Label("Cerchi: " + (order.getConfiguredCar().getCircle().equals("null") ? order.getConfiguredCar().getCircle() : "di serie" ));
        Label sensor = new Label("Sensori: " + (order.getConfiguredCar().getSensor().equals("null") ? order.getConfiguredCar().getSensor() : "nessun optional"));
        Label interior = new Label("Interni: " + (order.getConfiguredCar().getInterior().equals("null") ? order.getConfiguredCar().getInterior() : "di serie"));
        Label price = new Label("Prezzo: " + order.getConfiguredCar().getPrice());

        tmp.getChildren().clear();
        tmp.getChildren().setAll(engine, colour, tyre, sensor, interior, price);

        AnchorPane.setTopAnchor(engine, 10.0);
        AnchorPane.setTopAnchor(colour, 30.0);
        AnchorPane.setTopAnchor(tyre, 50.0);
        AnchorPane.setTopAnchor(sensor, 70.0);
        AnchorPane.setTopAnchor(interior, 90.0);
        AnchorPane.setTopAnchor(price, 110.0 );
        return tmp;
    }

    private void centerContent() {
        double width = rootPane.getWidth();
        double height = rootPane.getHeight();

        // Centering title and user
        AnchorPane.setLeftAnchor(title, (width - title.getWidth()) / 2);
        AnchorPane.setTopAnchor(title, 30.0);
        AnchorPane.setLeftAnchor(userLogged, (width - userLogged.getWidth()) / 2);
        AnchorPane.setTopAnchor(userLogged, 90.0);
        // Configure Car button pos
        AnchorPane.setTopAnchor(configureCar, (height - configureCar.getHeight()) / 3);
        AnchorPane.setLeftAnchor(configureCar, (width/2 - configureCar.getWidth()) / 2);
        // Order grid position
        AnchorPane.setTopAnchor(orderBox, (height - orderBox.getHeight()) / 3);
        AnchorPane.setRightAnchor(orderBox, (width/2 - orderBox.getWidth()) / 2);
        // Ready box position
        AnchorPane.setBottomAnchor(readyBox, (height - readyBox.getHeight()) / 3);
        AnchorPane.setLeftAnchor(readyBox, (width - readyBox.getWidth()) / 2);
        // Logout button
        AnchorPane.setLeftAnchor(logoutButton, (width - logoutButton.getWidth()) / 2);
    }

    @FXML
    protected void openSelectedOrder() {
        try {
            Scanner sc = new Scanner (new File("database/preventivi.csv"));
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                quotation = new Preventivo(line);
                if (quotation.getOrderID().equals(quotationChoiceBox.getValue().getOrderID())) {
                    if (mainController.showConfirmationAlert("Apertura ordine",
                            "Stai aprendo l'ordine #" + quotation.getOrderID(),
                            "Continuare?")) {
                        SessionManager.getInstance().setOpenQuotation(quotationChoiceBox.getValue());
                        mainController.loadUserOrderConfirmationView();
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void startCarConfiguration() {
        SessionManager.getInstance().setAuthenticatedUser(user.getUserID());
        mainController.loadFirstView();
    }

    @FXML
    protected void onSaveToPDF() {
        // Recupero il preventivo selezionato
        
        if ((quotation = quotationChoiceBox.getValue()) != null) {
            PDFConverter pdfConverter = new PDFConverter("pdf/" + quotation.getOrderID() + ".pdf",
                    quotation);
            pdfConverter.printPdf();
            mainController.showAlert("Pdf creato!", "Il PDF e' stato generato con successo.");
        } else {
            mainController.showError("Errore!", "Nessun preventivo selezionato.");
        }
    }

    @FXML
    protected void onLogout() {
        if (mainController.showConfirmationAlert("Attenzione",
                "Stai uscendo dalla tua area riservata, le configurazioni non completate verranno perse",
                "Sei sicuro di voler uscire?")) {
            SessionManager.getInstance().clearSession();
            mainController.loadHomePage();
        }
    }

    @FXML
    protected void onExitButtonClick() {
        Platform.exit();
    }


}
