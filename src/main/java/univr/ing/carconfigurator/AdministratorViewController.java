package univr.ing.carconfigurator;

public class AdministratorViewController {

    private MainController mainController;
    private Impiegato impiegato;

    public AdministratorViewController () {

    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void setImpiegato(Impiegato impiegato) {
        this.impiegato = impiegato;
    }

    public Impiegato getImpiegato() {
        return this.impiegato;
    }


}
