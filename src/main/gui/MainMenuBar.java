package main.gui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import main.utilities.Language;

public class MainMenuBar extends AnchorPane {

    private VBox vb;
    private MenuBar mb;

    public MainMenuBar() {
        mb = new MenuBar();
        vb = new VBox(mb);
        setup();
    }

    public VBox getMainMenuBar() {
        return vb;
    }

    private void setup() {

        // File -> Login, Quit
        Menu file = new Menu();
        MenuItem login = new MenuItem("Login");
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

        mb.getMenus().add(file);
        mb.getMenus().add(settings);
    }

    private void loginHandler(ActionEvent event) {
        LoginDialog l = new LoginDialog();
    }

    private void languageHandler(ActionEvent event) {
        String id = ((MenuItem) event.getSource()).getId();
        Language.setLocale(id);
    }
}
