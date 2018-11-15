package server.game.lobby;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.util.Map;

import server.user.User;
import server.user.UserManager;
import server.user.UserState;
import server.user.UserType;
import shared.net.protocol.ProtocolManagement;
import shared.net.protocol.ProtocolManager;
import shared.net.protocol.packages.LobbyPackage;
import shared.util.ConsoleLog;

public class LobbyManager extends ProtocolManagement {

  public static LobbyManager instance;
  private List<Lobby> lobbies;
  private Map<User, Lobby> userLobbies;

  /**
   * Creates a LobbyManager with a List of lobbies.
   */
  public LobbyManager() {
    lobbies = new ArrayList<>();
    userLobbies = new HashMap<>();

    ProtocolManager.getInstance().registerCaller("requestLobbies", LobbyHandle.getInstance());
    ProtocolManager.getInstance().registerCaller("openLobby", LobbyHandle.getInstance());
    ProtocolManager.getInstance().registerCaller("removeLobby", LobbyHandle.getInstance());
    ProtocolManager.getInstance()
        .registerCaller("removePlayerFromLobby", LobbyHandle.getInstance());
    ProtocolManager.getInstance().registerCaller("joinLobby", LobbyHandle.getInstance());
    ProtocolManager.getInstance().registerCaller("startLobby", LobbyHandle.getInstance());
    ProtocolManager.getInstance().registerCaller("onMapChanged", LobbyHandle.getInstance());
    ProtocolManager.getInstance().registerCaller("onStateChange", LobbyHandle.getInstance());

  }

  /**
   * This method creates an instance if it doesn't exist. Otherwise it returns the existing one.
   *
   * @return LobbyManager instance.
   */
  public static synchronized LobbyManager getInstance() {
    if (LobbyManager.instance == null) {
      LobbyManager.instance = new LobbyManager();
    }
    return LobbyManager.instance;
  }

  /**
   * Checks if there is an equal lobby, if not, this will be added. Additionally broadcasts the
   * new/added lobby to all users.
   *
   * @param lobby the lobby to add.
   */
  public void add(Lobby lobby) {
    if (!lobbies.contains(lobby)) {
      lobbies.add(lobby);
      createLobby(lobby);
    }
  }

  /**
   * Checks if there is an equal lobby, if there is, this will be removed. Additionally broadcasts
   * the lobby to be removed to all users.
   *
   * @param lobby the lobby to be removed.
   */
  public void remove(Lobby lobby) {
    if (lobbies.contains(lobby)) {
      for (User u : lobby.getUsers()) {
        u.setLobby(null);
        lobby.quit(u, UserType.PLAYER);
      }
      lobbies.remove(lobby);
      lobby.closeChatRoom();
      broadcastRemoveLobby(lobby);
      ConsoleLog.debug("Lobby " + lobby.getName() + " was closed!");
    }
  }

  /**
   * Gets a lobby by its unique ID.
   */
  public Lobby getLobby(int id) {
    for (Lobby l : lobbies) {
      if (l.getId() == id) {
        return l;
      }
    }
    return null;
  }

  /**
   * Returns a lobby with a specific User.
   *
   * @param user specific User.
   * @return Returns lobby with specific User.
   */
  public Lobby getLobby(User user) {
    for (Lobby l : lobbies) {
      if (l.getOwner() == user) {
        return l;
      }
    }
    return null;
  }

  /**
   * Gets all lobbies.
   */
  public ArrayList<Lobby> getLobbies() {
    return new ArrayList<>(lobbies);
  }

  /**
   * Creates a new LobbyPackage with the receiving user and the corresponding lobby.
   *
   * @param user the user to send to.
   * @param lobby the lobby to be send.
   */
  public void createLobby(User user, Lobby lobby) {
    LobbyPackage lobbyPackage = new LobbyPackage(user.getSession().getSocket(), "createLobby");
    lobbyPackage.setLobbyData(lobby);
    user.sendPackage(lobbyPackage);
  }

  /**
   * Sends all existing lobbies to one user.
   *
   * @param user the user which should receive the lobbies.
   */
  public void createLobby(User user) {
    for (Lobby lobby : lobbies) {
      synchronizeLobby(user, lobby);
    }
  }

  /**
   * Broadcasts one lobby to all users.
   *
   * @param lobby the lobby to be broadcasted.
   */
  public void createLobby(Lobby lobby) {
    for (User user : UserManager.getInstance().getUsers()) {
      createLobby(user, lobby);
    }
  }

  private void synchronizeLobby(User user, Lobby lobby) {
    LobbyPackage lobbyPackage = new LobbyPackage(user.getSession().getSocket(), "synchronizeLobby");
    lobbyPackage.setLobbyData(lobby);
    user.sendPackage(lobbyPackage);
  }

  /**
   * Broadcasts all lobbies to all users.
   */
  public void broadcastAllLobbies() {
    for (User user : UserManager.getInstance().getUsers()) {
      createLobby(user);
    }
  }

  /**
   * Creates a new LobbyPackage with the receiving user and the corresponding lobby. Sends the
   * updated version of an existing lobby to a user.
   *
   * @param user the user which should receive the updated lobby.
   * @param lobby the updated lobby.
   */
  public void updateLobby(User user, Lobby lobby) {
    LobbyPackage lobbyPackage = new LobbyPackage(user.getSession().getSocket(), "updateLobby");
    lobbyPackage.setLobbyData(lobby);
    user.sendPackage(lobbyPackage);
  }

