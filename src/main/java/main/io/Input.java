package main.io;

import javafx.scene.control.*;
import javafx.scene.control.Dialog;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

import javafx.geometry.Insets;
import main.logic.Patient;

import java.util.Optional;

public class Input {
    private final Printer printer = new Printer();

    public String enterText(String description) {
        TextInputDialog dialog = new TextInputDialog(description.toLowerCase());
        dialog.setTitle(description + " Input Dialog");
        dialog.setHeaderText("Look, a" + description + "Input Dialog");
        dialog.setContentText("Please enter the " + description.toLowerCase() + ":");

        Optional<String> result = dialog.showAndWait();
        return result.orElse(null);
    }

    public Integer enterInteger(String description) {
        TextInputDialog dialog = new TextInputDialog(description.toLowerCase());
        dialog.setTitle(description + " Input Dialog");
        dialog.setHeaderText("Look, a" + description + "Input Dialog");
        dialog.setContentText("Please enter the " + description.toLowerCase() + ":");

        Optional<String> result = dialog.showAndWait();
        return result.map(s -> Integer.parseInt(s.trim())).orElse(0);
    }

    public Pair<Integer, Integer> enterInterval(){
        Dialog<Pair<Integer, Integer>> dialog = new Dialog<>();
        dialog.setTitle("Interval Input Dialog");
        dialog.setHeaderText("Look, a Interval Input Dialog");

        ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField firstInterval = new TextField();
        firstInterval.setPromptText("First Interval");
        TextField lastInterval = new TextField();
        lastInterval.setPromptText("Last Interval");

        grid.add(new Label("First Interval: "), 0, 0);
        grid.add(firstInterval, 1, 0);
        grid.add(new Label("Last Interval: "), 0, 1);
        grid.add(lastInterval, 1, 1);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okButtonType) {
                try {
                    int start = Integer.parseInt(firstInterval.getText());
                    int end = Integer.parseInt(lastInterval.getText());
                    return new Pair<>(start, end);
                } catch (NumberFormatException e) {
                    printer.showError("Both values must be integers.");
                }
            }
            return null;
        });

        Optional<Pair<Integer, Integer>> result = dialog.showAndWait();
        return result.orElse(null);
    }

    public Patient newPatient(){
        Dialog<Patient> dialog = new Dialog<>();
        dialog.setTitle("New Patient Dialog");
        dialog.setHeaderText("Create a new Patient");

        ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField id = new TextField();
        id.setPromptText("Id");
        TextField fullName = new TextField();
        fullName.setPromptText("Full Name");
        TextField address = new TextField();
        address.setPromptText("Address");
        TextField phoneNumber = new TextField();
        phoneNumber.setPromptText("Phone Number");
        TextField medicalRecordNumber = new TextField();
        medicalRecordNumber.setPromptText("Medical Record Number");
        TextField medicalDiagnosis = new TextField();
        medicalDiagnosis.setPromptText("Medical Diagnosis");
        TextField lastDateVisit = new TextField();
        lastDateVisit.setPromptText("Last Date Visit");
        TextField currentYearVisitCount = new TextField();
        currentYearVisitCount.setPromptText("Current Year Visit Count");

        grid.add(new Label("Id: "), 0, 0);
        grid.add(id, 1, 0);
        grid.add(new Label("Full Name"), 0, 1);
        grid.add(fullName, 1, 1);
        grid.add(new Label("Address"), 0, 2);
        grid.add(address, 1, 2);
        grid.add(new Label("Phone Number"), 0, 3);
        grid.add(phoneNumber, 1, 3);
        grid.add(new Label("Medical Record Number"), 0, 4);
        grid.add(medicalRecordNumber, 1, 4);
        grid.add(new Label("Medical Diagnosis"), 0, 5);
        grid.add(medicalDiagnosis, 1, 5);
        grid.add(new Label("Last Date Visit"), 0, 6);
        grid.add(lastDateVisit, 1, 6);
        grid.add(new Label("Current Year Visit"), 0, 7);
        grid.add(currentYearVisitCount, 1, 7);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okButtonType) {
                try {
                    return new Patient(
                            Integer.parseInt(id.getText()),
                            fullName.getText(),
                            address.getText(),
                            phoneNumber.getText(),
                            Integer.parseInt(medicalRecordNumber.getText()),
                            medicalDiagnosis.getText(),
                            lastDateVisit.getText(),
                            Integer.parseInt(currentYearVisitCount.getText())
                    );
                } catch (NumberFormatException e) {
                    printer.showError("Both values must be integers.");
                }
            }
            return null;
        });

        Optional<Patient> result = dialog.showAndWait();
        return result.orElse(null);
    }
}
