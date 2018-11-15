package client.game.lobby;

import client.Client;
import client.chat.ChatManager;
import client.chat.ChatRoom;
import client.ui.WindowManager;
import client.user.User;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Platform;
import server.game.lobby.State;


public class Lobby {

  private String ownerName;
  private int id;
  private String name;
  private User owner;
  private List<User> users;
  private State state;
  private final int maxPlayers = 4;
  private ChatRoom chatRoom;

  /**
   * Creates a lobby with its owner, name and ID.
   *
   * @param owner User that owns the lobby.
   * @param name Name of the lobby.
   * @param id ID of the lobby.
   */
  public Lobby(User owner, String name, int id) {
    this.owner = owner;
    this.chatRoom = new ChatRoom(name);
    ChatManager.getInstance().add(this.chatRoom);
    this.ownerName = "N/A";
    if (owner != null) {
      owner.setOwnedLobby(this);
      owner.setLobby(this);
      ownerName = owner.getName();
    }
    this.name = name;
    this.id = id;
    this.users = new ArrayList<>();
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public User getOwner() {
    return owner;
  }

  public String getOwnerName() {
    return ownerName;
  }

  public State getState() {
    return state;
  }

  /**
   * Gets the state of the lobby.
   *
   * @return the state of the lobby as String.
   */
  public String getStateString() {
    if (state == null) {
      return "N/A";
    }
    return state.toString();
  }

  public void setState(State state) {
    this.state = state;
  }

  /**
   * Adds a User to the lobby, if he or she doesn't exist already.
   *
   * @param user User that wants to be added.
   */
  public void add(User user) {
    if (!users.contains(user)) {
      users.add(user);
    }
  }

  /**
   * Removes a User, if he or she doesn't exist.
   *
   * @param user User that wants to be removed.
   */
  public void remove(User user) {
    if (users.contains(user)) {
      users.remove(user);
    }
  }


  public ArrayList<User> getUsers() {
    return new ArrayList<>(users);
  }

  public String getPlayers() {
    return users.size() + "/" + maxPlayers;
  }

  public boolean findUser(User user) {
    return users.contains(user);
  }

  /**
   * Removes all User of the lobby.
   */
  public void removeAll() {
    for (User user : users) {
    }
  }

  public ChatRoom getChatRoom() {
    return chatRoom;
  }

}
