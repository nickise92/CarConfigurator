package univr.ing.carconfigurator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

public class Preventivo {

    private String orderID; // giorno + orderTime
    private String userID; // per univocita'
    private String shopLocation;
    private Auto configuredCar; // contiene prezzo, motore, optional...private String optionals; // Formato: "optional1,optional2,optional3,...
    private LocalDate orderDate; // data di creazione
    private boolean oldCarDiscount; // Se il preventivo allega un auto da valutare
    private double oldCarValue; // valore della valutazione dell'auto usata
    private Valutazione evaluation; // verifica interna del preventivo

    public Preventivo() {

    }

    public Preventivo(String csvLine) {
        String[] content = csvLine.split(",");
        this.orderID = content[0];
        this.userID = content[1];
        this.orderDate = LocalDate.parse(content[2]);
        this.configuredCar = new Auto(content[3], content[4]);
        this.configuredCar.setColor(content[5]);
        this.configuredCar.setEngine(new Engine(content[6]));
        this.configuredCar.setCircle(new Optional(content[7], OptTypes.CERCHI));
        this.configuredCar.setSensor(new Optional(content[8], OptTypes.SENSORI));
        this.configuredCar.setInterior(new Optional(content[9], OptTypes.INTERNI));
        this.configuredCar.setPrice(Double.parseDouble(content[10]));
        this.oldCarDiscount = Boolean.parseBoolean(content[11]);
        this.oldCarValue = Double.parseDouble(content[12]);
        this.shopLocation = content[13];
    }

    public Preventivo(String orderID, String userID, LocalDate date, Auto configuredCar, String shopLocation) {
        this.orderID = orderID;
        this.userID = userID;
        this.orderDate = date;
        this.configuredCar = configuredCar;
        this.shopLocation = shopLocation;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserID() {
        return userID;
    }

    public void setShopLocation(String shopLocation) {
        this.shopLocation = shopLocation;
    }

    public String getShopLocation() { return shopLocation; }

    public Auto getConfiguredCar(){
        return configuredCar;
    }

    public void setConfiguredCar(Auto configuredCar) {
        this.configuredCar = configuredCar;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public boolean getOldCarDiscount() {
        return oldCarDiscount;
    }

    public void setOldCarDiscount(boolean oldCarDiscount) {
        this.oldCarDiscount = oldCarDiscount;
    }

    public double getOldCarValue() {
        return oldCarValue;
    }

    public void setOldCarValue(double oldCarValue) {
        this.oldCarValue = oldCarValue;
    }

    public static boolean checkQuotationValidity(Preventivo preventivo) {
        LocalDate currentDay = LocalDate.now();
        return ChronoUnit.DAYS.between(preventivo.orderDate, LocalDate.now()) < 20;
    }

    public String checkEvaluation() {
        try {
            Scanner evaluationSc = new Scanner(new File("database/valutazioni.csv"));
            while (evaluationSc.hasNextLine()) {
                String tmp = evaluationSc.nextLine();
                evaluation = new Valutazione(tmp.split(",")[0], tmp.split(",")[1]);
                if (evaluation.getQuotationID().equals(orderID) &&
                evaluation.getUserID().equals(userID)) {
                    evaluation.delete();
                    return "L'usato allegato all'ordine " + orderID +
                            " è stato valutato.\n Il preventivo aggiornato è stato aggiunto alla sua lista preventivi.";
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void saveToDb() throws IOException {
        FileWriter wr = new FileWriter("database/preventivi.csv", true);
        String data = this.orderID + "," + this.userID + "," +
                this.orderDate + "," + this.configuredCar.toCSV() + "," +
                SessionManager.getInstance().getUsedEvaluationRequested() + "," +
                this.oldCarValue + "," + this.shopLocation + ",\n";

        System.out.println(data);
        wr.append(data);
        wr.close();
    }

    @Override
    public String toString() {
        return this.orderID + " " + this.userID + " " + this.orderDate + " " +
                this.configuredCar.getBrand() + " " + this.configuredCar.getModel();
    }
}
