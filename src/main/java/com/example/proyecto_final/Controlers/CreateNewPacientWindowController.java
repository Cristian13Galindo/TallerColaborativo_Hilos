package com.example.proyecto_final.Controlers;

import com.example.proyecto_final.Model.Pacient;
import com.example.proyecto_final.Logic.Clinic;
import com.example.proyecto_final.Persistence.PacientPersistence;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class CreateNewPacientWindowController {

    @FXML
    private TextField txtName;
    @FXML
    private TextField txtLastName;
    @FXML
    private DatePicker btndateOfBirth;
    @FXML
    private TextField txtDni;
    @FXML
    private TextField txtPhone;
    @FXML
    private TextField txtEmail;

    @FXML
    private ComboBox<String> comboPriority;

    public Button btnBack;
    public Button btnCreatePacient;

    private final Clinic clinic = new Clinic();
    private final PacientPersistence pacientPersistence = new PacientPersistence();
    private Map<String, Integer> priorityMap;

    @FXML
    public void initialize() {
        btnCreatePacient.setCursor(Cursor.HAND);
        btnBack.setCursor(Cursor.HAND);

        Pattern pattern = Pattern.compile("[a-zA-Z]*");
        setTextFieldFormatter(txtName, pattern);
        setTextFieldFormatter(txtLastName, pattern);

        Pattern numberPattern = Pattern.compile("\\d{0,10}");
        setTextFieldFormatter(txtDni, numberPattern);
        setTextFieldFormatter(txtPhone, numberPattern);

        EventHandler<KeyEvent> enterKeyHandler = event -> {
            if (event.getCode() == KeyCode.ENTER) {
                clicCreatePacient(null);
            }
        };

        setEnterKeyHandler(enterKeyHandler);

        comboPriority.getItems().clear();
        comboPriority.getItems().addAll("Urgencia", "Dolor localizado", "Consulta de rutina");


        priorityMap = new HashMap<>();
        priorityMap.put("Urgencia", 1);
        priorityMap.put("Dolor localizado", 2);
        priorityMap.put("Consulta de rutina", 3);
    }

    private void setTextFieldFormatter(TextField textField, Pattern pattern) {
        TextFormatter<String> formatter = new TextFormatter<>(change -> pattern.matcher(change.getControlNewText()).matches() ? change : null);
        textField.setTextFormatter(formatter);
    }

    private void setEnterKeyHandler(EventHandler<KeyEvent> enterKeyHandler) {
        txtName.setOnKeyPressed(enterKeyHandler);
        txtLastName.setOnKeyPressed(enterKeyHandler);
        btndateOfBirth.setOnKeyPressed(enterKeyHandler);
        txtDni.setOnKeyPressed(enterKeyHandler);
        txtPhone.setOnKeyPressed(enterKeyHandler);
        txtEmail.setOnKeyPressed(enterKeyHandler);
    }

    public void clicBack(ActionEvent actionEvent) {
        changeScene(actionEvent, "MenuUserDentistWindow.fxml");
    }

    private void changeScene(ActionEvent actionEvent, String fxmlFile) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);

            Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            currentStage.setScene(scene);
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void clicCreatePacient(ActionEvent actionEvent) {
        String name = txtName.getText();
        String lastName = txtLastName.getText();
        String dateOfBirth = (btndateOfBirth.getValue() != null) ? btndateOfBirth.getValue().toString() : null;
        String dni = txtDni.getText();
        String phone = txtPhone.getText();
        String email = txtEmail.getText();
        String selectedPriority = comboPriority.getValue();
        Integer priority = priorityMap.get(selectedPriority);

        if (!validateFields(name, lastName, dateOfBirth, dni, phone, email, priority)) {
            return;
        }

        List<Pacient> existingPacients = pacientPersistence.getAllPacients();
        for (Pacient existingPacient : existingPacients) {
            if (existingPacient.getDni().equals(dni)) {
                showAlert(Alert.AlertType.ERROR, "Paciente ya existe", "El paciente con número de DNI " + dni + " ya existe");
                return;
            }
        }

        Pacient newPacient = new Pacient(name, lastName, dateOfBirth, dni, phone, email, priority);
        clinic.addPatient(newPacient);
        pacientPersistence.savePacient(newPacient);
        showAlert(Alert.AlertType.INFORMATION, "Paciente creado", "¡Paciente creado con éxito!");
        changeScene(actionEvent, "MenuUserDentistWindow.fxml");
    }

    private boolean validateFields(String name, String lastName, String dateOfBirth, String dni, String phone, String email, int priority) {
        if (name.isEmpty() || lastName.isEmpty() || dateOfBirth == null || dni.isEmpty() || phone.isEmpty() || email.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Campos incompletos", "Complete todos los campos para continuar.");
            return false;
        } else if (!validateEmail(email)) {
            showAlert(Alert.AlertType.ERROR, "Email inválido", "Ingrese un correo electrónico válido.");
            return false;
        } else if (dni.length() != 10) {
            showAlert(Alert.AlertType.ERROR, "DNI inválido", "El número de DNI debe tener exactamente 10 dígitos.");
            return false;
        } else if (phone.length() != 10) {
            showAlert(Alert.AlertType.ERROR, "Teléfono inválido", "El número de teléfono debe tener exactamente 10 dígitos.");
            return false;
        }
        return true;
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private boolean validateEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return Pattern.compile(emailRegex).matcher(email).matches();
    }
}