package main.controllers;

import database.TripQueries;
import database.UserQueries;
import database.models.Trip;

import database.models.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainController implements Initializable {

  @FXML
  public TableView<Trip> trip_table;
  @FXML
  public TableColumn<Trip, String> trip_table_name;
  @FXML
  public TableColumn<Trip, Integer> trip_table_price;
  @FXML
  public TextField trip_table_search;

  @FXML
  public TableView<User> user_table;
  @FXML
  public TableColumn<User, String> user_table_username;
  @FXML
  public TableColumn<User, String> user_table_email;
  @FXML
  public TextField user_table_search;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    trip_table_name.setCellValueFactory(new PropertyValueFactory<>("name"));
    trip_table_price.setCellValueFactory(new PropertyValueFactory<>("price"));

    user_table_username.setCellValueFactory(new PropertyValueFactory<>("username"));
    user_table_email.setCellValueFactory(new PropertyValueFactory<>("email"));

    // fyllum töflurnar af gögnum úr databaseinu
    user_table.setItems(user_models());
    trip_table.setItems(trip_models());

    //DbMain.close();
  }

  /**
   * sækjum array lista af öllum userum úr databaseinu og köstum yfir í observable lista -> filtered lista
   *
   * @return filtered listi af userum eftir text inputinu
   */
  private ObservableList<User> user_models() {
    ObservableList<User> users = FXCollections.observableArrayList(UserQueries.getAllUsers());

    // breytum observable listanum í filtered lista fyrir dýnamíska leit
    FilteredList<User> filtered_users = new FilteredList<>(users, s -> true);

    user_table_search.textProperty().addListener(obs -> {
      String filter = user_table_search.getText();
      if (filter == null || filter.length() == 0) {
        filtered_users.setPredicate(s -> true);
      }
      else {
        filtered_users.setPredicate(s ->
            s.getUsername().toLowerCase().contains(filter) ||
                s.getEmail().contains(filter)
        );
      }
    });

    return filtered_users;
  }

  /**
   * sækjum array lista af öllum ferðunum úr databaseinu og köstum yfir í observable lista -> filtered lista
   *
   * @return filtered listi af ferðum eftir text inputinu
   */
  private FilteredList<Trip> trip_models() {
    ObservableList<Trip> trips = FXCollections.observableArrayList(TripQueries.getAllTrips());

    // breytum observable listanum í filtered lista fyrir dýnamíska leit
    FilteredList<Trip> filtered_trips = new FilteredList<>(trips, s -> true);

    trip_table_search.textProperty().addListener(obs -> {
      String filter = trip_table_search.getText();
      if (filter == null || filter.length() == 0) {
        filtered_trips.setPredicate(s -> true);
      }
      else {
        filtered_trips.setPredicate(s ->
            s.getName().toLowerCase().contains(filter) ||
                Integer.toString(s.getPrice()).contains(filter)
        );
      }
    });

    return filtered_trips;
  }


}

