package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("main.fxml"));
        Scene scene = new Scene(loader.load(), 1200, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Patient Management");
        primaryStage.show();
    }
}
