package main.io;

import javafx.scene.control.Alert;

public class Printer {
    public void showError(String description) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Input Error");
        alert.setHeaderText(null);
        alert.setContentText(description);
        alert.showAndWait();
    }

    public void showText(String title, String description) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(description);
        alert.showAndWait();
    }

    public void successWriteToFile() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Writing Success");
        alert.setHeaderText(null);
        alert.setContentText("The data was successfully written to the file");
        alert.showAndWait();
    }

    public void successReadFromFile() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Reading Success");
        alert.setHeaderText(null);
        alert.setContentText("The data was successfully read from the file");
        alert.showAndWait();
    }
}
