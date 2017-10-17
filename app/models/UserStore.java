package models;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class UserStore {
    private static UserStore instance;
    private Map<Integer, User> users = new HashMap<>();

    public static UserStore getInstance() {
        if (instance == null) {
            instance = new UserStore();
        }
        return instance;
    }

    public User addUser(User user) {
        int id = users.size();
        user.setId(id);
        users.put(id, user);
        return user;
    }

    public User getUser(int id) {
        return users.get(id);
    }

    public Set<User> getAllUsers() {
        return new HashSet<>(users.values());
    }

    public User updateUser(User user) {
        int id = user.getId();
        if (users.containsKey(id)) {
            users.put(id, user);
            return user;
        }
        return null;
    }

    public boolean deleteUser(int id) {
        return users.remove(id) != null;
    }

}
