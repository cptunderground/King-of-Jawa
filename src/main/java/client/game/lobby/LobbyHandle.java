package client.game.lobby;

import client.Client;
import client.game.GameManager;
import client.sound.Player;
import client.ui.Window;
import client.ui.WindowManager;
import client.user.User;
import client.user.UserManager;
import java.util.UUID;

import javafx.application.Platform;

import server.game.lobby.State;
import server.user.UserState;
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
  public <T extends Package> void run(T p) {
    LobbyPackage lobbyPackage = p.cast();
    switch (p.getName()) {
      case "createLobby":
        handleCreation(lobbyPackage);
        break;
      case "synchronizeLobby":
        handleSynchronization(lobbyPackage);
        break;
      case "deleteLobby":
        handleRemoval(lobbyPackage);
        break;
      case "onJoin":
        handleJoin(lobbyPackage);
        break;
      case "onQuit":
        handleQuit(lobbyPackage);
        break;
      case "onStateChange":
        handleStateChange(lobbyPackage);
        break;
      case "onUserStateChange":
        handleUserStateChange(lobbyPackage);
        break;
      case "onMapChange":
        handleMapChange(lobbyPackage);
        break;
      case "updateLobby":
        handleUpdateLobby(lobbyPackage);
        break;
      case "removeLobby":
        handleRemove(lobbyPackage);
        break;
      default:
        return;
    }
  }

  private void handleMapChange(LobbyPackage lobbyPackage) {
    Lobby lobby = LobbyManager.getInstance().get(lobbyPackage.getLobbyId());
    if (WindowManager.getLobbyController() != null) {
      if (Client.getInstance().getLocalUser().getLobby() != null) {
        if (Client.getInstance().getLocalUser().getLobby() == lobby) {
          WindowManager.getLobbyController().setCurrentMap(lobbyPackage.getLobbyMap());
        }
      }
    }
  }

  private void handleSynchronization(LobbyPackage lobbyPackage) {
    handleCreation(lobbyPackage);
  }

  private void handleUserStateChange(LobbyPackage lobbyPackage) {
    UUID userId = lobbyPackage.getJoinedUserId();
    User user = UserManager.getInstance().getUser(userId);
    user.setState(lobbyPackage.getUserState().toString());
    if (WindowManager.getMainController() != null) {
      WindowManager.getMainController().updateLobbyList();
    }
    if (user.getLobby() != null) {
      if (WindowManager.getLobbyController() != null) {
        WindowManager.getLobbyController()
            .setUserReady(user, !lobbyPackage.getUserState().equals(UserState.WAITING));
      }
    }
  }

  private void handleQuit(LobbyPackage lobbyPackage) {
    UUID leftUUid = lobbyPackage.getLeftUser();
    User leftUser = UserManager.getInstance().getUser(leftUUid);
    int lobbyId = lobbyPackage.getLobbyId();
    Lobby lobby = LobbyManager.getInstance().get(lobbyId);
    Platform.runLater(() -> {
      lobby.remove(leftUser);
      if (leftUser != null) {
        leftUser.setLobby(null);
        leftUser.setOwnedLobby(null);
      }
      if (WindowManager.getLobbyController() != null) {
        WindowManager.getLobbyController().remove(leftUser);
      }

      if (leftUser == Client.getInstance().getLocalUser()) {
        ConsoleLog.debug("User left the lobby: " + leftUser.getName());
        if (WindowManager.getLobbyController() != null) {
          Platform.runLater(WindowManager::closeLobbyWindow);
          WindowManager.getLobbyController().removeAllUser();
          if (GameManager.getInstance().getSeasoundId() >= 0) {
            Player.stopSound(GameManager.getInstance().getSeasoundId());
          }
        }
        WindowManager.setWindow(Window.MAIN);
      }

      if (WindowManager.getMainController() != null) {
        WindowManager.getMainController().updateLobbyList();
      }
    });
  }

  private void handleRemoval(LobbyPackage lobbyPackage) {
    int lobbyId = Integer.parseInt(lobbyPackage.getData("lobbyId"));
    LobbyManager.getInstance().remove(lobbyId);

  }

  private void handleStateChange(LobbyPackage lobbyPackage) {
    int lobbyId = lobbyPackage.getLobbyId();
    Lobby lobby = LobbyManager.getInstance().get(lobbyId);
    State state = lobbyPackage.getLobbyState();
    lobby.setState(state);
    if (WindowManager.getMainController() != null) {
      WindowManager.getMainController().updateLobbyList();
    }
  }

  private void handleCreation(LobbyPackage lobbyPackage) {
    int lobbyId = lobbyPackage.getLobbyId();
    String lobbyName = lobbyPackage.getLobbyName();
    UUID ownerId = lobbyPackage.getOwnerId();
    int userCount = lobbyPackage.getUserCount();
    State state = lobbyPackage.getLobbyState();
    User owner = UserManager.getInstance().getUser(ownerId);
    Lobby lobby = new Lobby(owner, lobbyName, lobbyId);
    lobby.setState(state);
    LobbyManager.getInstance().add(lobby);

    for (int i = 0; i < userCount; i++) {
      UUID userId = lobbyPackage.getUserId(i);
      User user = UserManager.getInstance().getUser(userId);
      if (user != null) {
        user.setLobby(lobby);
        Platform.runLater(() -> {
          lobby.add(user);
          if (WindowManager.getLobbyController() != null) {
            if (Client.getInstance().getLocalUser().getLobby() != null) {
              //TODO Add user.
              WindowManager.getLobbyController().setCurrentMap(lobbyPackage.getLobbyMap());
            }
          }
        });
      }
    }

    if (WindowManager.getMainController() != null) {
      WindowManager.getMainController().updateLobbyList();
    }
  }

  private void handleJoin(LobbyPackage lobbyPackage) {
    UUID joinedUUid = lobbyPackage.getJoinedUserId();
    User joinedUser = UserManager.getInstance().getUser(joinedUUid);
    int lobbyId = lobbyPackage.getLobbyId();
    Lobby lobby = LobbyManager.getInstance().get(lobbyId);
    joinedUser.setLobby(lobby);
    joinedUser.setState(lobbyPackage.getUserState().toString());
    State state = lobby.getState();

    Platform.runLater(() -> {
      lobby.add(joinedUser);
      if (joinedUser == lobby.getOwner()) {
        if (WindowManager.getLobbyController() != null) {
          WindowManager.getLobbyController()
              .setUserReady(joinedUser, !lobbyPackage.getUserState().equals(UserState.WAITING));
        }
      }
      if (joinedUser == Client.getInstance().getLocalUser()) {
        if (state == State.WAITING) {
          WindowManager.setWindow(Window.LOBBY);
          WindowManager.getLobbyController().removeAllUser();
          WindowManager.getLobbyController().setCurrentMap(lobbyPackage.getLobbyMap());
        }
        for (int i = 0; i < lobby.getUsers().size(); i++) {
          User userToAdd = lobby.getUsers().get(i);
          if (Client.getInstance().getLocalUser().getLobby() != null) {
            if (WindowManager.getLobbyController() != null) {
              WindowManager.getLobbyController().addUser(userToAdd);
            }
          }
        }
      } else {
        if (WindowManager.getLobbyController() != null) {
          WindowManager.getLobbyController().addUser(joinedUser);
        }
      }
      if (WindowManager.getMainController() != null) {
        WindowManager.getMainController().updateLobbyList();
      }


    });
  }

  /**
   * Sets up the LobbyPackage with its lobby.
   *
   * @param lobby a lobby which only contains its unique ID.
   * @param p received package.
   */
  private void setupLobby(Lobby lobby, LobbyPackage p) {

  }

  /**
   * Handles the package in which the server determines which lobbies exist and saves the updates in
   * the clients lobbylist.
   *
   * @param p LobbyPackage with the key "sendLobby".
   */
  private void handleSendLobby(LobbyPackage p) {

  }

  /**
   * Handles the package in which the server determines which lobby was updated and saves the
   * updates in the clients lobbbylist.
   *
   * @param p LobbyPackage with the key "updateLobby".
   */
  private void handleUpdateLobby(LobbyPackage p) {
    int lobbyId = Integer.parseInt(p.getData("lobbyId"));
    Lobby lobby = LobbyManager.getInstance().get(lobbyId);

    setupLobby(lobby, p);
    if (WindowManager.getMainController() != null) {
      WindowManager.getMainController().updateLobbyList();
    }
  }

  /**
   * Handles the package in which the server determines which lobby the client should delete.
   *
   * @param p LobbyPackage with the key "removeLobby".
   */
  private void handleRemove(LobbyPackage p) {
    int lobbyId = Integer.parseInt(p.getData("lobbyId"));
    LobbyManager.getInstance().remove(lobbyId);
  }
}
