package hopurd.main;

import hopurd.database.DbMain;
import hopurd.main.gui.MainMenuBar;
import hopurd.main.utilities.Language;
import hopurd.models.Trip;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import hopurd.main.utilities.UTF8Control;

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
        Parent myMenu = FXMLLoader.load(getClass().getResource("fxml/Main.fxml"), bundle);

        // Assign the FXML to the BorderPane(root)
        MainMenuBar mb = new MainMenuBar();
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

//        ArrayList<Trip> trips = API.getTripsBySearchQueries("", "", 0, 0, 0, 0, 0, 0, "", "", "", "", "");
//        for (Trip trip: trips) {
//            System.out.println(trip.getName());
//        }
//
        //BookingExample.BookingExampleStatic();

        launch(args);
    }

}