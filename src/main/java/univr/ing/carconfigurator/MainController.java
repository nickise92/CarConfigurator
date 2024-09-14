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
            controller.setMainController(this);
            controller.setUser(SessionManager.getInstance().getAuthenticatedUser());
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
            controller.setMainController(this);
            if (SessionManager.getInstance().isVendorAuthenticated()) {
                controller.setVendor(SessionManager.getInstance().getAuthenticatedVendor());
            }
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
    
    public void loadFirstView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("configuratorFirstView.fxml"));
            Parent configPage = loader.load();
            FirstViewController controller = loader.getController();
            controller.setMainController(this);
            if (SessionManager.getInstance().isUserAuthenticated()) {
                controller.setUser(SessionManager.getInstance().getAuthenticatedUser());
            }
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
            controller.setMainController(this);
            if (SessionManager.getInstance().isUserAuthenticated()) {
                controller.setUser(SessionManager.getInstance().getAuthenticatedUser());
            }
            controller.setAuto(SessionManager.getInstance().getConfiguredAuto());
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
            controller.setMainController(this);
            if (SessionManager.getInstance().isUserAuthenticated()) {
                controller.setUser(SessionManager.getInstance().getAuthenticatedUser());
            }
            controller.setAuto(SessionManager.getInstance().getConfiguredAuto());
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
            controller.setMainController(this);
            if (SessionManager.getInstance().isUserAuthenticated()) {
                controller.setUser(SessionManager.getInstance().getAuthenticatedUser());
            }
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

    public void loadUserOrderConfirmationView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("userOrderConfirmationView.fxml"));
            Parent userOrderConfirmation = loader.load();
            UserOrderController controller = loader.getController();
            controller.setMainController(this);
            if (SessionManager.getInstance().isUserAuthenticated()) {
                controller.setUser(SessionManager.getInstance().getAuthenticatedUser());
            }
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

    public void loadCarEvaluationView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("carEvaluationView.fxml"));
            Parent carEval = loader.load();
            CarEvaluationView controller = loader.getController();
            controller.setMainController(this);
            controller.setVendor(SessionManager.getInstance().getAuthenticatedVendor());

            AnchorPane.setTopAnchor(carEval, 0.0);
            AnchorPane.setLeftAnchor(carEval, 0.0);
            AnchorPane.setRightAnchor(carEval, 0.0);
            AnchorPane.setBottomAnchor(carEval, 0.0);

            mainPane.getChildren().clear();
            mainPane.getChildren().add(carEval);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** ========================== ALERT ========================== **/
    public void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
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
