package univr.ing.carconfigurator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Optional {

    private String name;
    private OptTypes type;
    private double price;


    public Optional(String name, OptTypes type) {
        this.name = name;
        this.type = type;

        try {
            assert getPath() != null;
            Scanner sc = new Scanner(new File(getPath()));

            while (sc.hasNextLine()) {
                String[] line = sc.nextLine().split(",");
                if (line[1].equals(this.name)) {
                    this.price = Double.parseDouble(line[2]);
                    System.out.println(price);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private String getPath() {
        if (type.equals(OptTypes.CERCHI)) {
            return "database/tire.csv";
        } else if (type.equals(OptTypes.INTERNI)) {
            return "database/interior.csv";
        } else if (type.equals(OptTypes.SENSORI)) {
            return "database/sensor.csv";
        } else
            return null;
    }

    public String getName() {
        return this.name;
    }

    public double getPrice() {
        return this.price;
    }
}
