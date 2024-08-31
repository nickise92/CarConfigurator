package univr.ing.carconfigurator;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class CarEvaluationView {

    private Auto car;
    private Cliente customer;
    private Venditore vendor;
    private MainController mainController;
    private SessionManager sessionManager;

    @FXML TextField discountAmount;
    @FXML Label carConfiguredPrice;

    public CarEvaluationView() {

    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void setVendor(Venditore vendor) {
        this.vendor = vendor;
    }

    public Venditore getVendor() {
        return vendor;
    }

    public void setCar(Auto car) {
        this.car = car;
    }

    public Auto getCar() {
        return car;
    }

    public void setCustomer(Cliente customer) {
        this.customer = customer;
    }

    public Cliente getCustomer() {
        return customer;
    }

    @FXML
    protected void onExitButton() {
        Platform.exit();
    }

}
