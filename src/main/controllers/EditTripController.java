package main.controllers;

import database.CompanyQueries;
import database.TripQueries;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import models.Company;
import models.Enums;
import models.Trip;
import main.utilities.Language;
import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;

public class EditTripController implements Initializable {

    @FXML
    Label nameLabel;
    @FXML
    TextField nameTextField;

    @FXML
    ComboBox categoryComboBox;

    @FXML
    Label priceLabel;
    @FXML
    Label priceLabelValue;
    @FXML
    Slider priceSlider;

    @FXML
    Label durationLabel;
    @FXML
    Label durationLabelValue;
    @FXML
    Slider durationSlider;

    @FXML
    Label groupSizeLabel;
    @FXML
    Label groupSizeLabelValue;
    @FXML
    Slider groupSizeSlider;

    @FXML
    ComboBox countryComboBox;

    @FXML
    ComboBox cityComboBox;

    @FXML
    ComboBox accessabilityComboBox;

    @FXML
    ComboBox languageComboBox;

    @FXML
    HBox sustainableHBox;
    @FXML
    ToggleGroup sustainableToggleGroup;
    @FXML
    RadioButton sustainableTrue;
    @FXML
    RadioButton sustainableFalse;

    @FXML
    Label descriptionLabel;
    @FXML
    TextArea descriptionTextArea;

    @FXML
    ComboBox companyComboBox;

    @FXML
    Button cancelButton;
    @FXML
    Button okButton;

    private Trip editTrip;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Labels
        nameLabel.textProperty().bind(Language.createStringBinding("NewTripController.name"));
        descriptionLabel.textProperty().bind(Language.createStringBinding("NewTripController.description"));

        // Sliders
        priceLabel.textProperty().bind(Language.createStringBinding("NewTripController.price"));
        durationLabel.textProperty().bind(Language.createStringBinding("NewTripController.duration"));
        groupSizeLabel.textProperty().bind(Language.createStringBinding("NewTripController.groupSize"));

        // Buttons
        cancelButton.textProperty().bind(Language.createStringBinding("NewTripController.cancelButton"));
        okButton.textProperty().bind(Language.createStringBinding("NewTripController.okButton"));

        // Comboboxes
        categoryComboBox.promptTextProperty().bind(Language.createStringBinding("NewTripController.category")); // Enum
        countryComboBox.promptTextProperty().bind(Language.createStringBinding("NewTripController.country")); // Enum
        cityComboBox.promptTextProperty().bind(Language.createStringBinding("NewTripController.city")); // Enum
        accessabilityComboBox.promptTextProperty().bind(Language.createStringBinding("NewTripController.accessability")); // Enum
        languageComboBox.promptTextProperty().bind(Language.createStringBinding("NewTripController.language")); // Enum
        companyComboBox.promptTextProperty().bind(Language.createStringBinding("NewTripController.company")); // Company

        // Radiobuttons
        sustainableTrue.textProperty().bind(Language.createStringBinding("NewTripController.sustainableTrue")); // Boolean
        sustainableFalse.textProperty().bind(Language.createStringBinding("NewTripController.sustainableFalse")); // Boolean

