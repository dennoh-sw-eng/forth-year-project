import java.util.ArrayList;
import java.util.List;

public class UserData {
    private static UserData instance;
    private List<String> usernames = new ArrayList<>();

    private UserData() {
    }

    public static synchronized UserData getInstance() {
        if (instance == null) {
            instance = new UserData();
        }
        return instance;
    }

    public List<String> getUsernames() {
        return new ArrayList<>(usernames);
    }

    public void addUsername(String username) {
        usernames.add(username);
    }

    public void removeUsername(String username) {
        usernames.remove(username);
    }
}
