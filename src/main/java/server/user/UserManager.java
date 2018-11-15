package server.user;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import server.chat.ChatManager;
import server.game.lobby.Lobby;
import server.game.lobby.LobbyManager;
import server.net.session.Session;
import server.net.session.SessionManager;
import shared.net.protocol.PackageManager;
import shared.net.protocol.packages.UserPackage;
import shared.util.ConsoleLog;

public class UserManager {

  private static UserManager instance;
  private List<User> users;

  private UserManager() {
    users = new ArrayList<>();
  }

  /**
   * This method creates an instance if it doesn't exist. Otherwise it returns the existing one.
   *
   * @return UserManager instance.
   */
  public static synchronized UserManager getInstance() {
    if (UserManager.instance == null) {
      UserManager.instance = new UserManager();
    }
    return UserManager.instance;
  }

  /**
   * Searches through the list of the UserManager for an existing client by its name.
   *
   * @param name username of the client.
   * @return user Object if its exist, otherwise null.
   */
  public User findByName(String name) {
    for (User c : users) {
      if (c.getName().equals(name)) {
        return c;
      }
    }
    return null;
  }

  /**
   * Searches through the list of the UserManager for an existing client by its socket.
   *
   * @param s Socket of the client.
   * @return user Object if its exist, otherwise null.
   */
  public User getUser(Socket s) {
    if (SessionManager.get(s) == null) {
      return null;
    }
    return SessionManager.get(s).getUser();
  }

  /**
   * Searches through the list of the UserManager for an existing client by its ID.
   *
   * @param uuid ID of the client.
   * @return user Object if its exist, otherwise null.
   */
  public User getUser(UUID uuid) {
    for (User c : users) {
      if (c.getUUid().equals(uuid)) {
        return c;
      }
    }
    return null;
  }

  public User getUser(int x) {
    return null;
  }

  /**
   * Adds a new client in the UserManager and broadcasts this client to al the other users, also all
   * other users get broadcasted to this client.
   *
   * @param userToAdd client to add and broadcast.
   */
  public void add(User userToAdd) {
    ChatManager.getInstance().getRoom("global").addPlayer(userToAdd);
    users.add(userToAdd);
    for (User user : users) {
      //Sending a new user to everyone else.
      Session session = user.getSession();
      Socket socket = session.getSocket();

      UserPackage userPackage = new UserPackage(socket, "addPlayer");
      userPackage.setUsername(userToAdd.getOldName(), userToAdd.getName());
      userPackage.setUUid(userToAdd.getUUid());
      userPackage.setLocal(userToAdd == user);
      user.sendPackage(userPackage);

      //Sending all users to the fresh meat.
      if (userToAdd != user) {
        session = userToAdd.getSession();
        socket = session.getSocket();

        userPackage = new UserPackage(socket, "addPlayer");
        userPackage.setUsername(user.getOldName(), user.getName());
        userPackage.setUUid(user.getUUid());
        userPackage.setLocal(userToAdd == user);
        String state = "";
        if (LobbyManager.getInstance().getUserLobby(user) != null) {
          state = LobbyManager.getInstance().getUserLobby(user).getUserState(user).toString();
        }
        userPackage.setUserState(state);
        ConsoleLog.info("Server: State: " + state);
        userToAdd.sendPackage(userPackage);
      }
    }
    LobbyManager.getInstance().createLobby(userToAdd);
  }

  /**
   * Removes all user form the user list.
   */
  public void removeAll() {
    for (User u : users) {
      remove(u);
    }
  }

  /**
   * Removes the client from the connected users list.
   *
   * @param userToRemove The client which has to be removed.
   */
  public void remove(User userToRemove) {
    if (users.contains(userToRemove)) {
      //TODO: add handleRemoval for every add-in.
      ChatManager.getInstance().getRoom("global").removePlayer(userToRemove);
      for (User user : users) {
        if (userToRemove != user) {
          //Sending every user to remove the user.

          Session session = user.getSession();
          Socket socket = session.getSocket();

          UserPackage userPackage = new UserPackage(socket, "removePlayer");
          userPackage.setUUid(userToRemove.getUUid());
          user.sendPackage(userPackage);
        }
      }

      ConsoleLog.debug("user " + userToRemove.getName() + " has been removed.");

      users.remove(userToRemove);
      userToRemove.getSession().handleRemoval();
      PackageManager.getInstance().removeSocket(userToRemove.getSession().getSocket());

      //If this client is an owner of a lobby, it gets removed.
      //TODO: add handleRemoval for every add-in.
      ArrayList<Lobby> lobbies = LobbyManager.getInstance().getLobbies();
      for (Lobby lobby : lobbies) {
        if (lobby.getOwner() != null) {
          if (lobby.getPlayers().contains(userToRemove)) {
            lobby.quit(userToRemove, UserType.PLAYER);
          }
        }
      }
    }
  }

  public List<User> getUsers() {
    return new ArrayList<>(users);
  }

  /**
   * Verifies the Username of the user. Also sets a username based on the client's input and adds
   * the number of occurrences of this name.
   *
   * @param name Username input from user command prompt.
   * @return A validated Username for the user.
   */
  public String validateName(String name) {
    String retName;
    if (findByName(name) == null) {
      retName = name;
    } else {
      int i = 1;
      while (findByName(name + i) != null) {
        i++;
      }
      retName = name + i;
      ConsoleLog.info("Username already in use. (" + name + ")");
    }
    return retName;
  }
}
