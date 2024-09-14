package univr.ing.carconfigurator;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.Optional;

public class MainController {

    @FXML private AnchorPane mainPane;




    @FXML
    public void initialize() {
        loadHomePage();
    }


    public void loadLoginPage() {
        if (SessionManager.getInstance().isUserAuthenticated()) {
            showAlert("Autenticazione invalida", "L'utente " + SessionManager.getInstance().getAuthenticatedUser() +
                    " è già autenticato.");
        } else {

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
                Parent loginPage = loader.load();
                LoginController controller = loader.getController();
                controller.setMainController(this);
                AnchorPane.setTopAnchor(loginPage, 0.0);
                AnchorPane.setRightAnchor(loginPage, 0.0);
                AnchorPane.setBottomAnchor(loginPage,0.0);
                AnchorPane.setLeftAnchor(loginPage, 0.0);
                mainPane.getChildren().clear();
                mainPane.getChildren().add(loginPage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void loadHomePage() {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("home.fxml"));
            Parent homePage = loader.load();
            HomeController controller = loader.getController();
            controller.setUser(SessionManager.getInstance().getAuthenticatedUser());
            controller.setMainController(this);
            AnchorPane.setTopAnchor(homePage, 0.0);
            AnchorPane.setRightAnchor(homePage, 0.0);
            AnchorPane.setBottomAnchor(homePage, 0.0);
            AnchorPane.setLeftAnchor(homePage, 0.0);
            mainPane.getChildren().clear();
            mainPane.getChildren().add(homePage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadVendorView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("vendorView.fxml"));
            Parent vendorPage = loader.load();
            VendorViewController controller = loader.getController();
            if (SessionManager.getInstance().isVendorAuthenticated()) {
                controller.setVendor(SessionManager.getInstance().getAuthenticatedVendor());
            }
            controller.setMainController(this);
            AnchorPane.setTopAnchor(vendorPage, 0.0);
            AnchorPane.setRightAnchor(vendorPage, 0.0);
            AnchorPane.setBottomAnchor(vendorPage, 0.0);
            AnchorPane.setLeftAnchor(vendorPage, 0.0);
            mainPane.getChildren().clear();
            mainPane.getChildren().add(vendorPage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadGestioneOrdini() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("gestioneOrdiniView.fxml"));
            Parent orderPage = loader.load();
            GestioneOrdiniController controller = loader.getController();
            if (SessionManager.getInstance().isUserAuthenticated()) {
                controller.setVendor(SessionManager.getInstance().getAuthenticatedVendor());
            }
            controller.setMainController(this);
            AnchorPane.setTopAnchor(orderPage, 0.0);
            AnchorPane.setRightAnchor(orderPage, 0.0);
            AnchorPane.setBottomAnchor(orderPage, 0.0);
            AnchorPane.setLeftAnchor(orderPage, 0.0);
            mainPane.getChildren().clear();
            mainPane.getChildren().add(orderPage);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void loadFirstView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("configuratorFirstView.fxml"));
            Parent configPage = loader.load();
            FirstViewController controller = loader.getController();
            if (SessionManager.getInstance().isUserAuthenticated()) {
                controller.setUser(SessionManager.getInstance().getAuthenticatedUser());
            } else {
                System.out.println("Accesso come ospite");
            }
            controller.setMainController(this);
            AnchorPane.setTopAnchor(configPage, 0.0);
            AnchorPane.setRightAnchor(configPage, 0.0);
            AnchorPane.setBottomAnchor(configPage, 0.0);
            AnchorPane.setLeftAnchor(configPage, 0.0);
            mainPane.getChildren().clear();
            mainPane.getChildren().add(configPage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadSecondView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("configuratorSecondView.fxml"));
            Parent configPage = loader.load();
            SecondViewController controller = loader.getController();
            if (SessionManager.getInstance().isUserAuthenticated()) {
                controller.setUser(SessionManager.getInstance().getAuthenticatedUser());
            }
            controller.setAuto(SessionManager.getInstance().getConfiguredAuto());
            controller.setMainController(this);
            AnchorPane.setTopAnchor(configPage, 0.0);
            AnchorPane.setRightAnchor(configPage, 0.0);
            AnchorPane.setBottomAnchor(configPage, 0.0);
            AnchorPane.setLeftAnchor(configPage, 0.0);
            mainPane.getChildren().clear();
            mainPane.getChildren().setAll(configPage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadThirdView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("configuratorThirdView.fxml"));
            Parent configPage = loader.load();
            ThirdViewController controller = loader.getController();
            if (SessionManager.getInstance().isUserAuthenticated()) {
                controller.setUser(SessionManager.getInstance().getAuthenticatedUser());
            }
            controller.setAuto(SessionManager.getInstance().getConfiguredAuto());
            controller.setMainController(this);
            AnchorPane.setTopAnchor(configPage, 0.0);
            AnchorPane.setRightAnchor(configPage, 0.0);
            AnchorPane.setBottomAnchor(configPage, 0.0);
            AnchorPane.setLeftAnchor(configPage, 0.0);
            mainPane.getChildren().clear();
            mainPane.getChildren().setAll(configPage);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadRegistrationView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("registration.fxml"));
            Parent registrationPage = loader.load();
            RegistrationController controller = loader.getController();
            controller.setMainController(this);
            AnchorPane.setTopAnchor(registrationPage, 0.0);
            AnchorPane.setRightAnchor(registrationPage, 0.0);
            AnchorPane.setBottomAnchor(registrationPage, 0.0);
            AnchorPane.setLeftAnchor(registrationPage, 0.0);
            mainPane.getChildren().clear();
            mainPane.getChildren().add(registrationPage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadUserView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("userView.fxml"));
            Parent userViewForm = loader.load();
            UserViewController controller = loader.getController();
            if (SessionManager.getInstance().isUserAuthenticated()) {
                controller.setUser(SessionManager.getInstance().getAuthenticatedUser());
            }
            controller.setMainController(this);
            AnchorPane.setTopAnchor(userViewForm, 0.0);
            AnchorPane.setRightAnchor(userViewForm, 0.0);
            AnchorPane.setBottomAnchor(userViewForm, 0.0);
            AnchorPane.setLeftAnchor(userViewForm, 0.0);
            mainPane.getChildren().clear();
            mainPane.getChildren().add(userViewForm);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void loadAdministratorview(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("administratorView.fxml"));
            Parent AdminPage = loader.load();
            AdministratorViewController controller = loader.getController();
            if (SessionManager.getInstance().isAdminAuthenticated()) {
                controller.setImpiegato(SessionManager.getInstance().getAuthenticatedAdmin());
            }
            controller.setMainController(this);
            AnchorPane.setTopAnchor(AdminPage, 0.0);
            AnchorPane.setRightAnchor(AdminPage, 0.0);
            AnchorPane.setBottomAnchor(AdminPage, 0.0);
            AnchorPane.setLeftAnchor(AdminPage, 0.0);
            mainPane.getChildren().clear();
            mainPane.getChildren().add(AdminPage);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    public void loadAdministratorAddCarFirstView(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AdministratorAddCarFirstView.fxml"));
            Parent addCarFirstView = loader.load();
            AdministratorAddCarFirstViewController controller = loader.getController();

            controller.setMainController(this);

            AnchorPane.setTopAnchor(addCarFirstView, 0.0);
            AnchorPane.setRightAnchor(addCarFirstView, 0.0);
            AnchorPane.setBottomAnchor(addCarFirstView, 0.0);
            AnchorPane.setLeftAnchor(addCarFirstView, 0.0);
            mainPane.getChildren().clear();
            mainPane.getChildren().add(addCarFirstView);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    public void loadAdministratorAddCarSecondView(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AdministratorAddCarSecondView.fxml"));
            Parent addCarsecondView = loader.load();
            AdministratorAddCarSecondViewController controller = loader.getController();
            controller.setMainController(this);

            AnchorPane.setTopAnchor(addCarsecondView, 0.0);
            AnchorPane.setRightAnchor(addCarsecondView, 0.0);
            AnchorPane.setBottomAnchor(addCarsecondView, 0.0);
            AnchorPane.setLeftAnchor(addCarsecondView, 0.0);
            mainPane.getChildren().clear();
            mainPane.getChildren().add(addCarsecondView);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    public void loadAdministratorAddCarthirdView(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("administratorAddCarThirdView.fxml"));
            Parent addCarThirdView = loader.load();
            AdministratorAddCarThirdViewController controller = loader.getController();
            controller.setMainController(this);
            AnchorPane.setTopAnchor(addCarThirdView, 0.0);
            AnchorPane.setRightAnchor(addCarThirdView, 0.0);
            AnchorPane.setBottomAnchor(addCarThirdView, 0.0);
            AnchorPane.setLeftAnchor(addCarThirdView, 0.0);
            mainPane.getChildren().clear();
            mainPane.getChildren().add(addCarThirdView);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    public void loadAdministratorAddOptionalView(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AdministratorAddOptionalView.fxml"));
            Parent AdministratoraddOptional = loader.load();
            AdministratorAddOptionalViewController controller = loader.getController();
            controller.setMainController(this);
            AnchorPane.setTopAnchor(AdministratoraddOptional, 0.0);
            AnchorPane.setRightAnchor(AdministratoraddOptional, 0.0);
            AnchorPane.setBottomAnchor(AdministratoraddOptional, 0.0);
            AnchorPane.setLeftAnchor(AdministratoraddOptional, 0.0);
            mainPane.getChildren().clear();
            mainPane.getChildren().add(AdministratoraddOptional);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    public void loadAdministratorAddEngine() {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AdministratorAddEngine.fxml"));
            Parent AdministratoraddEngine = loader.load();
            AdministratorAddEngineViewController controller = loader.getController();
            controller.setMainController(this);
            AnchorPane.setTopAnchor(AdministratoraddEngine, 0.0);
            AnchorPane.setRightAnchor(AdministratoraddEngine, 0.0);
            AnchorPane.setBottomAnchor(AdministratoraddEngine, 0.0);
            AnchorPane.setLeftAnchor(AdministratoraddEngine, 0.0);
            mainPane.getChildren().clear();
            mainPane.getChildren().add(AdministratoraddEngine);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    public void loadAdministratorEstimatesViewController() {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AdministratorEstimatesView.fxml"));
            Parent AdministratorEstimatesView = loader.load();
            AdministratorEstimatesViewController controller = loader.getController();
            controller.setMainController(this);
            AnchorPane.setTopAnchor(AdministratorEstimatesView, 0.0);
            AnchorPane.setRightAnchor(AdministratorEstimatesView, 0.0);
            AnchorPane.setBottomAnchor(AdministratorEstimatesView, 0.0);
            AnchorPane.setLeftAnchor(AdministratorEstimatesView, 0.0);
            mainPane.getChildren().clear();
            mainPane.getChildren().add(AdministratorEstimatesView);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public void loadUserOrderConfirmationView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("userOrderConfirmationView.fxml"));
            Parent userOrderConfirmation = loader.load();
            UserOrderController controller = loader.getController();
            if (SessionManager.getInstance().isUserAuthenticated()) {
                controller.setUser(SessionManager.getInstance().getAuthenticatedUser());
            }
            controller.setMainController(this);
            AnchorPane.setTopAnchor(userOrderConfirmation, 0.0);
            AnchorPane.setRightAnchor(userOrderConfirmation, 0.0);
            AnchorPane.setBottomAnchor(userOrderConfirmation, 0.0);
            AnchorPane.setLeftAnchor(userOrderConfirmation, 0.0);
            mainPane.getChildren().clear();
            mainPane.getChildren().add(userOrderConfirmation);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void loadSignInView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("signin.fxml"));
            Parent signInPage = loader.load();
            SignInController controller = loader.getController();
            controller.setMainController(this);
            controller.setAuto(SessionManager.getInstance().getConfiguredAuto());
            AnchorPane.setTopAnchor(signInPage, 0.0);
            AnchorPane.setRightAnchor(signInPage, 0.0);
            AnchorPane.setBottomAnchor(signInPage, 0.0);
            AnchorPane.setLeftAnchor(signInPage, 0.0);
            mainPane.getChildren().clear();
            mainPane.getChildren().add(signInPage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadUsedCarView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("usedCarProposition.fxml"));
            Parent usedPage = loader.load();
            UsedCarController controller = loader.getController();
            controller.setMainController(this);
            if (SessionManager.getInstance().isUserAuthenticated()) {
                controller.setUser(SessionManager.getInstance().getAuthenticatedUser());
            }
            controller.setAuto(SessionManager.getInstance().getConfiguredAuto());
            AnchorPane.setTopAnchor(usedPage, 0.0);
            AnchorPane.setRightAnchor(usedPage, 0.0);
            AnchorPane.setBottomAnchor(usedPage, 0.0);
            AnchorPane.setLeftAnchor(usedPage, 0.0);
            mainPane.getChildren().clear();
            mainPane.getChildren().add(usedPage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public boolean showBackAlert() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Attenzione!");
        alert.setHeaderText("Tornando indietro annullerai le personalizzazioni effettuate.");
        alert.setContentText("Vuoi continuare?");

        Optional<ButtonType> option = alert.showAndWait();

        if (option.get() == ButtonType.OK) {
            return true;
        }
        return false;
    }

    public boolean showConfirmationAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        Optional<ButtonType> option = alert.showAndWait();

        if (option.get() == ButtonType.OK) {
            return true;
        }
        return false;
    }

}

