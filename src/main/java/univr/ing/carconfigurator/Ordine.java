package univr.ing.carconfigurator;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Ordine {

    private String orderID; // giorno + orderTime
    private String userID; // per univocita'
    private String shopLocation;
    private Auto configuredCar; // contiene prezzo, motore, optional...private String optionals; // Formato: "optional1,optional2,optional3,...
    private LocalDate orderDate; // data di creazione
    private boolean oldCarDiscount; //
    private LocalDate deliveryDate; // data di consegna

    public Ordine(String csvQuotation) {

        String[] content = csvQuotation.split(",");
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
        this.shopLocation = content[12];
        this.deliveryDate = LocalDate.parse(content[13]);
    }

    // GETTER & SETTER


    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }


    public String getShopLocation() {
        return shopLocation;
    }

    public void setShopLocation(String shopLocation) {
        this.shopLocation = shopLocation;
    }

    public Auto getConfiguredCar() {
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

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public LocalDate getDeliveryDate() {
        return this.deliveryDate;
    }

    public String toString() {
        return this.orderID + " " + this.userID + " " + this.orderDate + " " +
                this.configuredCar.getBrand() + " " + this.configuredCar.getModel();
    }

    public String orderToCsv() {
        return this.orderID + "," + this.userID + "," + this.orderDate + "," +
                this.configuredCar.toCSV() + "," + this.oldCarDiscount + "," + this.shopLocation +
                "," + this.deliveryDate + ",\n";
    }

    public void writeToDb() {
        try {
            FileWriter fw = new FileWriter( "database/ordini.csv", true);
            String orderStr = orderToCsv();
            fw.append(orderStr);
            System.out.println(orderStr);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void moveToReady() {
        try {
            FileWriter fw = new FileWriter("database/ritiri.csv", true);
            String orderStr = orderToCsv();
            fw.append(orderStr);
            System.out.println(orderStr);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
