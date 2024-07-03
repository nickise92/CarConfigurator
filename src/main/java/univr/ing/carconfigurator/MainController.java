package univr.ing.carconfigurator;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class MainController {

    @FXML private AnchorPane mainPane;


    @FXML
    public void initialize() {
        loadHomePage();
    }


    public void loadLoginPage() {
        if (SessionManager.getInstance().isAuthenticated()) {
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


    public void loadFirstView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("configuratorFirstView.fxml"));
            Parent configPage = loader.load();
            FirstViewController controller = loader.getController();
            if (SessionManager.getInstance().isAuthenticated()) {
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
            if (SessionManager.getInstance().isAuthenticated()) {
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

    public void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
