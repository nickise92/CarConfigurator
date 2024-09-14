package univr.ing.carconfigurator;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class Preventivo {

    private String orderID; // giorno + orderTime
    private String userID; // per univocita'
    private String shopLocation;
    private Auto configuredCar; // contiene prezzo, motore, optional...private String optionals; // Formato: "optional1,optional2,optional3,...
    private LocalDate orderDate; // data di creazione
    private boolean oldCarDiscount; // Se il preventivo allega un auto da valutare

    public Preventivo() {

    }

    public Preventivo(String csvLine) {
        String[] content = csvLine.split(",");
        this.orderID = content[0];
        this.userID = content[1];
        this.orderDate = LocalDate.parse(content[2]);
        this.shopLocation = content[12];
        this.configuredCar = new Auto(content[3], content[4]);
        this.configuredCar.setColor(content[5]);
        this.configuredCar.setEngine(new Engine(content[6]));
        this.configuredCar.setCircle(new Optional(content[7], OptTypes.CERCHI));
        this.configuredCar.setSensor(new Optional(content[8], OptTypes.SENSORI));
        this.configuredCar.setInterior(new Optional(content[9], OptTypes.INTERNI));
        this.configuredCar.setPrice(Double.parseDouble(content[10]));
        this.oldCarDiscount = Boolean.parseBoolean(content[11]);

    }

    public Preventivo(String orderID, String userID, LocalDate date, Auto configuredCar, String shopLocation) {
        this.orderID = orderID;
        this.userID = userID;
        this.orderDate = date;
        this.configuredCar = configuredCar;
        this.shopLocation = shopLocation;
    }

    public Preventivo(String s, String s1) {
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

    public boolean isOldCarDiscount() {
        return oldCarDiscount;
    }

    public void setOldCarDiscount(boolean oldCarDiscount) {
        this.oldCarDiscount = oldCarDiscount;
    }

    public static boolean checkOrderValidity(Preventivo preventivo) {
        LocalDate currentDay = LocalDate.now();
        return ChronoUnit.DAYS.between(preventivo.orderDate, LocalDate.now()) < 20;
    }

    public void saveToDb() throws IOException {

        FileWriter wr = new FileWriter("database/preventivi.csv", true);
        String data = this.orderID + "," + this.userID + "," +
                this.orderDate + "," +
                this.configuredCar.toCSV() + "," +
                SessionManager.getInstance().getUsedEvaluationRequested() + "," +
                this.shopLocation + ",\n";

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
