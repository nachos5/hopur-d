package hopurd.main.gui;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;
import hopurd.main.utilities.Account;
import hopurd.main.utilities.Language;
import hopurd.models.User;

import java.util.Optional;

public class LoginDialog extends Dialog {

    private Dialog<Pair<String, String>> login;

    // For error message display
    private Optional<Pair<String, String>> result;
    private TextField username;
    private PasswordField password;

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
        ButtonType okButtonType = new ButtonType("Login", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButtonType = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        // Add buttons to dialog
        login.getDialogPane().getButtonTypes().addAll(okButtonType, cancelButtonType);

        // Set buttons properties and disable loginButton by default
        Button loginButton = (Button) login.getDialogPane().lookupButton(okButtonType);
        loginButton.textProperty().bind(Language.createStringBinding("LoginDialog.login"));
        loginButton.setDisable(true);

        Button cancelButton = (Button) login.getDialogPane().lookupButton(cancelButtonType);
        cancelButton.textProperty().bind(Language.createStringBinding("LoginDialog.cancel"));

        // Create the username and password labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        // Textfields
        username = new TextField();;
        username.promptTextProperty().bind(Language.createStringBinding("LoginDialog.usernamePrompt"));
        password = new PasswordField();
        password.promptTextProperty().bind(Language.createStringBinding("LoginDialog.passwordPrompt"));

        // Labels
        Label usernameLabel = new Label();
        usernameLabel.textProperty().bind(Language.createStringBinding("LoginDialog.username"));
        Label passwordLabel = new Label();
        passwordLabel.textProperty().bind(Language.createStringBinding("LoginDialog.password"));

        // Add Labels and textfields to grid
        grid.add(usernameLabel, 0, 0);
        grid.add(username, 1, 0);
        grid.add(passwordLabel, 0, 1);
        grid.add(password, 1, 1);

        // Textfield username, event listener, if username and password are not empty, enable ok button
        username.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(newValue.trim().isEmpty() | password.getText().isEmpty());
        });

        // Textfield password, event listener, if username and password are not empty, enable ok button
        password.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(newValue.trim().isEmpty() | username.getText().isEmpty());
        });

        // Set grid into dialogpane
        login.getDialogPane().setContent(grid);

        // Request focus on the username field by default.
        Platform.runLater(() -> username.requestFocus());

        // Convert the result to a username-password-pair when the login button is clicked.
        login.setResultConverter(dialogButton -> {
            if (dialogButton == okButtonType) {
                return new Pair<>(username.getText(), password.getText());
            }
            return null;
        });
    }

    public void start() {
        // Show dialog and capture values
        result = login.showAndWait();

        // Try to login user
        result.ifPresent(usernamePassword -> {
            User before = Account.getCurrentUser(); // Previous user
            Account.login(usernamePassword.getKey(), usernamePassword.getValue()); // Login in user
            User after = Account.getCurrentUser(); // New user

            // If new user wasn't logged in display error
            if (before == after) this.error();
        });
    }

    private void error() {
        // Reset results
        login.setResult(null);
        // Empty textfields
        username.setText("");
        password.setText("");

        // Red background on textfields
        username.setStyle("-fx-control-inner-background: #cc0000;");
        password.setStyle("-fx-control-inner-background: #cc0000;");
        this.start();
    }

}
