package main.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;

import main.gui.LoginDialog;
import main.utilities.Language;

import java.net.URL;
import java.util.ResourceBundle;

public class MenuBarController implements Initializable {
    @FXML
    public AnchorPane mainAnchor;
    @FXML
    public Menu file;
    @FXML
    public MenuItem login;
    @FXML
    public MenuItem quit;
    @FXML
    public Menu settings;
    @FXML
    public Menu language;
    @FXML
    public MenuItem is;
    @FXML
    public MenuItem en;

    @FXML
    private void quitProgram(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    private void temp(ActionEvent event) {
        System.out.println("Test item");
    }

    @FXML
    private void login(ActionEvent event) {
        LoginDialog l = new LoginDialog();
    }

    @FXML
    private void setLanguage(ActionEvent event) {
        String id = ((MenuItem) event.getSource()).getId();
        Language.setLocale(id);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Initialize all strings
        file.textProperty().bind(Language.createStringBinding("MenuBar.file"));
        login.textProperty().bind(Language.createStringBinding("MenuBar.login"));
        quit.textProperty().bind(Language.createStringBinding("MenuBar.quit"));
        settings.textProperty().bind(Language.createStringBinding("MenuBar.settings"));
        language.textProperty().bind(Language.createStringBinding("MenuBar.language"));
        is.textProperty().bind(Language.createStringBinding("MenuBar.icelandic"));
        en.textProperty().bind(Language.createStringBinding("MenuBar.english"));
    }
}