        populateComboBoxes();
        setupSliders();
        setupBindings();

    }

    public void setTrip(Trip editTrip) {
        this.editTrip = editTrip;

        // Textfields
        nameTextField.setText(editTrip.getName());
        // Textareas
        descriptionTextArea.setText(editTrip.getDescription());

        // Radiobutton
        if (editTrip.isSustainable()) sustainableTrue.setSelected(true);
        else sustainableFalse.setSelected(true);

        // Sliders
        priceLabelValue.setText(editTrip.getPrice() + "");
        priceSlider.setValue(editTrip.getPrice());

        durationLabelValue.setText(editTrip.getDuration() + "");
        durationSlider.setValue(editTrip.getDuration());

        groupSizeLabelValue.setText(editTrip.getGroupSize() + "");
        groupSizeSlider.setValue(editTrip.getGroupSize());

        // Comboboxes
        categoryComboBox.getSelectionModel().select(editTrip.getCategory());
        countryComboBox.getSelectionModel().select(editTrip.getCountry());
        cityComboBox.getSelectionModel().select(editTrip.getCity());
        accessabilityComboBox.getSelectionModel().select(editTrip.getAccessability());
        languageComboBox.getSelectionModel().select(editTrip.getLanguage());
        companyComboBox.getSelectionModel().select(editTrip.getCompany().getName());

    }

    private void setupSliders() {
        // Price - update label
        priceSlider.valueProperty().addListener((ov, old_val, new_val) -> {
            priceLabelValue.setText(String.format("%.0f", new_val));
        });

        // Duration - update label
        durationSlider.valueProperty().addListener((ov, old_val, new_val) -> {
            durationLabelValue.setText(String.format("%.0f", new_val));
        });

        // GroupSize - update label
        groupSizeSlider.valueProperty().addListener((ov, old_val, new_val) -> {
            groupSizeLabelValue.setText(String.format("%.0f", new_val));
        });
    }

    private void populateComboBoxes() {
        // Country
        for (Enum country : Enums.Country.values()) {
            countryComboBox.getItems().add(country);
        }

        // Language
        for (Enum language : Enums.Language.values()) {
            languageComboBox.getItems().add(language);
        }

        // Company
        Iterator<Company> companyIterator = CompanyQueries.getAllCompanies().iterator();

        while (companyIterator.hasNext()) {
            companyComboBox.getItems().add(companyIterator.next().getName());
        }

        // Category
        for (Enum category : Enums.Category.values()) {
            categoryComboBox.getItems().add(category);
        }

        // Accessability
        for (Enum accessability : Enums.Accessability.values()) {
            accessabilityComboBox.getItems().add(accessability);
        }

        // City
        for (Enum city : Enums.City.values()) {
            cityComboBox.getItems().add(city);
        }
    }

    private void setupBindings() {
        // Require all fields to have a value before submition
        BooleanBinding disableButton = nameTextField.textProperty().isEmpty()
                .or(categoryComboBox.valueProperty().isNull()
                .or(priceSlider.valueProperty().isEqualTo(0)
                .or(durationSlider.valueProperty().isEqualTo(0)
                .or(groupSizeSlider.valueProperty().isEqualTo(0)
                .or(countryComboBox.valueProperty().isNull()
                .or(cityComboBox.valueProperty().isNull()
                .or(accessabilityComboBox.valueProperty().isNull()
                .or(languageComboBox.valueProperty().isNull()
                .or(sustainableToggleGroup.selectedToggleProperty().isNull()
                .or(descriptionTextArea.textProperty().isEmpty()
                .or(companyComboBox.valueProperty().isNull()
                )))))))))));

        okButton.disableProperty().bind(disableButton);
    }

    public void cancelHandler(ActionEvent event) {
        closeStage(event);
    }

    public void okHandler(ActionEvent event) {
        // Get selected radiobutton
        RadioButton selectedRadioButton = (RadioButton) sustainableToggleGroup.getSelectedToggle();

        // Translate radiobutton to boolean
        Boolean sustainable = false;
        if (selectedRadioButton.getId() == sustainableTrue.getId()) sustainable = true;

        // Construct a new trip
        Trip newTrip = new Trip(
                nameTextField.getText().trim(),                                         // String
                (Enums.Category) categoryComboBox.getValue(),                           // Enum
                (int) priceSlider.getValue(),                                           // int
                (int) durationSlider.getValue(),                                        // int
                (int) groupSizeSlider.getValue(),                                       // int
                (Enums.Country) countryComboBox.getValue(),                             // Enum
                (Enums.City) cityComboBox.getValue(),                                   // Enum
                (Enums.Accessability) accessabilityComboBox.getValue(),                 // Enum
                (Enums.Language) languageComboBox.getValue(),                           // Enum
                sustainable,                                                            // Boolean
                descriptionTextArea.getText().trim(),                                   // String
                CompanyQueries.getCompanyByName(companyComboBox.getValue().toString())  // Company
        );

        // Insert newTrip into database
        //TripQueries.insertTrip(newTrip);

        // Close dialog
        closeStage(event);
    }

    private void closeStage(ActionEvent event) {
        Node source = (Node)  event.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }
}

