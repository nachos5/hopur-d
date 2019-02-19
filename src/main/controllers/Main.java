package main.controllers;

import database.DbMain;
import database.models.Trip;

import database.models.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class Main implements Initializable {

  @FXML
  public TableView<Trip> trip_table;
  @FXML
  public TableColumn<Trip, String> trip_table_name;
  @FXML
  public TableColumn<Trip, Integer> trip_table_price;

  @FXML
  public TableView<User> user_table;
  @FXML
  public TableColumn<User, String> user_table_name;
  @FXML
  public TableColumn<User, String> user_table_email;

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

  // sækjum array lista af öllum ferðunum úr databaseinu og köstum yfir í observable lista
  private static ObservableList<Trip> trip_models() {
    return FXCollections.observableArrayList(DbMain.getAllTrips());
  }

  private static ObservableList<User> user_models() {
    return FXCollections.observableArrayList(DbMain.getAllUsers());
  }


}

