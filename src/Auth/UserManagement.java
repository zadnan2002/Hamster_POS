package Auth;

import javax.naming.AuthenticationException;
import java.util.HashMap;
import java.util.Map;

public class UserManagement {
    private static final Map<String, String> userDatabase = new HashMap<>();
    private User currentUser = null;


    public static boolean registerUser(String username, String password) {
        if (!userDatabase.containsKey(username)) {
            userDatabase.put(username, User.hashPassword(password));
            return true;
        }
        return false;
    }

    public boolean isAuthenticated() {
        String storedPassword = userDatabase.get(currentUser.getUsername());
        String hashedPassword = currentUser.getPassword();
        return storedPassword != null && storedPassword.equals(hashedPassword);
    }

    public void login(String username, String password) throws AuthenticationException {
        currentUser = new User(username, password);
        if (!isAuthenticated()) {
            currentUser = null;
            throw new AuthenticationException("Invalid username or password.");
        }
    }

    public void logout() {
        currentUser = null;
    }

    public User getCurrentUser() {
        return currentUser;
    }


}
