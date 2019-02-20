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
/*
public class MainController extends Application {

    public static void main(String[] args) {
        DbMain.init();
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("fxml/Main.fxml"));
        primaryStage.setTitle("Hópur D");
        primaryStage.setScene(new Scene(root, 500, 300));
        primaryStage.show();
    }

}*/

public class Main extends Application {

    // Static root for the scenes
    private static final BorderPane root = new BorderPane();

    //private static final TempDir tempDir = new TempDir();

    /**
     * Getter to access the BorderPane
     * @return BorderPane root
     */
    public static BorderPane getRoot() {
        return root;
    }

    /**
     *
     * @return
     */
   /* public static TempDir getTempDir() {
        return tempDir;
    }*/

    /**
     *
     * @param stage
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException{

        // Loading the MenuBarController
        //FXMLLoader loader = new FXMLLoader(getClass().getResource("controllers/MenuBar.fxml"));
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/main/fxml/MenuBar.fxml"));
        Parent menuBar = loader.load();
        // Get MenuBarController.fml controller
        //MenuBarController menuBarController = loader.getController();

        System.out.println("loader " + loader.getLocation());
        System.out.println("eg er menuBarContr root = " + loader.getRoot());

        // Loadig the MainMenu Pane
        //MainMenu mainMenu = new MainMenu();

        // Pass mainMenu to menuBar
        //menuBarController.setMainMenu(mainMenu);
        //mainMenu.setMenuBar(menuBarController);
        // Assign the FXML to the BorderPane(root)
        root.setTop(menuBar);
        //root.setCenter(mainMenu.getHbox());

        Parent myMenu = FXMLLoader.load(getClass().getResource("fxml/Main.fxml"));

        root.setCenter(myMenu);

        // Create a new scene with root
        Scene scene = new Scene(root, 500, 300);

        // Set the scene to the stage
        stage.setScene(scene);

        // Stage sizes
        stage.setMinWidth(500);
        stage.setMinHeight(300);

        //stage.setMaxWidth(800);
        //stage.setMaxHeight(600);

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