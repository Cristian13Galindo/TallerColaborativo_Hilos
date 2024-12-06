module com.example.proyecto_final {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;

    opens com.example.proyecto_final.Model to com.google.gson;
    opens com.example.proyecto_final.Controlers to javafx.fxml;

    exports com.example.proyecto_final.Controlers;
}
