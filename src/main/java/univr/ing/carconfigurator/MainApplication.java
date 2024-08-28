package univr.ing.carconfigurator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;

public class MainApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));

        // Aggiunta file CSS personalizzato
        File stylesheetFile = new File ("custom.css");
        URL stylesheetUrl = stylesheetFile.toURI().toURL();
        root.getStylesheets().add(stylesheetUrl.toExternalForm());

        primaryStage.setTitle("Configuratore Auto");
        primaryStage.setScene(new Scene(root));
        primaryStage.setMaximized(true);
        primaryStage.show();
        
    }

    public static void main(String[] args) {
        launch();
    }

}
