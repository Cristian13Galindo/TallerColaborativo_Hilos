package com.example.proyecto_final.Controlers;

import com.example.proyecto_final.Model.Pacient;
import com.example.proyecto_final.Persistence.PacientPersistence;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.util.List;

public class ConsultPacientWindowController {
    @FXML
    private Button btnConsultPacient;
    @FXML
    private Button btnBack;
    @FXML
    private Button btnDeletePatient;
    @FXML
    private TextField txtDni;
    @FXML
    private TableView<Pacient> tableInfo;
    @FXML
    private TableColumn<Pacient, String> dni;
    @FXML
    private TableColumn<Pacient, String> fullName;
    @FXML
    private TableColumn<Pacient, String> dateOfBirth;
    @FXML
    private TableColumn<Pacient, String> priority;
    @FXML
    private TableColumn<Pacient, Double> progress; // Add this lin
    @FXML
    private Label lblStatus;

    private final PacientPersistence pacientPersistence = new PacientPersistence();

    @FXML
    public void initialize() {
        fillTableWithPacients();

        txtDni.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                clicConsultPacient();
            }
        });

        btnConsultPacient.setOnMouseEntered(event -> btnConsultPacient.setCursor(Cursor.HAND));
        btnBack.setOnMouseEntered(event -> btnBack.setCursor(Cursor.HAND));
        btnDeletePatient.setOnMouseEntered(event -> btnDeletePatient.setCursor(Cursor.HAND));
    }

    public void clicBack(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MenuUserDentistWindow.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);

            Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            currentStage.setScene(scene);
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void clicConsultPacient() {
        String dniInput = txtDni.getText();

        if (dniInput.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Advertencia");
            alert.setHeaderText(null);
            alert.setContentText("Campo vacío, por favor ingrese un número de identificación");
            alert.showAndWait();
            return;
        }

        List<Pacient> pacients = pacientPersistence.getAllPacients();
        boolean found = pacients.stream().anyMatch(pacient -> pacient.getDni().equals(dniInput));

        if (found) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ProcessPacientWindow.fxml"));
                Parent root = fxmlLoader.load();
                Scene scene = new Scene(root);

                Stage currentStage = (Stage) txtDni.getScene().getWindow();
                currentStage.setScene(scene);
                currentStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Paciente no encontrado");
            alert.showAndWait();
        }
    }

    public void clicDeletePatient() {
        Pacient removedPacient = pacientPersistence.removePacientByPriority();
        if (removedPacient != null) {
            refreshTable();
            showAlert(Alert.AlertType.INFORMATION, "Paciente eliminado", "¡Paciente eliminado correctamente!");
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "No se pudo eliminar el paciente");
        }
    }

    public void fillTableWithPacients() {
        List<Pacient> pacients = pacientPersistence.getAllPacients();

        pacients.forEach(pacient -> System.out.println(pacient.getDni() + ", " + pacient.getName() + ", " + pacient.getDateOfBirth()));

        dni.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDni()));
        fullName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName() + " " + cellData.getValue().getLastName()));
        dateOfBirth.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDateOfBirth()));
        priority.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getPriority())));

        // Configure the progress bar with custom cell factory
        progress.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getProgress()).asObject());
        progress.setCellFactory(param -> new ColoredProgressBarTableCell());

        ObservableList<Pacient> observableList = FXCollections.observableArrayList(pacients);
        tableInfo.setItems(observableList);

        // Sort the table by priority
        priority.setSortType(TableColumn.SortType.ASCENDING);
        tableInfo.getSortOrder().add(priority);
        tableInfo.sort();
    }

    private void refreshTable() {
        List<Pacient> pacients = pacientPersistence.getAllPacients();
        ObservableList<Pacient> observableList = FXCollections.observableArrayList(pacients);
        tableInfo.setItems(observableList);
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

