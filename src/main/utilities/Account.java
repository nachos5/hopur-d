package main.utilities;

import database.DbMain;
import database.models.User;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableStringValue;

public class Account {

    private static User currentUser;
    private static SimpleStringProperty currentUsername;
    private static final User noUser  = new User("NoUser", false, "NoUser@gmail.com","NoUserPassword");

    static {
        currentUser = noUser;
        currentUsername = new SimpleStringProperty("");
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static ObservableStringValue getCurrentUsername() {
        return currentUsername;
    }

    public static void login(String username, String password) {
        User user = DbMain.getUser(username);

        System.out.println("user = " + user);
        if (user == null) {
            currentUser = noUser;
            currentUsername.set("");
        }
        else if (Utils.checkPassword(password, user.getPassword())) {
            currentUser = user;
            currentUsername.set(user.getUsername());
        }
    }

    public static void logout() {
        currentUser = noUser;
        currentUsername.set("");
    }
}
