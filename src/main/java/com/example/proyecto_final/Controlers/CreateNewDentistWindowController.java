package com.example.proyecto_final.Controlers;

import com.example.proyecto_final.Model.Dentist;
import com.example.proyecto_final.Persistence.UserPersistence;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class CreateNewDentistWindowController {

    public Button btnCreateNewDentist;

    public TextField txtUserName;
    public PasswordField txtPassword;

    public void initialize() {
        btnCreateNewDentist.setCursor(Cursor.HAND);
        txtUserName.setOnKeyPressed(this::handleEnterKeyPressed);
        txtPassword.setOnKeyPressed(this::handleEnterKeyPressed);
    }

    private void handleEnterKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            clicCreateNewDentist();
        }
    }

    public void clicCreateNewDentist(ActionEvent actionEvent) {
        clicCreateNewDentist();
    }

    public void clicCreateNewDentist() {
        String userName = txtUserName.getText();
        String password = txtPassword.getText();

        if (userName.isEmpty() || password.isEmpty()) {
            showAlert("Error", "Username and password must not be empty", Alert.AlertType.ERROR);
            return;
        }

        UserPersistence writer = new UserPersistence();
        ArrayList<Dentist> dentists = writer.readDentistFile();

        for (Dentist dentist : dentists) {
            if (dentist.getName().equals(userName)) {
                showAlert("Error", "Dentist already exists", Alert.AlertType.ERROR);
                return;
            }
        }

        Dentist newDentist = new Dentist(userName, password);
        dentists.add(newDentist);
        writer.writeToJson(dentists);

        showAlert("Success", "Dentist created successfully", Alert.AlertType.INFORMATION);
        navigateToLogin();
    }

    private void navigateToLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginWindow.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) txtUserName.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void clicExit(MouseEvent mouseEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginWindow.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) txtUserName.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
