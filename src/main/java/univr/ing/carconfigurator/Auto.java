package univr.ing.carconfigurator;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Auto {

    private List<String> auto = new ArrayList<>();
    private final String imgPath = "img/";
    private String brand;
    private String model;
    private double price;
    private double weight;
    private double height;
    private double width;
    private double length;
    private String defaultColor;
    private String color;

    private double trunkVolume;
    private Engine engine;
    private Engine defaultEngine;

    private Optional circle;
    private Optional interior;
    private Optional sensor;
    
    // Costruttore vuoto
    public Auto() {
    
    }

    // Costruttore che permette la generazione di una nuova auto
    public Auto(String brand, String model, double length, double width, double height,
                double weight, String defaultColor, double trunkVolume, Engine defaultEngine,
                double price) {
        this.brand = brand;
        this.model = model;
        this.length = length;
        this.width = width;
        this.height = height;
        this.weight = weight;
        this.defaultColor = defaultColor;
        this.trunkVolume = trunkVolume;
        this.defaultEngine = defaultEngine;
        this.price = price;
    }

    public Auto(String brand, String model, Engine engine, String color, Optional tyre,
                Optional sensor, Optional interior, double price) {
        // generazione dell'auto con gli optional gia' impostati dal preventivo.
        this.brand = brand;
        this.model = model;
        this.color = color;
        this.engine = engine;
        this.circle = tyre;
        this.sensor = sensor;
        this.interior = interior;
        this.price = price;
    }

    public String getImgPath(int index) {
        return imgPath + this.brand + "/" + this.model + "/" + this.color + "/"
                + index +".jpg";
    }

    public Auto(String brand, String model) {
        this.auto.add(brand);
        this.auto.add(model);
        getAutoInfo(brand, model);
    }

    public Auto(String desc) {
        for (int i = 0; i < 9; i++) {
            auto.add(desc.split(",")[i]);
        }
    }

    /**
     * Con questo metodo si recuperano dal database tutti i dati dell'auto
     * richiesta con i parametri.
     * @param brand -> Il nome della marca di auto
     * @param model -> Il nome del modello di auto
     */
    private void getAutoInfo(String brand, String model) {
        this.brand = brand;
        this.model = model;
        try {
            Scanner sc = new Scanner(new File("database/car.csv"));
            while (sc.hasNextLine()) {
                String[] car = sc.nextLine().split(",");
                if (brand.equals(car[0]) && model.equals(car[1])) {
                    
                    this.price = Double.parseDouble(car[2]);
                    this.weight = Double.parseDouble(car[3]);
                    this.height = Double.parseDouble(car[4]);
                    this.width = Double.parseDouble(car[5]);
                    this.length = Double.parseDouble(car[6]);
                    this.trunkVolume = Double.parseDouble(car[7]);
                    this.defaultEngine = new Engine(car[8]);
                    this.engine = new Engine(car[8]);
                    this.defaultColor = "Bianco";
                    this.color = "Bianco";
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String getBrand() {
        return this.brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return this.model;
    }

    public void setModel(String model) {
        this.model = model;
    }
    
    public void setDefaultColor(String defaultColor) {
        this.defaultColor = defaultColor;
    }

    public String getName() {
        return this.brand + " " + this.model;
    }

    public double getHeight() {
        return this.height;
    }
    
    public void setHeight(double height) {
        this.height = height;
    }
    public double getWidth() {
        return this.width;
    }
    
    public void setWidth(double width) {
        this.width = width;
    }
    
    public double getLength() {
        return this.length;
    }
    
    public void setLength(double length) {
        this.length = length;
    }
    
    public double getWeight() {
        return this.weight;
    }
    
    public void setWeight(double wieght) {
        this.weight = weight;
    }
    
    public double getTrunkVol() {
        return this.trunkVolume;
    }
    
    public void setTrunkVolume(double trunkVolume) {
        this.trunkVolume = trunkVolume;
    }
    
    // Questo metodo ritorna il valore del prezzo dell'auto
    // senza considerare il motore!
    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Engine getEngine() {
        if (engine != null)
            return this.engine;
        else {
            return this.defaultEngine;
        }
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public Engine getDefaultEngine() {
        return this.defaultEngine;
    }

    public String getDefaultColor() {
        return this.defaultColor;
    }

    public String getColor() {
        if (color != null) {
            return this.color;
        } else {
            return this.defaultColor;
        }
    }

    public double getColorPrice() {
        if (!this.color.equals(this.defaultColor)) {
            return 800.00;
        }
        return 0.00;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setCircle(Optional circle) {
        this.circle = circle;
    }

    public Optional getCircle() {
        return circle;
    }

    public void setInterior(Optional interior) {
        this.interior = interior;
    }

    public Optional getInterior() {
        return interior;
    }

    public void setSensor(Optional sensor) {
        this.sensor = sensor;
    }

    public Optional getSensor() {
        return sensor;
    }

    public String toString() {
        return "" + this.brand + " " + this.model + " " + this.price;
    }

    public String toCSV() {
        return this.brand + "," + this.model + "," + this.color + "," +
                this.engine.getName() + "," + this.circle + "," + this.sensor + "," +
                this.interior + "," + this.price;
    }

    public void saveNewAutoToDb() {
        String csvData = this.brand + "," + this.model + "," + this.price + "," +
                this.weight + "," + this.height + "," + this.width + "," +
                this.length + "," + this.trunkVolume + "," + this.defaultEngine + ",\n";
        
        try {
            FileWriter writer = new FileWriter("database/car.csv", true);
            writer.append(csvData);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public int getOptionalCount() {
        int count = 0;

        if (!engine.getName().equals(defaultEngine.getName())) {
            count++;
        }

        if (!color.toString().equals(defaultColor.toString())) {
            count++;
        }

        if (circle != null) {
            count++;
        }

        if (sensor != null) {
            count++;
        }

        if (interior != null) {
            count++;
        }

        return count;
    }


}
