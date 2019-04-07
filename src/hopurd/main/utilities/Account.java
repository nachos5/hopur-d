package hopurd.main.utilities;

import hopurd.database.UserQueries;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableBooleanValue;
import hopurd.models.User;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableStringValue;

public class Account {

    private static User currentUser;
    private static SimpleStringProperty currentUsername;
    private static SimpleBooleanProperty isNoUser;
    private static SimpleBooleanProperty isNotAdmin;
    private static final User noUser  = new User("NoUser", false, "NoUser@gmail.com","NoUserPassword");

    static {
        currentUser = noUser;
        currentUsername = new SimpleStringProperty("");
        isNoUser = new SimpleBooleanProperty(true);
        isNotAdmin = new SimpleBooleanProperty(true);
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static Boolean isLoggedIn() {
        return currentUser != noUser;
    }

    public static ObservableBooleanValue isLoggedInObservable() {
        return isNoUser;
    }

    public static ObservableBooleanValue isAdminObservable() {
        return isNotAdmin;
    }

    public static ObservableStringValue getCurrentUsername() {
        return currentUsername;
    }

    public static void login(String username, String password) {
        User user = UserQueries.getUser(username);

        if (user == null) return;
        if (Utils.checkPassword(password, user.getPassword())) {
            currentUser = user;
            currentUsername.set(user.getUsername());
            isNoUser.set(false);

            // Set admin privileges to true
            if (user.isAdmin()) isNotAdmin.set(false);
        }
    }

    public static void logout() {
        currentUser = noUser;
        currentUsername.set("");
        isNoUser.set(true);
        isNotAdmin.set(true);
    }
}
