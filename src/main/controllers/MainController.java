package main.controllers;

import database.DbMain;
import database.models.Trip;

import database.models.User;
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
  public TableColumn<User, String> user_table_name;
  @FXML
  public TableColumn<User, String> user_table_email;
  @FXML
  public TextField user_table_search;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    trip_table_name.setCellValueFactory(new PropertyValueFactory<>("name"));
    trip_table_price.setCellValueFactory(new PropertyValueFactory<>("price"));

    user_table_name.setCellValueFactory(new PropertyValueFactory<>("name"));
    user_table_email.setCellValueFactory(new PropertyValueFactory<>("email"));

    // fyllum töflurnar af gögnum úr databaseinu
    DbMain.connect();
    trip_table.setItems(trip_models());
    user_table.setItems(user_models());
    DbMain.close();
  }

  /**
   * sækjum array lista af öllum ferðunum úr databaseinu og köstum yfir í observable lista -> filtered lista
   *
   * @return filtered listi af ferðum eftir text inputinu
   */
  private FilteredList<Trip> trip_models() {
    ObservableList<Trip> trips = FXCollections.observableArrayList(DbMain.getAllTrips());

    // breytum observable listanum í filtered lista fyrir dýnamíska leit
    FilteredList<Trip> filtered_trips = new FilteredList<>(trips, s -> true);

    trip_table_search.textProperty().addListener(obs -> {
      String filter = trip_table_search.getText();
      if (filter == null || filter.length() == 0) {
        filtered_trips.setPredicate(s -> true);
      }
      else {
        filtered_trips.setPredicate(s ->
            s.getName().toLowerCase().contains(filter) || // nafnið
            Integer.toString(s.getPrice()).contains(filter) // verðið
        );
      }
    });

    return filtered_trips;
  }

  /**
   * sækjum array lista af öllum userum úr databaseinu og köstum yfir í observable lista -> filtered lista
   *
   * @return filtered listi af userum eftir text inputinu
   */
  private ObservableList<User> user_models() {
    ObservableList<User> users = FXCollections.observableArrayList(DbMain.getAllUsers());

    // breytum observable listanum í filtered lista fyrir dýnamíska leit
    FilteredList<User> filtered_users = new FilteredList<>(users, s -> true);

    user_table_search.textProperty().addListener(obs -> {
      String filter = user_table_search.getText();
      if (filter == null || filter.length() == 0) {
        filtered_users.setPredicate(s -> true);
      }
      else {
        filtered_users.setPredicate(s ->
            s.getName().toLowerCase().contains(filter) || // nafnið
            s.getEmail().contains(filter) // verðið
        );
      }
    });

    return filtered_users;
  }


}
