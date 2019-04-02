package main.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.utilities.Language;
import models.Enums;
import models.Trip;

import java.net.URL;
import java.util.ResourceBundle;

public class NewTripController implements Initializable {

    @FXML
    Label nameLabel;
    @FXML
    TextField nameTextField;

    @FXML
    Label categoryLabel;
    @FXML
    ComboBox categoryComboBox;

    @FXML
    Label priceLabel;
    @FXML
    TextField priceTextField;

    @FXML
    Label durationLabel;
    @FXML
    TextField durationTextField;

    @FXML
    Label groupSizeLabel;
    @FXML
    TextField groupSizeTextField;

    @FXML
    Label countryLabel;
    @FXML
    TextField countryTextField;

    @FXML
    Label cityLabel;
    @FXML
    TextField cityTextField;

    @FXML
    Label accessabilityLabel;
    @FXML
    TextField accessabilityTextField;

    @FXML
    Label languageLabel;
    @FXML
    TextField languageTextField;

    @FXML
    Label sustainableLabel;
    @FXML
    TextField sustainableTextField;

    @FXML
    Label descriptionLabel;
    @FXML
    TextField descriptionTextField;

    @FXML
    Label companyLabel;
    @FXML
    TextField companyTextField;

    @FXML
    Label reviewsLabel;
    @FXML
    TextField reviewsTextField;

    @FXML
    Button cancelButton;
    @FXML
    Button okButton;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Labels
        nameLabel.textProperty().bind(Language.createStringBinding("NewTripController.name"));
        categoryLabel.textProperty().bind(Language.createStringBinding("NewTripController.category"));
        priceLabel.textProperty().bind(Language.createStringBinding("NewTripController.price"));
        durationLabel.textProperty().bind(Language.createStringBinding("NewTripController.duration"));
        groupSizeLabel.textProperty().bind(Language.createStringBinding("NewTripController.groupSize"));
        countryLabel.textProperty().bind(Language.createStringBinding("NewTripController.country"));
        cityLabel.textProperty().bind(Language.createStringBinding("NewTripController.city"));
        accessabilityLabel.textProperty().bind(Language.createStringBinding("NewTripController.accessability"));
        languageLabel.textProperty().bind(Language.createStringBinding("NewTripController.language"));
        sustainableLabel.textProperty().bind(Language.createStringBinding("NewTripController.sustainable"));
        descriptionLabel.textProperty().bind(Language.createStringBinding("NewTripController.description"));
        companyLabel.textProperty().bind(Language.createStringBinding("NewTripController.company"));
        reviewsLabel.textProperty().bind(Language.createStringBinding("NewTripController.reviews"));

        // Button
        cancelButton.textProperty().bind(Language.createStringBinding("NewTripController.cancelButton"));
        okButton.textProperty().bind(Language.createStringBinding("NewTripController.okButton"));

    }

    public void cancelHandler(ActionEvent event) {
        closeStage(event);
    }

    public void okHandler(ActionEvent event) {
       /* Trip newTrip = new Trip(
                nameTextField.getText().trim(),                                 // String
                categoryTextField.getText().trim(),                             // Enum
                Integer.parseInt(priceTextField.getText().trim()),              // int
                Integer.parseInt(durationTextField.getText().trim()),           // int
                Integer.parseInt(groupSizeTextField.getText().trim()),          // int
                countryTextField.getText().trim(),                              // Enum
                cityTextField.getText().trim(),             // Enum
                accessabilityTextField.getText().trim(),    // Enum
                languageTextField.getText().trim(),         // Enum
                sustainableTextField.getText().trim(),      // Boolean
                descriptionTextField.getText().trim(),      // String
                companyTextField.getText().trim(),          // Company
        );*/
    }

    private void closeStage(ActionEvent event) {
        Node source = (Node)  event.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }
}

