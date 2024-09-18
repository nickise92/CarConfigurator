package univr.ing.carconfigurator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Engine {

    private final String enginePath = "database/engine.csv";

    private String brand;
    private String name;
    private String fuelType;
    private String accelerationTime;
    private String gramsCO2perKm;   // grammi di CO2 prodotta per km
    private String consumption;     // consumi
    private String displacement;    // cilindrata
    private String power;           // potenza
    private double price;

    // Costruttore che genera un oggetto motore gia' caricato
    // nel database dei motori opzionali
    public Engine(String name) {

        this.name = name;

        try {
            Scanner sc = new Scanner(new File(enginePath));
            while (sc.hasNextLine()) {
                String[] line = sc.nextLine().split(",");

                if (line.length == 9 &&  line[1].equals(name)) {
                    this.brand = line[0];
                    this.fuelType = line[2];
                    this.accelerationTime = line[3];
                    this.gramsCO2perKm = line[4];
                    this.consumption = line[5];
                    this.displacement = line[6];
                    this.power = line[7];
                    this.price = Double.parseDouble(line[8]);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Costruttore che permette di aggiungere un nuovo motore alla lista di motori
    public Engine(String brand, String name, String fuelType, String accelerationTime, String emission, String consumption, String displacement,
                  String power, double price) {
        this.brand = brand;
        this.name = name;
        this.fuelType = fuelType;
        this.accelerationTime = accelerationTime;
        this.gramsCO2perKm = emission;
        this.consumption = consumption;
        this.displacement = displacement;
        this.power = power;
        this.price = price;
    }

    // Getter dei parametri della classe Engine.
    public String getBrand() {return brand;}

    public String getName() {
        return name;
    }

    public String getFuelType() {
        return fuelType;
    }

    public String getAccelerationTime() {
        return accelerationTime;
    }

    public String getGramsCO2perKm() {
        return gramsCO2perKm;
    }

    public String getConsumption() {
        return consumption;
    }

    public String getDisplacement() {
        return displacement;
    }

    public String getPower() {
        return power;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return getName();
    }
    
    public void saveNewEngineToDb() {
        String csvData = this.brand + "," + this.name + "," + this.fuelType + "," +
                this.accelerationTime + "s," + this.gramsCO2perKm + " g/km," +
                this.consumption + " l/100km," + this.displacement + "," +
                this.power + "," + this.price + ",\n";

        try {
            FileWriter writer = new FileWriter(enginePath, true);
            writer.append(csvData);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
