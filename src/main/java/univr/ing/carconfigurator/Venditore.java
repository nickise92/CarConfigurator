package univr.ing.carconfigurator;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Venditore extends Utente {

    private String shop;

    public Venditore(String userID, String userName, String userLastName, String userPsw, String shop) {
        super(userID, userName, userLastName, userPsw);
        this.shop = shop;
    }

    public Venditore(String userID) {
        super(userID);

        try {
            Scanner sc = new Scanner(new File("database/users.csv"));

            while (sc.hasNextLine()) {
                String tmp = sc.nextLine();
                if (tmp.split(",")[0].equals(this.getUserID())) {
                    this.shop = tmp.split(",")[4];
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void setShop(String shop) {
        this.shop = shop;
    }
    
    public String getShop() {
        return shop;
    }


}
