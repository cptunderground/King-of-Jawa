package client.user;

import client.Client;
import client.game.lobby.Lobby;
import client.ui.WindowManager;

import java.util.UUID;

public class User extends shared.user.User {

  private UUID uuid;
  private String name;
  private boolean isLocalPlayer;
  private long ping;
  private Lobby lobby;
  private String state;

  private Lobby ownedLobby;


  /**
   * This is the constructor for synchronized user objects.
   *
   * @param uuid            The players uuid.
   * @param name          Its name.
   * @param isLocalPlayer a boolean containing the info if the user is the local client or not.
   */
  public User(UUID uuid, String name, boolean isLocalPlayer) {
    this.uuid = uuid;
    this.name = name;
    if (isLocalPlayer) {
      this.ping = 0;
      Client.getInstance().setLocalUser(this);
    }
    this.isLocalPlayer = isLocalPlayer;

  }

  @Override
  public int getLobbyId() {
    if (lobby != null) {
      return lobby.getId();
    }
    return -1;
  }

  public String getName() {
    return name;
  }

  @Override
  public UUID getUUid() {
    return uuid;
  }

  @Override
  public int getId() {
    return 0;
  }

  /**
   * Changes the old username to a new one.
   *
   * @param oldName old username.
   * @param name    new username.
   */
  public void setName(String oldName, String name) {
    this.name = name;
    if (WindowManager.getMainController() != null) {
      WindowManager.getMainController().updateUserList();
    }
  }

  public void setLocal(boolean b) {
    this.isLocalPlayer = b;
  }

  public boolean isLocal() {
    return isLocalPlayer;
  }

  public void setPing(long ping) {
    this.ping = ping;
  }

  public long getPing() {
    return this.ping;
  }

  public Lobby getLobby() {
    return lobby;
  }

  public Lobby getOwnedLobby() {
    return ownedLobby;
  }

  public void setOwnedLobby(Lobby ownedLobby) {
    this.ownedLobby = ownedLobby;
  }

  public void setLobby(Lobby lobby) {
    this.lobby = lobby;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

}
