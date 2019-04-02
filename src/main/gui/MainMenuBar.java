package main.gui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.*;
import main.utilities.Account;
import main.utilities.Language;

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
        login = new MenuItem("Login");
        MenuItem quit = new MenuItem("Quit");

        // Text properties
        file.textProperty().bind(Language.createStringBinding("MenuBar.file"));
        login.textProperty().bind(Language.createStringBinding("MenuBar.login"));
        quit.textProperty().bind(Language.createStringBinding("MenuBar.quit"));

        // Item events
        login.setOnAction(e -> loginHandler(e));
        quit.setOnAction(e -> Platform.exit());

        // Add items to menu file
        file.getItems().add(login);
        file.getItems().add(quit);


        // Settings -> Language -> Icelandic, English
        Menu settings = new Menu("Settings");
        Menu language = new Menu("Language");
        MenuItem icelandic = new MenuItem("Icelandic");
        icelandic.setId("is");
        MenuItem english = new MenuItem("English");
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
}
