package server.user;

import java.net.Socket;
import java.util.UUID;

import server.chat.ChatManager;
import server.game.lobby.Lobby;
import server.net.session.Session;
import shared.net.protocol.Package;
import shared.net.protocol.packages.UserPackage;

public class User extends shared.user.User {

  private Session session;
  private String oldName;
  private String name;
  private Lobby lobby;
  private UUID uuid;

  /**
   * Constructor for clients connected to the server which are managed in the UserManager.
   *
   * @param s The clients network socket.
   * @param name the clients name which has to be set.
   * @param oldName The clients old name.
   */
  public User(Socket s, String name, String oldName) {
    this.name = name;
    this.oldName = oldName;
    this.session = new Session(this, s);
    this.uuid = UUID.randomUUID();
  }

  public long getPing() {
    return session.getPing();
  }

  /**
   * This method set's the players name and broadcasts it to the other clients.
   *
   * @param name The new name.
   */
  public void setName(String name) {
    String oldName = this.name;
    for (User c : UserManager.getInstance().getUsers()) {
      UserPackage pckg = new UserPackage(c.getSession().getSocket(), "setName");
      pckg.setUsername(oldName, name);
      pckg.setUUid(uuid);
      sendPackage(pckg);
    }
    this.name = name;


  }

  public int getId() {
    return 0;
  }

  public Session getSession() {
    return this.session;
  }

  public String getName() {
    return this.name;
  }

  String getOldName() {
    return this.oldName;
  }

  public UUID getUUid() {
    return this.uuid;
  }

  @Override
  public int getLobbyId() {
    if (lobby != null) {
      return lobby.getId();
    }
    return -1;
  }

  public void sendPackage(Package p) {
    session.sendPackage(p);
  }

  /**
   * Sends a massage to a client managed in the UserManager without a prefix.
   *
   * @param message The message which has to be sent
   */
  public void sendMessage(String message) {
    ChatManager.getInstance().sendDirectMessage(this, message);

  }

  /**
   * Sends a massage to a client managed in the UserManager with a prefix. The prefix is displayed
   * in front of the message.
   *
   * @param message The message which has to be sent.
   * @param prefix String to be displayed in front of the message.
   */
  public void sendMessage(String message, String prefix) {
    ChatManager.getInstance().sendDirectMessage(this, "[" + prefix.toUpperCase() + "]" + message);
  }

  /**
   * Sends a massage to a client managed in the UserManager.
   *
   * @param source the client the message came from.
   * @param message The message which has to be sent.
   */
  public void sendMessage(User source, String message) {
    ChatManager.getInstance().sendPrivateMessage(source, this, message);
  }

  public void setLobby(Lobby lobby) {
    this.lobby = lobby;
  }

  public Lobby getLobby() {
    return lobby;
  }
}
