module univr.ing.carconfigurator {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires javafx.graphics;

    opens univr.ing.carconfigurator to javafx.fxml;
    exports univr.ing.carconfigurator;
}