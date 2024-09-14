package univr.ing.carconfigurator;

import java.util.ArrayList;
import java.util.List;

public class Cliente extends Utente {

    List<String> orderList = new ArrayList<String>();

    public Cliente(String userID, String userName, String userLastName, String userPsw) {
        super(userID, userName, userLastName, userPsw);
    }

    public Cliente(String user) {
        super(user);
    }

    public String toTxt() {
        return getUserID();
    }
}
