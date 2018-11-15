package server.game.lobby;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import server.chat.ChatManager;
import server.chat.ChatRoom;
import server.game.GameContainer;
import server.user.User;
import server.user.UserState;
import server.user.UserType;
import shared.util.ConsoleLog;

public class Lobby {

  private static int idCounter = 0;
  private int id;
  private Map<UserType, ArrayList<User>> users;
  private Map<User, UserState> userStateMap;
  private String name;
  private User owner;
  private ChatRoom chatRoom;
  private State lobbyState;
  private String currentMap;

  private GameContainer gameContainer;

  /**
   * Creates a lobby with a specific name and owner.
   *
   * @param name specific name of the Lobby.
   * @param owner Owner of the lobby.
   */
  public Lobby(String name, User owner) {
    this.owner = owner;
    this.name = name;
    this.chatRoom = new ChatRoom(name);
    this.currentMap = "basic";
    ChatManager.getInstance().add(this.chatRoom);
    this.lobbyState = State.WAITING;
    this.id = idCounter;
    this.users = new HashMap<>();
    this.userStateMap = new HashMap<>();
    this.users.put(UserType.PLAYER, new ArrayList<>());
    this.users.put(UserType.SPECTATOR, new ArrayList<>());
    setUserState(owner, UserState.READY);
    idCounter++;
    if (owner != null) {
      ConsoleLog.debug("Lobby: " + name + " was created by " + owner.getName());
    } else {
      ConsoleLog.debug("Lobby: " + name + " was created by N/A");
    }
  }

  public Lobby(User owner) {
    this(owner.getName() + "s lobby", owner);

  }

  /**
   * This method allows a User to join the lobby.
   *
   * @param u specific User.
   */
  public void join(User u, UserType type) {
    if (!find(u)) {
      if (LobbyManager.getInstance().getUserLobby(u) == null) {
        users.get(type).add(u);
        ConsoleLog.debug("Playing Users: " + users.get(UserType.PLAYER).size());
        chatRoom.addPlayer(u);
        if (type.equals(UserType.PLAYER)) {
          if (u != owner) {
            userStateMap.put(u, UserState.WAITING);
          }
        }
        LobbyManager.getInstance().setUserLobby(u, this);
        LobbyManager.getInstance().broadcastLobbyJoin(this, u);
      }
    }
  }

  /**
   * Removes a specific User from the lobby.
   *
   * @param u specific User.
   */
  public void quit(User u, UserType userType) {
    if (find(u)) {

      users.get(userType).remove(u);
      chatRoom.removePlayer(u);
      LobbyManager.getInstance().setUserLobby(u, null);
      LobbyManager.getInstance().broadcastLobbyQuit(this, u);

      if (users.get(UserType.PLAYER).size() == 0) {
        if (lobbyState == State.RUNNING) {
          setState(State.FINISHED);
          closeChatRoom();
          if (gameContainer != null) {
            gameContainer.stopGame();
          }
        } else if (lobbyState == State.WAITING) {
          LobbyManager.getInstance().remove(this);
          return;
        }
      }
      if (lobbyState == State.RUNNING) {
        if (gameContainer != null) {
          gameContainer.initiateLose(u);
        }
      }
      if (owner == u) {
        owner = null;
        if (lobbyState == State.WAITING) {
          LobbyManager.getInstance().remove(this);
        }
      }

    }
  }

  public void startGame(String mapName) {
    setState(State.RUNNING);
    gameContainer = new GameContainer(this, mapName);
  }

  public State getState() {
    return lobbyState;
  }

  public void setState(State state) {
    this.lobbyState = state;
    LobbyManager.getInstance().broadcastLobbyStateChanged(this, state);
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

  /**
   * Gets all users in this lobby.
   *
   * @return all users as a List.
   */
  public ArrayList<User> getUsers() {
    ArrayList<User> ret = new ArrayList<>();
    ret.addAll(users.get(UserType.PLAYER));
    ret.addAll(users.get(UserType.SPECTATOR));
    return ret;
  }

  public ArrayList<User> getPlayers() {
    return new ArrayList<>(users.get(UserType.PLAYER));
  }


  public ArrayList<User> getSpectators() {
    return new ArrayList<>(users.get(UserType.SPECTATOR));
  }

  public void closeChatRoom() {
    ChatManager.getInstance().remove(this.chatRoom);
  }

  public GameContainer getGameContainer() {
    return gameContainer;
  }

  private boolean find(User user) {
    return (users.get(UserType.PLAYER).contains(user) || users.get(UserType.SPECTATOR)
        .contains(user));
  }

  public void setUserState(User user, UserState state) {
    userStateMap.put(user, state);
    LobbyManager.getInstance().broadcastUserStateChange(this, user);
  }

  /**
   * Gets the state of a user.
   *
   * @param user the user to get the state from.
   * @return the state defined in the enum.
   */
  public UserState getUserState(User user) {
    if (userStateMap.containsKey(user)) {
      return userStateMap.get(user);
    }
    return null;
  }

  public String getCurrentMap() {
    return currentMap;
  }

  public void setCurrentMap(String currentMap) {
    this.currentMap = currentMap;
  }

  /**
   * Checks if all the users in the lobby are ready.
   *
   * @return a boolean which determines whether all users are ready or not.
   */
  public boolean isEveryoneReady() {
    boolean ret = true;
    for (UserState userState : userStateMap.values()) {
      if (userState == UserState.WAITING) {
        ret = false;
        break;
      }
    }
    return ret;
  }

  /**
   * Gets all the users in the lobby which have the state equals to WAITING.
   *
   * @return all waiting users as a List.
   */
  public List<User> getWaitingUsers() {
    List<User> ret = new ArrayList<>();
    for (User user : userStateMap.keySet()) {
      if (getUserState(user) == UserState.WAITING) {
        ret.add(user);
      }
    }
    return ret;
  }

  public ChatRoom getChatRoom() {
    return chatRoom;
  }

  /**
   * This method changes the user mode.
   * @param user the user, the mode should be changed for.
   */
  public void changeUserMode(User user) {
    if (isPlayer(user)) {
      users.get(UserType.PLAYER).remove(user);
      users.get(UserType.SPECTATOR).add(user);
    } else {
      users.get(UserType.SPECTATOR).remove(user);
      users.get(UserType.PLAYER).add(user);

    }
  }

  public boolean isPlayer(User user) {
    return users.get(UserType.PLAYER).contains(user);
  }

  /**
   * This method stops the game for every user.
   */
  public void stopGame() {
    gameContainer = null;
    for (User user : getUsers()) {
      quit(user, getUserType(user));
    }
  }

  private UserType getUserType(User user) {
    for (UserType userType: users.keySet()) {
      List<User> userList = users.get(userType);
      if (userList.contains(user)) {
        return userType;
      }
    }
    return null;
  }
}
