package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import database.DbMain;

public class Main extends Application {

    public static void main(String[] args) {
        DbMain.init();
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("fxml/main.fxml"));
        primaryStage.setTitle("Hópur D");
        primaryStage.setScene(new Scene(root, 500, 300));
        primaryStage.show();
    }

}
