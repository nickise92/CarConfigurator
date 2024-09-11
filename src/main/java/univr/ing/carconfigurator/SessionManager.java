package univr.ing.carconfigurator;

public class SessionManager {

    private static SessionManager currentInstance;


    private Utente authenticatedUser;
    private Utente authenticatedVendor;
    private Auto configuredAuto;
    private boolean backFlag;
    private String riepilogo;
    private boolean usedEvaluationRequested = false;
    private Preventivo openOrder;
    private double discountAmount;
    private boolean oldCarEvaluated = false;
    private Valutazione valutazione;

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
        configuredAuto = null;
        riepilogo = null;
        openOrder = null;
        oldCarEvaluated = false;
        usedEvaluationRequested = false;
        discountAmount = 0;
    }

    public boolean isUserAuthenticated() {
        return authenticatedUser != null;
    }

    public boolean isVendorAuthenticated() {
        return authenticatedVendor != null;
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

    public void setOpenQuotation(Preventivo prv) {
        this.openOrder = prv;
    }

    public Preventivo getOpenQuotation() {
        return this.openOrder;
    }

    public void setDiscountAmount(double amount) {
        this.discountAmount = amount;
    }

    public double getDiscountAmount() {
        return this.discountAmount;
    }

    public void setOldCarEvaluated(boolean flag) {
        this.oldCarEvaluated = flag;
    }

    public boolean getOldCarEvaluated() {
        return this.oldCarEvaluated;
    }

    public void setValutazione(Valutazione valutazione) {
        this.valutazione = valutazione;
    }

    public Valutazione getValutazione() {
        return this.valutazione;
    }
}