package univr.ing.carconfigurator;

public class SessionManager {

    private static SessionManager currentInstance;


    private Utente authenticatedUser;
    private Utente authenticatedVendor;
    private Utente authenticatedAdmin;
    private Auto configuredAuto;
    private boolean backFlag;
    private String riepilogo;
    private boolean usedEvaluationRequested = false;
    private Preventivo openOrder;
    private String AutoCSV;

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

    public void setAuthenticatedVendor(String userID) {
        this.authenticatedVendor = Utente.checkID(userID);
    }

    public void clearSession() {
        authenticatedUser = null;
        authenticatedVendor = null;
        authenticatedAdmin = null;
    }

    public boolean isUserAuthenticated() {
        return authenticatedUser != null;
    }

    public boolean isVendorAuthenticated() {
        return authenticatedVendor != null;
    }
    public boolean isAdminAuthenticated(){
        return authenticatedAdmin != null;
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

    public void setRiepilogo(String riepilogo) {
        this.riepilogo = riepilogo;
    }

    public String getRiepilogo() {
        return riepilogo;
    }

    public void setUsedEvaluationRequested(boolean flag) {
        this.usedEvaluationRequested = flag;
    }

    public boolean getUsedEvaluationRequested() {
        return this.usedEvaluationRequested;
    }

    public void setAuthenticatedVendor(Venditore vendor) {
        this.authenticatedVendor = vendor;
    }

    public Venditore getAuthenticatedVendor() {
        return (Venditore) this.authenticatedVendor;
    }

    public void setOpenOrder(Preventivo prv) {
        this.openOrder = prv;
    }

    public Preventivo getOpenOrder() {
        return this.openOrder;
    }
    public Impiegato getAuthenticatedAdmin(){ return (Impiegato) this.authenticatedAdmin;}

    public void setCarCsv(String AutoCSV) {
        this.AutoCSV = AutoCSV;
    }
    public String getCarCsv() {return AutoCSV;}
}