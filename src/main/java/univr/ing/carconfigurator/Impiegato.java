package univr.ing.carconfigurator;

public class Impiegato extends Utente {

    public Impiegato(String userID, String userName, String userLastName, String userPsw) {
        super(userID, userName, userLastName, userPsw);
    }

    public Impiegato(String userID) {
        super(userID);
    }
}
