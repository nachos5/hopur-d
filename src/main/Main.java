package main;

import database.DbMain;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import main.controllers.MenuBarController;
import main.controllers.MainController;

import java.io.IOException;

public class Main extends Application {

    // Static root for the scenes
    private static final BorderPane root = new BorderPane();

    /**
     * Getter to access the BorderPane
     * @return BorderPane root
     */
    public static BorderPane getRoot() {
        return root;
    }

    /**
     *
     * @param stage
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException{

        /* Load FXML */

        // MenuBar
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/MenuBar.fxml"));
        Parent menuBar = loader.load();
        // Load controller
        MenuBarController menuBarController = loader.getController();

        // Main
        Parent myMenu = FXMLLoader.load(getClass().getResource("fxml/Main.fxml"));

        // Assign the FXML to the BorderPane(root)
        root.setTop(menuBar);
        root.setCenter(myMenu);

        // Create a new scene with root
        Scene scene = new Scene(root, 500, 300);

        // Set the scene to the stage
        stage.setScene(scene);

        // Stage sizes
        stage.setMinWidth(500);
        stage.setMinHeight(300);

        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        DbMain.init();
        launch(args);
    }

}