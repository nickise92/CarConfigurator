package univr.ing.carconfigurator;

public class SessionManager {

    private static SessionManager currentInstance;


    private Utente authenticatedUser;
    private Auto configuredAuto;
    private boolean backFlag;




    private SessionManager() {
        // Costruttore della classe privato per prevenire l'istanziazione
    }

    public static SessionManager getInstance() {
        if (currentInstance == null) {
            currentInstance = new SessionManager();
        }

        return currentInstance;
    }

    public Utente getAuthenticatedUser() {
        return authenticatedUser;
    }

    public void setAuthenticatedUser(String userID) {
        // Ritorna il cliente, l'impiegato o il venditore corrispondente all'ID
        this.authenticatedUser = Utente.checkID(userID);
    }

    public void clearSession() {
        authenticatedUser = null;
    }

    public boolean isAuthenticated() {
        return authenticatedUser != null;
    }

    public Auto getConfiguredAuto() {
        return configuredAuto;
    }

    public void setConfiguredAuto(Auto auto) {
        this.configuredAuto = auto;
    }

    public void setBackFlag(boolean flag) {
        this.backFlag = flag;
    }

    public boolean getBackFlag() {
        return backFlag;
    }
}
