package main.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;

import main.controllers.LoginDialog;

public class MenuBarController {
    @FXML
    public AnchorPane mainAnchor;
    @FXML
    public Menu fileItem;
    @FXML
    public MenuItem loginItem;
    @FXML
    public MenuItem saveItem;
    @FXML
    public MenuItem quitItem;
    @FXML
    public Menu settingsItem;
    @FXML
    public Menu languageItem;
    @FXML
    public MenuItem icelandic;
    @FXML
    public MenuItem english;

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
}
