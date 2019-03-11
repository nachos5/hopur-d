package main.gui;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;
import main.utilities.Account;
import main.utilities.Language;

import java.util.Optional;

public class LoginDialog extends Dialog {

    private Dialog<Pair<String, String>> login;

    public LoginDialog() {
        login = new Dialog<>();

        setup();
    }

    public Dialog getLogin() {
        return login;
    }

    private void setup() {
        // Titles
        login.titleProperty().bind(Language.createStringBinding("LoginDialog.title"));
        login.headerTextProperty().bind(Language.createStringBinding("LoginDialog.header"));

        // Image
        login.setGraphic(new ImageView(this.getClass().getResource("/login.png").toString()));

        // Set the button types.
        ButtonType loginButtonType = new ButtonType("Login", ButtonBar.ButtonData.OK_DONE);
        login.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        // Create the username and password labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField username = new TextField();
        username.setPromptText("Username");
        PasswordField password = new PasswordField();
        password.setPromptText("Password");

        grid.add(new Label("Username:"), 0, 0);
        grid.add(username, 1, 0);
        grid.add(new Label("Password:"), 0, 1);
        grid.add(password, 1, 1);



        // Enable/Disable login button depending on whether a username was entered.
        Node loginButton = login.getDialogPane().lookupButton(loginButtonType);
        loginButton.setDisable(true);

        // Do some validation (using the Java 8 lambda syntax).
        username.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(newValue.trim().isEmpty());
        });

        login.getDialogPane().setContent(grid);

        // Request focus on the username field by default.
        Platform.runLater(() -> username.requestFocus());

        // Convert the result to a username-password-pair when the login button is clicked.
        login.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return new Pair<>(username.getText(), password.getText());
            }
            return null;
        });

        // Show and capture values
        Optional<Pair<String, String>> result = login.showAndWait();

        // Trigger
        result.ifPresent(usernamePassword -> {
            String username1 = usernamePassword.getKey();
            String password1 = usernamePassword.getValue();
            System.out.println("Username=" + usernamePassword.getKey() + ", Password=" + usernamePassword.getValue());

            Account.login(username1, password1);

        });
    }

}
