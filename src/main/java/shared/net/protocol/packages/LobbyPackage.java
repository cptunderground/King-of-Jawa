package shared.net.protocol.packages;

import java.net.Socket;
import java.util.List;
import java.util.UUID;

import server.game.lobby.Lobby;
import server.game.lobby.State;
import server.user.User;
import server.user.UserState;
import shared.net.protocol.Package;

public class LobbyPackage extends Package {

  public LobbyPackage(Socket s, String callName) {
    super(callName, PackageType.LOBBY, s);
  }

  public LobbyPackage(Package p) {
    super(p);
  }

  /**
   * Sets the lobby name.
   *
   * @param name The name to be set.
   */
  private void setLobbyName(String name) {
    setData("name", name);
  }

  /**
   * Sets the owner id.
   *
   * @param id The id, which has to be set as ownerId.
   */
  private void setLobbyOwner(UUID id) {
    setData("ownerId", id + "");
  }

  /**
   * Sets the lobbys GameState.
   *
   * @param state The lobbys GameState.
   */
  public void setLobbyState(State state) {
    setData("lobbyState", state.toString());
  }

  /**
   * Sets the unique lobby id.
   *
   * @param id The lobbys id.
   */
  public void setLobbyId(int id) {
    setData("lobbyId", id + "");
  }

  /**
   * Sets number of users in the lobby.
   *
   * @param userCount The number of users in a lobby.
   */
  private void setLobbyUserCount(int userCount) {
    setData("lobbyUserCount", userCount + "");
  }

  //TODO use setData with ArrayList as datatype
  private void setLobbyUsers(List<User> users) {
    for (int i = 0; i < users.size(); i++) {
      setData("user" + i, "" + users.get(i).getUUid().toString());
    }

  }

  /**
   * Sets the whole data of a lobby.
   *
   * @param lobby The lobby which has to be synchronized.
   */
  public void setLobbyData(Lobby lobby) {
    setLobbyName(lobby.getName());
    if (lobby.getOwner() != null) {
      setLobbyOwner(lobby.getOwner().getUUid());
    }
    setLobbyState(lobby.getState());
    setLobbyId(lobby.getId());
    setLobbyUsers(lobby.getUsers());
    setLobbyUserCount(lobby.getUsers().size());
    setLobbyMap(lobby.getCurrentMap());
  }

  public void setLobbyMap(String lobbyMap) {
    setData("lobbyMap", lobbyMap);
  }

  public void setJoinedUser(User user) {
    setData("joinedUser", user.getUUid().toString());
  }

  /**
   * Gets the lobby owners UUID from the package as String and converts it to an UUID.
   *
   * @return the UUID of the lobby owner as UUID.
   */
  public UUID getOwnerId() {
    if (getData("ownerId") == null) {
      return null;
    }
    return UUID.fromString(getData("ownerId"));
  }

  /**
   * Gets the lobby id from the package as String and parses it to an int.
   *
   * @return the lobby id as int.
   */
  public int getLobbyId() {
    if (getData("lobbyId") == null) {
      return -1;
    }
    return Integer.parseInt(getData("lobbyId"));
  }

  /**
   * Gets the lobby name.
   *
   * @return the lobby name as String.
   */
  public String getLobbyName() {
    if (getData("name") == null) {
      return null;
    }
    return getData("name");
  }

  /**
   * Gets the lobby's state from the package as String and converts it to the corresponding enum
   * type.
   *
   * @return the state as enum.
   */
  public State getLobbyState() {
    if (getData("lobbyState") == null) {
      return null;
    }
    return State.valueOf(getData("lobbyState"));
  }


  /**
   * Gets the number of users in the lobby from the package as String and parses it to an int.
   *
   * @return the number of users as int.
   */
  public int getUserCount() {
    if (getData("lobbyUserCount") == null) {
      return -1;
    }
    return Integer.parseInt(getData("lobbyUserCount"));
  }

  /**
   * Gets the userId by a number, checks first if the corresponding user to te number exists.
   *
   * @param i user number.
   * @return the id of this user.
   */
  public UUID getUserId(int i) {
    if (i > getUserCount()) {
      return null;
    }
    if (getData("user" + i) == null) {
      return null;
    }
    return UUID.fromString(getData("user" + i));
  }

  /**
   * Converts the UUID String of a user which joined to the UUID and gives it back.
   *
   * @return an UUID.
   */
  public UUID getJoinedUserId() {
    if (getData("joinedUser") == null) {
      return null;
    }
    return UUID.fromString(getData("joinedUser"));
  }

  /**
   * Converts the UUID String of a user which left to the UUID and gives it back.
   *
   * @return an UUID.
   */
  public UUID getLeftUser() {
    if (getData("leftUser") == null) {
      return null;
    }
    return UUID.fromString(getData("leftUser"));
  }

  public void setLeftUser(User user) {
    setData("leftUser", user.getUUid().toString());
  }

  /**
   * Gets the user's state from the package. Converts it from a String to Enum UserState.
   *
   * @return the state as enum.
   */
  public UserState getUserState() {
    if (getData("userState") == null) {
      return null;
    }
    return UserState.valueOf(getData("userState"));
  }

  /**
   * This method sets the user-state to a lobby based state.
   *
   * @param userState the user state.
   */
  public void setUserState(UserState userState) {
    if (userState != null) {
      setData("userState", userState.toString());
    }
  }

  /**
   * Gets the lobby's map from the package.
   *
   * @return the map as String.
   */
  public String getLobbyMap() {
    if (getData("lobbyMap") == null) {
      return null;
    }
    return getData("lobbyMap");
  }
}
