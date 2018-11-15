package client.game.lobby;

import client.Client;
import client.chat.ChatManager;
import client.ui.WindowManager;
import client.user.User;

import java.util.ArrayList;
import java.util.List;

import shared.net.protocol.PackageManager;
import shared.net.protocol.ProtocolManagement;
import shared.net.protocol.ProtocolManager;
import shared.net.protocol.packages.LobbyPackage;

public class LobbyManager extends ProtocolManagement {


  private static LobbyManager instance;
  private List<Lobby> lobbies;


  private LobbyManager() {
    lobbies = new ArrayList<>();
    ProtocolManager.getInstance().registerCaller("createLobby", LobbyHandle.getInstance());
    ProtocolManager.getInstance().registerCaller("synchronizeLobby", LobbyHandle.getInstance());
    ProtocolManager.getInstance().registerCaller("deleteLobby", LobbyHandle.getInstance());
    ProtocolManager.getInstance().registerCaller("onJoin", LobbyHandle.getInstance());
    ProtocolManager.getInstance().registerCaller("onQuit", LobbyHandle.getInstance());
    ProtocolManager.getInstance().registerCaller("onStateChange", LobbyHandle.getInstance());
    ProtocolManager.getInstance().registerCaller("onUserStateChange", LobbyHandle.getInstance());
    ProtocolManager.getInstance().registerCaller("onMapChange", LobbyHandle.getInstance());

    ProtocolManager.getInstance().registerCaller("leaveSuccessful", LobbyHandle.getInstance());
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
   * Checks if there is an equal lobby, if not, this will be added.
   *
   * @param lobby the lobby to add.
   */
  public void add(Lobby lobby) {
    if (!lobbies.contains(lobby)) {
      lobbies.add(lobby);
      if (WindowManager.getMainController() != null) {
        WindowManager.getMainController().getLobbyList().add(lobby);
      }
    }
  }

  /**
   * Checks if there is an equal lobby, if there is, this will be removed.
   *
   * @param id the id of the lobby to be removed.
   */
  public void remove(int id) {
    Lobby toRemove = get(id);
    if (toRemove != null) {
      lobbies.remove(toRemove);
      ChatManager.getInstance().remove(toRemove.getChatRoom());
      for (User u : toRemove.getUsers()) {
        toRemove.remove(u);
      }
      toRemove.removeAll();
      if (WindowManager.getMainController() != null) {
        WindowManager.getMainController().getLobbyList().remove(toRemove);
      }
    }
  }

  /**
   * Gets a lobby by its unique ID.
   */
  public Lobby get(int id) {
    for (Lobby lobby : lobbies) {
      if (lobby.getId() == id) {
        return lobby;
      }
    }
    return null;
  }


  /**
   * Sends a request to the server to remove a lobby.
   *
   * @param lobby the lobby to be removed.
   */
  public void requestLobbyRemoval(Lobby lobby) {
    LobbyPackage lobbyPackage = new LobbyPackage(Client.getInstance().getSocket(), "removeLobby");
    lobbyPackage.setLobbyId(lobby.getId());
    PackageManager.getInstance().add(lobbyPackage);
  }

  /**
   * Sends a request to the server to leave a lobby.
   */
  public void requestLobbyLeave() {
    Lobby lobby = Client.getInstance().getLocalUser().getLobby();
    if (lobby != null) {
      LobbyPackage lobbyPackage =
          new LobbyPackage(Client.getInstance().getSocket(), "removePlayerFromLobby");
      lobbyPackage.setLobbyId(lobby.getId());
      PackageManager.getInstance().add(lobbyPackage);
    }
  }

  /**
   * Sends a request to the server to open a new lobby.
   */
  public void openLobby() {
    LobbyPackage lobbyPackage = new LobbyPackage(Client.getInstance().getSocket(), "openLobby");
    PackageManager.getInstance().add(lobbyPackage);
  }

  public List<Lobby> getLobbies() {
    return lobbies;
  }

  /**
   * Sends a request to the server to join a lobby.
   */
  public void joinLobby(Lobby lobbyToJoin) {
    LobbyPackage lobbyPackage = new LobbyPackage(Client.getInstance().getSocket(), "joinLobby");
    lobbyPackage.setLobbyId(lobbyToJoin.getId());
    PackageManager.getInstance().add(lobbyPackage);
  }

  /**
   * Sends a request to the server to start a lobby.
   *
   * @param lobby        Lobby, which wants to be started.
   * @param selectedItem Selected map.
   */
  public void requestStart(Lobby lobby, String selectedItem) {
    LobbyPackage lobbyPackage = new LobbyPackage(Client.getInstance().getSocket(), "startLobby");
    if (lobby != null) {
      lobbyPackage.setLobbyId(lobby.getId());
      lobbyPackage.setLobbyMap(selectedItem);
      PackageManager.getInstance().add(lobbyPackage);
    }
  }

  /**
   * Sends mapChange to the server.
   * @param newMap Map, which is selected.
   * @param lobby Lobby
   */
  public void sendMapChange(String newMap, Lobby lobby) {
    LobbyPackage lobbyPackage = new LobbyPackage(Client.getInstance().getSocket(), "onMapChanged");
    if (lobby != null) {
      lobbyPackage.setLobbyId(lobby.getId());
      lobbyPackage.setLobbyMap(newMap);
      PackageManager.getInstance().add(lobbyPackage);
    }
  }

  /**
   * Sends a request to the server to update ready State.
   */
  public void requestUpdateReadyState() {
    LobbyPackage lobbyPackage = new LobbyPackage(Client.getInstance().getSocket(), "onStateChange");
    Lobby lobby = Client.getInstance().getLocalUser().getLobby();
    if (lobby != null) {
      lobbyPackage.setLobbyId(lobby.getId());
      PackageManager.getInstance().add(lobbyPackage);
    }
  }
}
