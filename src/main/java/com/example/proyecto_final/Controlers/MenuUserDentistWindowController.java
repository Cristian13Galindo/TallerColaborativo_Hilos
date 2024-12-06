package com.example.proyecto_final.Controlers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuUserDentistWindowController {


    public Button btnExit;
    public Button btnCreateNewPacient;
    public Button btnConsultPacient;

    public void initialize() {
        btnCreateNewPacient.setCursor(Cursor.HAND);
        btnConsultPacient.setCursor(Cursor.HAND);
        btnExit.setCursor(Cursor.HAND);
    }

    public void clicExit(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("LoginWindow.fxml"));
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

    public void clicCreateNewPacient(ActionEvent mouseEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CreateNewPacientWindow.fxml"));
            Parent root = fxmlLoader.load();

            Scene scene = new Scene(root);
            Stage currentStage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
            currentStage.setScene(scene);
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void clicConsultPacient(ActionEvent mouseEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ConsultPacientWindow.fxml"));
            Parent root = fxmlLoader.load();

            Scene scene = new Scene(root);

            Stage currentStage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
            currentStage.setScene(scene);

            currentStage.show();

            Stage menuStage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
            menuStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
