package main;

import database.DbMain;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import main.controllers.MainController;
import main.gui.MainMenuBar;
import main.utilities.Account;
import main.utilities.Language;
import main.utilities.UTF8Control;

import java.io.IOException;
import java.util.*;

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
        ResourceBundle bundle = ResourceBundle.getBundle("languages", new UTF8Control());

        // Main
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/Main.fxml"), bundle);
        Parent myMenu = loader.load();

        MainController mainController = (MainController) loader.getController();

        // Assign the FXML to the BorderPane(root)
        MainMenuBar mb = new MainMenuBar(mainController);
        root.setTop(mb.getMainMenuBar());
        root.setCenter(myMenu);

        // Create a new scene with root
        Scene scene = new Scene(root, 800, 400);

        // Set the scene to the stage
        stage.titleProperty().bind(Language.createStringBinding("Main.title"));
        stage.setResizable(false);
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
        DbMain.init(false);
        launch(args);
    }

}