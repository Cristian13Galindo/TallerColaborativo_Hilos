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
import javafx.stage.Stage;

import java.io.IOException;


public class LoginWindowController {


    public TextField txtUserName;
    public PasswordField txtPassword;
    public Button btnLogin;

    public void initialize() {
        btnLogin.setCursor(Cursor.HAND);
        txtUserName.setOnKeyPressed(this::handleEnterKeyPressed);
        txtPassword.setOnKeyPressed(this::handleEnterKeyPressed);
    }

    private void handleEnterKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            clicLogin();
        }
    }

    public void clicHelp(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("SaludDigital 1.0.0");
        alert.setHeaderText("¡Para cualquier eventualidad comuniquese con soporte! \n" +
                "Canales de comunicaion: \n" +
                "\n" +
                "cristian.galindo05@uptc.edu.co \n" +
                "GitHub: Cristian13Galindo \n" +
                "Linkedin: www.linkedin.com/in/cristian-camilo-galindo-suárez-90049b24b \n" +
                "\n" +
                "Esperamos tu comunicacón :=) \n" +
                "Equipo de desarrollo de SaludDigital\n");
        alert.showAndWait();
    }

    public void clicLogin(ActionEvent actionEvent) {
        clicLogin();
    }

    private void clicLogin() {
        String userName = txtUserName.getText().trim();
        String password = txtPassword.getText().trim();

        if (userName.isEmpty() || password.isEmpty()) {
            showAlert("Campos vacíos", "Por favor, ingrese ambos campos: usuario y contraseña.", Alert.AlertType.WARNING);
            return;
        }

        UserPersistence userPersistence = new UserPersistence();

        if (userName.equals("admin") && password.equals("123")) {
            navigateToCreateDentist();
        } else {
            Dentist dentist = userPersistence.getDentistFromJson(userName);
            if (dentist != null && dentist.getPassword().equals(password)) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("MenuUserDentistWindow.fxml"));
                    Parent root = loader.load();
                    Stage stage = (Stage) txtUserName.getScene().getWindow();
                    stage.setScene(new Scene(root));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                showAlert("Error", "Nombre de usuario o contraseña incorrectos.", Alert.AlertType.ERROR);
            }
        }
    }

    private void navigateToCreateDentist() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CreateNewDentistWindow.fxml"));
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
