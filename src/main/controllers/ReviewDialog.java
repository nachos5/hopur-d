package main.controllers;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

public class ReviewDialog extends Dialog {

  private Dialog<String> review_dialog;

  ReviewDialog() {
     review_dialog = new Dialog<>();

    setup();
  }

  public Dialog getReviewDialog() {
    return review_dialog;
  }

  private void setup() {
    // Titles
    review_dialog.setTitle("Login");
    review_dialog.setHeaderText("Please log in");

    // Create the username and password labels and fields.
    GridPane grid = new GridPane();
    grid.setHgap(10);
    grid.setVgap(10);
    grid.setPadding(new Insets(20, 150, 10, 10));

    review_dialog.getDialogPane().setContent(grid);

  }

}
