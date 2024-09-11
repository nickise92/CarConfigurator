package univr.ing.carconfigurator;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CarEvaluationView {

    private Auto car;
    private Cliente customer;
    private Venditore vendor;
    private MainController mainController;
    private SessionManager sessionManager;
    private int imgIndex = 0;
    private Valutazione valutazione;


    @FXML AnchorPane rootPane;
    @FXML AnchorPane imgPane;
    @FXML AnchorPane discountPane;
    @FXML ImageView carImage;
    @FXML TextField discountAmount;
    @FXML Label carConfiguredPrice;

    public CarEvaluationView() {

    }

    public void initialize() {

        Platform.runLater(this::centerContent);
        Platform.runLater(this::loadImage);
    }

    private void centerContent() {
        double width = rootPane.getWidth();
        double height = rootPane.getHeight();

        // Allineamento immagini a sinistra
        AnchorPane.setLeftAnchor(imgPane, (width - imgPane.getWidth()) / 4);
        AnchorPane.setTopAnchor(imgPane, (height - imgPane.getHeight()) / 2);
        // Allineamento pannello inserimento sconto
        AnchorPane.setRightAnchor(discountPane, (width - discountPane.getWidth()) / 4);
        AnchorPane.setTopAnchor(discountPane, (height - discountPane.getHeight()) / 2);
    }

    private void loadImage() {
        // Retrive customer data:
        customer = new Cliente(SessionManager.getInstance().getOpenQuotation().getUserID());
        setCarImage(customer, imgIndex);
    }

    private void setCarImage(Cliente customer, int index) {
        String imagePath = "img/Usato/" +  customer.getUserID() + "/" + customer.getUserName() + index + ".jpg";
        Image img = new Image(new File(imagePath).toURI().toString());
        carImage.setImage(img);
    }

    // Image navigation buttons
    public void onLeftArrow() {
        imgIndex = (imgIndex - 1) % 5;
        if (imgIndex < 0) {
            imgIndex += 5;
        }
        setCarImage(customer, imgIndex);
    }

    public void onRightArrow() {
        imgIndex = (imgIndex + 1) % 5;
        setCarImage(customer, imgIndex);
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void setVendor(Venditore vendor) {
        this.vendor = vendor;
    }

    public Venditore getVendor() {
        return vendor;
    }

    public void setCar(Auto car) {
        this.car = car;
    }

    public Auto getCar() {
        return car;
    }

    public void setCustomer(Cliente customer) {
        this.customer = customer;
    }

    public Cliente getCustomer() {
        return customer;
    }

    @FXML
    protected void onConfirmDiscount() {
        SessionManager.getInstance().setOldCarEvaluated(true);
        String discount = discountAmount.getText();
        valutazione = new Valutazione(SessionManager.getInstance().getOpenQuotation().getUserID(),
                SessionManager.getInstance().getOpenQuotation().getOrderID());

        SessionManager.getInstance().setValutazione(valutazione);

        if (discount.matches("[0-9]+")) {
            double finalPrice = SessionManager.getInstance().getOpenQuotation().getConfiguredCar().getPrice() - Double.parseDouble(discount);
            SessionManager.getInstance().getOpenQuotation().getConfiguredCar().setPrice(finalPrice);
            SessionManager.getInstance().getOpenQuotation().setOldCarValue(Double.parseDouble(discount));
            updateQuotationCsv(SessionManager.getInstance().getOpenQuotation().getOrderID(), finalPrice, Double.parseDouble(discount));
            mainController.loadVendorView();
        } else {
            mainController.showAlert("Attenzione", "Caratteri non validi inseriti. Si prega di inserire un valore numerico.");
        }
    }

    // Procedura di aggiornamento del prezzo nel file preventivi.
    private void updateQuotationCsv(String quotationID, double finalPrice, double evaluation) {
        // Leggo tutte le righe del file preventivi
        try {
            List<String> quotationCsvFile = new ArrayList<>();
            BufferedReader reader = new BufferedReader(new FileReader("database/preventivi.csv"));
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                quotationCsvFile.add(currentLine);
            }
            reader.close();

            // Filtro le righe per trovare quella da aggiornare
            List<String> updatedQuotationCsv = new ArrayList<>();
            for (String row : quotationCsvFile) {
                if (row.split(",")[0].equals(quotationID)) {
                    // Se la riga corrente e' il preventivo in valutaizone aggiorno il prezzo
                    String tmp = "";
                    for (int i = 0; i < row.split(",").length; i++) {
                        if (i != 10 && i != 11 && i != 12) {
                            tmp += row.split(",")[i] + ",";
                        } else if (i == 10){
                            tmp += String.valueOf(finalPrice) + ",";
                        } else if (i == 11) {
                            tmp += "false,";    // Ripristino a false la flag
                        } else if (i == 12) {
                            tmp+= evaluation + ","; // Aggiungo la valutazione al preventivo
                        }
                    }
                    updatedQuotationCsv.add(tmp);
                } else {
                    // Altrimenti aggiungo la riga alla lista aggiornata senza modificarla.
                    updatedQuotationCsv.add(row);
                }
            }

            // Riscrivo il file CSV con le righe aggiornate
            BufferedWriter writer = new BufferedWriter(new FileWriter("database/preventivi.csv"));
            for (String row : updatedQuotationCsv) {
                writer.write(row);
                writer.newLine();
            }
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void onExitButton() {
        Platform.exit();
    }

}
