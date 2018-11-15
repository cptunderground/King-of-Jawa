package client.user;

import client.ui.WindowManager;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import shared.net.protocol.ProtocolManagement;
import shared.net.protocol.ProtocolManager;

public class UserManager extends ProtocolManagement {

  private static UserManager instance;
  private Map<UUID, User> users;

  private UserManager() {
    users = new HashMap<>();
    ProtocolManager.getInstance().registerCaller("setName", UserHandle.getInstance());
    ProtocolManager.getInstance().registerCaller("addPlayer", UserHandle.getInstance());
    ProtocolManager.getInstance().registerCaller("removePlayer", UserHandle.getInstance());
  }

  /**
   * This method creates an instance if it doesn't exist. Otherwise it returns the existing one.
   *
   * @return UserManager instance.
   */
  public static UserManager getInstance() {
    if (UserManager.instance == null) {
      UserManager.instance = new UserManager();
    }
    return UserManager.instance;
  }

  public Map<UUID, User> getUsers() {
    return new HashMap<>(users);
  }

  /**
   * Adds a player to the UserList.
   *
   * @param user User.
   */
  void addPlayer(User user) {
    users.put(user.getUUid(), user);
    if (WindowManager.getMainController() != null) {
      WindowManager.getMainController().getUserList().put(user.getUUid(), user);
    }
  }

  /**
   * This method removes a user from the users list by it's id.
   *
   * @param uuid The users id whe are looking for.
   */
  void removePlayer(UUID uuid) {
    User toRemove = users.get(uuid);
    if (toRemove != null) {
      users.remove(uuid);
      if (WindowManager.getMainController() != null) {
        WindowManager.getMainController().getUserList().remove(uuid);
      }
    }
  }

  /**
   * This method tries to get a user by its id.
   *
   * @param uuid The user id we are looking for.
   * @return The users element if it's found, otherwise null.
   */
  public User getUser(UUID uuid) {
    return users.get(uuid);
  }
}
