package hopurd.main.controllers;

import hopurd.database.ReviewQueries;
import hopurd.database.TripQueries;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import hopurd.main.utilities.Account;
import hopurd.main.utilities.Language;
import hopurd.models.Review;
import hopurd.models.Trip;

import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;

public class NewReviewController implements Initializable {
    @FXML
    Label titleLabel;
    @FXML
    TextField titleTextField;

    @FXML
    ComboBox tripComboBox;

    @FXML
    Label ratingLabelValue;
    @FXML
    Label ratingLabel;
    @FXML
    Slider ratingSlider;

    @FXML
    HBox isPublicHBox;
    @FXML
    ToggleGroup isPublicToggleGroup;
    @FXML
    RadioButton isPublicTrue;
    @FXML
    RadioButton isPublicFalse;

    @FXML
    Label descriptionLabel;
    @FXML
    TextArea descriptionTextArea;

    @FXML
    Button cancelButton;
    @FXML
    Button okButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Labels
        titleLabel.textProperty().bind(Language.createStringBinding("NewReviewController.name"));
        descriptionLabel.textProperty().bind(Language.createStringBinding("NewReviewController.description"));

        // Comboboxes
        tripComboBox.promptTextProperty().bind(Language.createStringBinding("NewReviewController.trip"));

        // Sliders
        ratingLabel.textProperty().bind(Language.createStringBinding("NewReviewController.rating"));
        ratingLabelValue.setText(String.format("%.0f", ratingSlider.getValue()));

        // Buttons
        cancelButton.textProperty().bind(Language.createStringBinding("NewReviewController.cancelButton"));
        okButton.textProperty().bind(Language.createStringBinding("NewReviewController.okButton"));

        // Radiobuttons
        isPublicFalse.textProperty().bind(Language.createStringBinding("NewReviewController.isPublicFalse"));
        isPublicTrue.textProperty().bind(Language.createStringBinding("NewReviewController.isPublicTrue"));

        populateComboBoxes();
        setupSliders();
        setupBindings();
    }

    private void populateComboBoxes() {
        // Trip
        Iterator<Trip> tripIterator = TripQueries.getAllTrips().iterator();

        while (tripIterator.hasNext()) {
            tripComboBox.getItems().add(tripIterator.next().getName());
        }
    }

    private void setupSliders() {
        // Rating - update label
        ratingSlider.valueProperty().addListener((ov, old_val, new_val) -> {
            ratingLabelValue.setText(String.format("%.0f", new_val));
        });
    }

    private void setupBindings() {
        // Require all fields to have a value before submition
        BooleanBinding disableButton = titleTextField.textProperty().isEmpty()
                .or(isPublicToggleGroup.selectedToggleProperty().isNull()
                .or(descriptionTextArea.textProperty().isEmpty()
                .or(tripComboBox.valueProperty().isNull()
                )));

        okButton.disableProperty().bind(disableButton);
    }


    public void cancelHandler(ActionEvent event) {
        closeStage(event);
    }

    public void okHandler(ActionEvent event) {
        // Get selected radiobutton
        RadioButton selectedRadioButton = (RadioButton) isPublicToggleGroup.getSelectedToggle();

        // Translate radiobutton to boolean
        Boolean isPublic = false;
        if (selectedRadioButton.getId() == isPublicTrue.getId()) isPublic = true;

        // Construct a new review
        Review newReview = new Review(
                Account.getCurrentUser(),
                TripQueries.getTripByName(tripComboBox.getValue().toString()),
                titleTextField.getText().trim(),
                descriptionTextArea.getText().trim(),
                ratingSlider.getValue(),
                isPublic
        );

        // Insert newReview into db
        ReviewQueries.insertReview(newReview);

        // Close dialog
        closeStage(event);
    }

    private void closeStage(ActionEvent event) {
        Node source = (Node)  event.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
