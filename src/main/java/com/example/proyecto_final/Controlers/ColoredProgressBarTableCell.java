package com.example.proyecto_final.Controlers;

import com.example.proyecto_final.Model.Pacient;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableCell;

public class ColoredProgressBarTableCell extends TableCell<Pacient, Double> {
    private final ProgressBar progressBar = new ProgressBar(0);

    @Override
    protected void updateItem(Double progress, boolean empty) {
        super.updateItem(progress, empty);
        if (empty || progress == null) {
            setGraphic(null);
        } else {
            progressBar.setProgress(progress);
            Pacient patient = getTableView().getItems().get(getIndex());
            progressBar.setStyle("-fx-accent: " + getColorForPriority(patient.getPriority()) + ";");
            setGraphic(progressBar);
        }
    }

    private String getColorForPriority(int priority) {
        switch (priority) {
            case 1:
                return "red";
            case 2:
                return "orange";
            case 3:
                return "yellow";
            default:
                return "white";
        }
    }
}