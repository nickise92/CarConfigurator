package univr.ing.carconfigurator;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

import javafx.stage.Stage;

import java.io.IOException;

public class GestioneOrdiniController {

    private MainController mainController;
    private Venditore vendor;

    @FXML
    private Label utente;
    @FXML
    private Label utenteLabel;
    @FXML
    private Label prezzo;
    @FXML
    private Label prezzoLabel;
    @FXML
    private Label DCS; // data di consegna
    @FXML
    private Label dataConsegnaLabel;
    @FXML
    private Label CDS; // concessionario di consegna
    @FXML
    private Label concessionarioLabel;
    @FXML
    private Label PAU; // Proposta Auto usata
    @FXML
    private Label propostaLabel;
    @FXML
    private Label VAU; // Valutazione auto usata
    @FXML
    private Label OC; // ordine confermata
    @FXML
    private Label ordineConfermatoLabel;
    @FXML
    private Button VU; // Valutazione usato
    @FXML
    private Button CO; // Conferma Ordine
    @FXML
    private Button LO = new Button("Log Out");
    @FXML
    private TextArea DescrizioneAuto;
    @FXML
    private Pagination paginazione;
    @FXML
    private   Label autoComprataLabel;
    @FXML
    private Label pageIndexLabel;

    @FXML
    public void initialize() {
        Platform.runLater(this::inizializzazione);
    }

    @FXML
    public void inizializzazione() {
        paginazione = new Pagination();
        paginazione.setPageFactory(this::createPage);
        paginazione.setPageCount(50); // Imposta il numero di pagine, potrebbe essere dinamico
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void setVendor(Venditore vendor) {
        this.vendor = vendor;
    }

    private Node createPage(int pageIndex) {
        AnchorPane pageContent = new AnchorPane();

        // Pulsanti
        VU = new Button("Valuta Usato");
        CO = new Button("Conferma Ordine");

        pageContent.getChildren().addAll(
                utenteLabel = new Label("Utente:"),
                utente = new Label(),
                prezzoLabel = new Label("Prezzo:"),
                prezzo = new Label(),
                dataConsegnaLabel = new Label("Data consegna stimata:"),
                DCS = new Label(),
                concessionarioLabel = new Label("Concessionario di consegna:"),
                CDS = new Label(),
                propostaLabel = new Label("Proposta auto usata:"),
                PAU = new Label(),
                ordineConfermatoLabel = new Label("Ordine Confermato:"),
                OC = new Label(),
                autoComprataLabel = new Label("Auto Comprata:"),
                DescrizioneAuto = new TextArea(),
                pageIndexLabel = new Label("Pagina " + (pageIndex + 1)),
                VU,
                CO
        );
        AnchorPane aPane = new AnchorPane(pageContent);
        return aPane;
    }

    @FXML
    protected void LOClick() throws IOException {
        mainController.loadHomePage();
        SessionManager.getInstance().setAuthenticatedVendor(null);
    }

}