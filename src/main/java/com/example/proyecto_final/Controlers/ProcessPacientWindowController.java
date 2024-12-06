package com.example.proyecto_final.Controlers;

import com.example.proyecto_final.Model.Pacient;
import com.example.proyecto_final.Persistence.PacientPersistence;
import com.example.proyecto_final.Persistence.ProcessPersistence;
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
import javafx.stage.Stage;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class ProcessPacientWindowController {
    public TextArea txtProcess;

    private String patientDni;

    @FXML
    private Button btnSaveChanges;
    @FXML
    private Button btnBack;
    @FXML
    private Button btnConsult;


    public void setPatientDni(String dni) {
        System.out.println("DNI establecido: " + dni);
        this.patientDni = dni;
    }

    PacientPersistence pacientPersistence = new PacientPersistence();

    public TableView<Pacient> tableInfo;
    public TableColumn<Pacient, String> dni;
    public TableColumn<Pacient, String> fullName;
    public TableColumn<Pacient, String> dateOfProcess;

    public void initialize() {
        Pacient pacient = pacientPersistence.getPacientByDni(patientDni);

        if (pacient != null) {
            dni.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDni()));
            fullName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName() + " " + cellData.getValue().getLastName()));
            dateOfProcess.setCellValueFactory(cellData -> new SimpleStringProperty(LocalDate.now().toString()));

            ObservableList<Pacient> observableList = FXCollections.observableArrayList(pacient);
            tableInfo.setItems(observableList);
        } else {
            System.out.println("Paciente no encontrado");
        }

        btnSaveChanges.setOnMouseEntered(event -> btnSaveChanges.setCursor(Cursor.HAND));
        btnBack.setOnMouseEntered(event -> btnBack.setCursor(Cursor.HAND));
        btnConsult.setOnMouseEntered(event -> btnConsult.setCursor(Cursor.HAND));
    }

    public void clicSaveChanges(ActionEvent actionEvent) {
        System.out.println("El botón 'Guardar Cambios' ha sido presionado.");

        Pacient pacient = pacientPersistence.getPacientByDni(patientDni);
        if (pacient != null) {
            System.out.println("Paciente seleccionado: " + pacient.getDni());

            // Leer procesos existentes del archivo JSON
            ProcessPersistence processPersistence = new ProcessPersistence();
            List<Pacient> existingProcesses;
            try {
                existingProcesses = processPersistence.readJsonFile(patientDni);
            } catch (IOException e) {
                System.out.println("Error al leer el archivo JSON: " + e.getMessage());
                e.printStackTrace();
                return;
            }

            // Contar cuántos procesos ya existen para la fecha actual
            LocalDate today = LocalDate.now();
            long count = existingProcesses.stream()
                    .filter(p -> p.getDateOfProcess().startsWith(today.toString()))
                    .count();

            // Generar la nueva fecha de proceso con sufijo
            String newDateOfProcess = today.toString() + "/" + (count + 1);

            pacient.setDateOfProcess(newDateOfProcess);
            pacient.setProcess(txtProcess.getText());

            System.out.println("Fecha de proceso establecida: " + pacient.getDateOfProcess());
            System.out.println("Proceso establecido: " + pacient.getProcess());

            try {
                processPersistence.createJsonFile(pacient);
                System.out.println("Archivo JSON creado para el paciente: " + pacient.getDni());
            } catch (IOException e) {
                System.out.println("Error al crear el archivo JSON: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("No se seleccionó ningún paciente.");
        }
    }

    public void clicConsult(ActionEvent actionEvent) {
        try {
            List<Pacient> pacients = new ProcessPersistence().readJsonFile(patientDni);

            StringBuilder dates = new StringBuilder();
            for (Pacient pacient : pacients) {
                dates.append(pacient.getDateOfProcess()).append("\n");
            }

            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Consultar Procedimiento");
            dialog.setHeaderText("Fechas de los procedimientos:\n" + dates.toString());
            dialog.setContentText("Ingrese una fecha:");

            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                String date = result.get();

                for (Pacient pacient : pacients) {
                    if (pacient.getDateOfProcess().equals(date)) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Información del Procedimiento");
                        alert.setHeaderText("Procedimiento del " + date);
                        alert.setContentText(pacient.getProcess());
                        alert.showAndWait();
                        break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void clicBack(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ConsultPacientWindow.fxml"));
            Parent root = fxmlLoader.load();

            Scene scene = new Scene(root);

            Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            currentStage.setScene(scene);

            currentStage.show();

            Stage menuStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            menuStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
