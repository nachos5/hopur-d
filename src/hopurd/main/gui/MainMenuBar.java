package hopurd.main.gui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.*;
import java.io.IOException;
import java.util.ResourceBundle;

import javafx.stage.Modality;
import javafx.stage.Stage;
import hopurd.main.utilities.Account;
import hopurd.main.utilities.Language;
import hopurd.main.utilities.UTF8Control;

public class MainMenuBar extends AnchorPane {

    private HBox menuBars;
    private MenuBar leftMenuBar;
    private MenuBar rightMenuBar;

    private MenuItem login;

    public MainMenuBar() {
        leftMenuBar = new MenuBar();
        rightMenuBar = new MenuBar();
        setup();

        HBox.setHgrow(leftMenuBar, Priority.ALWAYS);
        menuBars = new HBox(leftMenuBar, rightMenuBar);
    }

    public HBox getMainMenuBar() {
        return menuBars;
    }

    private void setup() {

        // File -> Login, Quit
        Menu file = new Menu();
        Menu menuNew = new Menu();

        MenuItem trip = new MenuItem();
        MenuItem review = new MenuItem();
        login = new MenuItem();
        MenuItem quit = new MenuItem();

        //Add items to menuNew
        menuNew.getItems().add(trip);
        menuNew.getItems().add(review);

        // Bindings
        trip.disableProperty().bind(Account.isAdminObservable());
        review.disableProperty().bind(Account.isLoggedInObservable());

        // Text properties
        file.textProperty().bind(Language.createStringBinding("MenuBar.file"));
        menuNew.textProperty().bind(Language.createStringBinding("MenuBar.new"));
        trip.textProperty().bind(Language.createStringBinding("MenuBar.trip"));
        review.textProperty().bind(Language.createStringBinding("MenuBar.review"));
        login.textProperty().bind(Language.createStringBinding("MenuBar.login"));
        quit.textProperty().bind(Language.createStringBinding("MenuBar.quit"));

        // Item events
        trip.setOnAction((e) -> {
            try {
                newTripHandler(e);
            } catch(IOException err) {
                System.err.println("Menubar new -> trip error: " + err);
            }
        });
        review.setOnAction((e) -> {
            try {
                newReviewHandler(e);
            } catch(IOException err) {
                System.err.println("Menubar new -> review error: " + err);
            }
        });
        login.setOnAction(e -> loginHandler(e));
        quit.setOnAction(e -> Platform.exit());

        // Add items to menu file
        file.getItems().add(menuNew);
        file.getItems().add(login);
        file.getItems().add(quit);


        // Settings -> Language -> Icelandic, English
        Menu settings = new Menu();
        Menu language = new Menu();
        MenuItem icelandic = new MenuItem();
        icelandic.setId("is");
        MenuItem english = new MenuItem();
        english.setId("en");

        // Text properties
        settings.textProperty().bind(Language.createStringBinding("MenuBar.settings"));
        language.textProperty().bind(Language.createStringBinding("MenuBar.language"));
        icelandic.textProperty().bind(Language.createStringBinding("MenuBar.icelandic"));
        english.textProperty().bind(Language.createStringBinding("MenuBar.english"));

        // Item events
        icelandic.setOnAction(e -> languageHandler(e));
        english.setOnAction(e -> languageHandler(e));

        // Add items to menu language
        language.getItems().add(icelandic);
        language.getItems().add(english);

        // Add menu language to menu settings
        settings.getItems().add(language);

        // Add menus to left menubar
        leftMenuBar.getMenus().add(file);
        leftMenuBar.getMenus().add(settings);

        // Right menubar

        // User label
        Menu user = new Menu();
        user.textProperty().bind(Account.getCurrentUsername());

        // Add menu to right menubar
        rightMenuBar.getMenus().add(user);
    }

    private void loginHandler(ActionEvent event) {
        // Logout logic
        if (Account.isLoggedIn()) {
            Account.logout();
            login.textProperty().bind(Language.createStringBinding("MenuBar.login"));
        }
        // Login logic
        else {
            LoginDialog loginDialog = new LoginDialog();
            loginDialog.start();

            // If login succeeded change to logout
            if (Account.isLoggedIn()) {
                login.textProperty().bind(Language.createStringBinding("MenuBar.logout"));
            }
        }
    }

    private void languageHandler(ActionEvent event) {
        String id = ((MenuItem) event.getSource()).getId();
        Language.setLocale(id);
    }

    private void newTripHandler(ActionEvent event) throws IOException {
        ResourceBundle bundle = ResourceBundle.getBundle("languages", new UTF8Control());
        Parent newTrip = FXMLLoader.load(getClass().getResource("../fxml/NewTrip.fxml"), bundle);

        // Set dialog
        Scene scene = new Scene(newTrip, 550, 750);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.titleProperty().bind(Language.createStringBinding("NewTripController.title"));
        stage.setResizable(false);
        stage.setScene(scene);
        stage.showAndWait();
    }

    private void newReviewHandler(ActionEvent event) throws IOException {
        ResourceBundle bundle = ResourceBundle.getBundle("languages", new UTF8Control());
        Parent newReview = FXMLLoader.load(getClass().getResource("../fxml/NewReview.fxml"), bundle);

        // Set dialog
        Scene scene = new Scene(newReview, 400, 350);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.titleProperty().bind(Language.createStringBinding("NewReviewController.title"));
        stage.setResizable(false);
        stage.setScene(scene);
        stage.showAndWait();
    }
}
