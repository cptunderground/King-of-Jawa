package server.game.lobby;

import java.util.List;
import server.user.User;
import server.user.UserManager;
import server.user.UserState;
import server.user.UserType;
import shared.net.protocol.Package;
import shared.net.protocol.PackageExecutable;
import shared.net.protocol.packages.LobbyPackage;
import shared.util.ConsoleLog;

public class LobbyHandle implements PackageExecutable {

  private static LobbyHandle instance;

  private LobbyHandle() {
  }

  /**
   * This method creates an instance if it doesn't exist. Otherwise it returns the existing one.
   *
   * @return LobbyHandle instance.
   */
  public static synchronized LobbyHandle getInstance() {
    if (LobbyHandle.instance == null) {
      LobbyHandle.instance = new LobbyHandle();
    }
    return LobbyHandle.instance;
  }

  /**
   * Determines what action follows to the received LobbyPackage.
   *
   * @param p a LobbyPackage
   */
  @Override
  public void run(Package p) {
    LobbyPackage lobbyPackage = p.cast();
    switch (p.getName()) {
      case "requestLobbies":
        handleRequest(lobbyPackage);
        break;
      case "joinLobby":
        handlePlayerJoin(lobbyPackage);
        break;
      case "openLobby":
        handleOpen(p);
        break;
      case "removePlayerFromLobby":
        handlePlayerLeave(lobbyPackage);
        break;
      case "startLobby":
        handleLobbyStart(lobbyPackage);
        break;
      case "onMapChanged":
        handleMapChange(lobbyPackage);
        break;
      case "onStateChange":
        handleUserStateChange(lobbyPackage);
        break;
      case "removeLobby":
        handleRemove(lobbyPackage);
        break;
      default:
        break;
    }
  }

  private void handleUserStateChange(LobbyPackage lobbyPackage) {
    int lobbyId = lobbyPackage.getLobbyId();
    Lobby lobby = LobbyManager.getInstance().getLobby(lobbyId);
    User requester = UserManager.getInstance().getUser(lobbyPackage.getSocket());
    if (lobby != null) {
      UserState toSet = UserState.WAITING;
      if (lobby.getUserState(requester) == toSet) {
        toSet = UserState.READY;
      }
      lobby.setUserState(requester, toSet);
    }
  }

  private void handleMapChange(LobbyPackage p) {
    int lobbyId = p.getLobbyId();
    Lobby lobby = LobbyManager.getInstance().getLobby(lobbyId);
    User requester = UserManager.getInstance().getUser(p.getSocket());
    if (requester == lobby.getOwner()) {
      LobbyManager.getInstance().broadcastLobbyMapChanged(lobby, p.getLobbyMap());
      lobby.setCurrentMap(p.getLobbyMap());
    }
  }


  /**
   * Handles the request of a new connected user, which wants to receive all existing lobbies.
   *
   * @param p LobbyPackage with the key "requestLobbies".
   */
  private void handleRequest(Package p) {
    User user = UserManager.getInstance().getUser(p.getSocket());
    LobbyManager.getInstance().createLobby(user);
  }

  /**
   * Handles the request of a user, which wants to open a new lobby, if successful broadcasts the
   * new lobby to all users.
   *
   * @param p LobbyPackage with the key "openLobby".
   */
  private void handleOpen(Package p) {
    User user = UserManager.getInstance().getUser(p.getSocket());
    if (LobbyManager.getInstance().getLobby(user) == null) {
      Lobby lobby = new Lobby(user);
      LobbyManager.getInstance().add(lobby);
      lobby.join(user, UserType.PLAYER);
      lobby.setState(State.WAITING);
    } else {
      ConsoleLog.warning("user: " + user.getName() + " tried to open a new lobby!");
      user.sendMessage("You are already owning a lobby!");
    }
  }

  /**
   * Handles the request of a user, which wants to close a lobby OR closes a lobby if the client
   * disconnects. When successful, broadcasts the lobby, which should be removed to all users.
   * <p>Additionally checks over the socket of the sending client if its id is equal to the socket
   * of the owner of the lobby. This provides injection attacks of a different client, which wants
   * to close existing lobbies he doesn't own.</p>
   *
   * @param p LobbyPackage with the key "removeLobby".
   */
  private void handleRemove(Package p) {
    int lobbyId = Integer.parseInt(p.getData("lobbyId"));
    Lobby toRemove = LobbyManager.getInstance().getLobby(lobbyId);
    if (UserManager.getInstance().getUser(p.getSocket()).getId() == toRemove.getOwner().getId()) {

      LobbyManager.getInstance().remove(toRemove);
    }
  }

  private void handlePlayerLeave(Package p) {
    int lobbyId = Integer.parseInt(p.getData("lobbyId"));
    Lobby lobby = LobbyManager.getInstance().getLobby(lobbyId);

    if (lobby != null) {
      if (lobby.getOwner() != null) {
        if (UserManager.getInstance().getUser(p.getSocket()).getId() == lobby.getOwner()
            .getId()) {
          if (lobby.getState() == State.WAITING) {
            lobby.quit(UserManager.getInstance().getUser(p.getSocket()), UserType.PLAYER);

          } else {
            lobby.quit(UserManager.getInstance().getUser(p.getSocket()), UserType.PLAYER);
          }
        } else {
          lobby.quit(UserManager.getInstance().getUser(p.getSocket()), UserType.PLAYER);
        }
        LobbyManager.getInstance()
            .sendLeaveSuccessful(UserManager.getInstance().getUser(p.getSocket()));
      } else {
        if (lobby.getState() == State.RUNNING) {
          lobby.quit(UserManager.getInstance().getUser(p.getSocket()), UserType.PLAYER);
        }
      }
    }
  }

  private void handlePlayerJoin(Package p) {
    int lobbyId = Integer.parseInt(p.getData("lobbyId"));
    Lobby lobby = LobbyManager.getInstance().getLobby(lobbyId);
    User user = UserManager.getInstance().getUser(p.getSocket());
    if (lobby.getState() == State.WAITING) {
      if (lobby.getPlayers().size() <= 4) {
        lobby.join(user, UserType.PLAYER);
      }
    } else {
      user.sendMessage("Game is in progress!", "info");
    }
  }


  private void handleLobbyStart(Package p) {
    int lobbyId = Integer.parseInt(p.getData("lobbyId"));
    String mapName = p.getData("lobbyMap");
    Lobby lobby = LobbyManager.getInstance().getLobby(lobbyId);
    if (lobby.getState() == State.WAITING) {
      if (lobby.isEveryoneReady()) {
        lobby.startGame(mapName);
      } else {
        User sender = UserManager.getInstance().getUser(p.getSocket());
        if (sender != null) {
          List<User> waiting = lobby.getWaitingUsers();
          StringBuilder str = new StringBuilder();
          str.append(waiting.get(0).getName());
          if (waiting.size() > 1) {
            for (int i = 1; i < waiting.size(); i++) {
              str.append(", " + waiting.get(i).getName());
            }
          }
          lobby.getChatRoom().send(
              " Following Users are not ready: " + str + "\n \t   Please wait for them to be ready",
              "info");
        }
      }
    }
  }


}
