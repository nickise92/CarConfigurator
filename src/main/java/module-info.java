module univr.ing.carconfigurator {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires javafx.graphics;
    requires kernel;
    requires layout;
    requires com.opencsv;
    
    opens univr.ing.carconfigurator to javafx.fxml;
    exports univr.ing.carconfigurator;
}