  /**
   * Broadcasts the updated version of an existing lobby to all user.
   *
   * @param lobby the updated lobby.
   */
  public void broadcastLobbyUpdate(Lobby lobby) {
    for (User user : UserManager.getInstance().getUsers()) {
      updateLobby(user, lobby);
    }
  }

  /**
   * Creates a new LobbyPackage with the receiving user and the corresponding lobby. Sends the lobby
   * which should be removed.
   *
   * @param user the user which should receive the lobby to remove.
   * @param lobby the lobby to remove.
   */
  public void removeLobby(User user, Lobby lobby) {
    LobbyPackage lobbyPackage = new LobbyPackage(user.getSession().getSocket(), "deleteLobby");
    lobbyPackage.setLobbyId(lobby.getId());
    user.sendPackage(lobbyPackage);
  }

  /**
   * Broadcasts a lobby which should be removed to all users.
   *
   * @param lobby the lobby to removed.
   */
  public void broadcastRemoveLobby(Lobby lobby) {
    for (User user : UserManager.getInstance().getUsers()) {
      removeLobby(user, lobby);
    }
  }

  /**
   * This method sends the succes for a quit request.
   * @param user the user, who has quit.
   */
  public void sendLeaveSuccessful(User user) {
    LobbyPackage lobbyPackage =
        new LobbyPackage(user.getSession().getSocket(), "leaveSuccessful");
    user.sendPackage(lobbyPackage);
  }

  public Lobby getUserLobby(User u) {
    return userLobbies.get(u);
  }

  public void setUserLobby(User user, Lobby lobby) {
    userLobbies.put(user, lobby);
  }

  /**
   * Broadcasts a the change to all users in the lobby, when a new user joins the lobby.
   *
   * @param lobby the lobby which should broadcast the change.
   * @param joinedUser the user which joined the lobby.
   */
  public void broadcastLobbyJoin(Lobby lobby, User joinedUser) {
    for (User user : UserManager.getInstance().getUsers()) {
      LobbyPackage lobbyPackage =
          new LobbyPackage(user.getSession().getSocket(), "onJoin");
      lobbyPackage.setJoinedUser(joinedUser);
      lobbyPackage.setLobbyId(lobby.getId());
      lobbyPackage.setUserState(lobby.getUserState(joinedUser));
      updateUserState(user, lobby, joinedUser);
      lobbyPackage.setLobbyMap(lobby.getCurrentMap());
      user.sendPackage(lobbyPackage);
    }
  }

  private void updateUserState(User user, Lobby lobby, User toUpdate) {
    LobbyPackage lobbyPackage = new LobbyPackage(user.getSession().getSocket(),
        "onUserStateChange");
    lobbyPackage.setJoinedUser(toUpdate);
    lobbyPackage.setLobbyId(lobby.getId());
    lobbyPackage.setUserState(lobby.getUserState(toUpdate));
    user.sendPackage(lobbyPackage);
  }

  /**
   * This method broadcasts a quit for a specific lobby.
   *
   * @param lobby the lobby the user quits.
   * @param joinedUser the quitting user.
   */
  public void broadcastLobbyQuit(Lobby lobby, User joinedUser) {
    for (User user : UserManager.getInstance().getUsers()) {
      LobbyPackage lobbyPackage =
          new LobbyPackage(user.getSession().getSocket(), "onQuit");
      lobbyPackage.setLeftUser(joinedUser);
      lobbyPackage.setLobbyId(lobby.getId());
      user.sendPackage(lobbyPackage);
    }
  }

  /**
   * Broadcasts a user change to all users in the lobby.
   *
   * @param lobby the lobby which should broadcast the change.
   * @param user the lobby user.
   */
  public void broadcastUserStateChange(Lobby lobby, User user) {
    for (User toSend : UserManager.getInstance().getUsers()) {
      updateUserState(toSend, lobby, user);
    }
  }

  /**
   * Broadcasts a state change to all users in the lobby.
   *
   * @param lobby the lobby which should broadcast the change.
   * @param state the lobby state.
   */
  public void broadcastLobbyStateChanged(Lobby lobby, State state) {
    for (User user : UserManager.getInstance().getUsers()) {
      LobbyPackage lobbyPackage =
          new LobbyPackage(user.getSession().getSocket(), "onStateChange");
      lobbyPackage.setLobbyId(lobby.getId());
      lobbyPackage.setLobbyState(state);
      user.sendPackage(lobbyPackage);
    }
  }

  /**
   * Broadcasts a map change to all users in the lobby.
   *
   * @param lobby the lobby which should broadcast the change.
   * @param lobbyMap the lobby map.
   */
  public void broadcastLobbyMapChanged(Lobby lobby, String lobbyMap) {
    for (User user : UserManager.getInstance().getUsers()) {
      LobbyPackage lobbyPackage =
          new LobbyPackage(user.getSession().getSocket(), "onMapChange");
      lobbyPackage.setLobbyId(lobby.getId());
      lobbyPackage.setLobbyMap(lobbyMap);
      user.sendPackage(lobbyPackage);
    }
  }
}
