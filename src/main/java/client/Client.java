package client;

import client.chat.ChatManager;
import client.chat.ChatRoom;
import client.game.GameManager;
import client.game.highscore.HighscoreManager;
import client.game.lobby.LobbyManager;
import client.game.logic.map.MapManager;
import client.game.surprise.SurpriseManager;
import client.net.connection.ConnectionManager;
import client.net.connection.Ping;
import client.net.connection.PingManager;
import client.net.connection.PingThread;
import client.sound.Player;
import client.sound.Sound;
import client.user.User;
import client.user.UserManager;

import java.io.IOException;
import java.net.Socket;

import java.util.regex.Pattern;

import javafx.application.Platform;
import shared.net.NetworkThread;
import shared.net.protocol.ProtocolManager;
import shared.net.protocol.packages.PackageType;
import shared.util.ConsoleLog;

/**
 * Creates a singleton user instance.
 */
public class Client {

  public static Client instance;
  private String host;
  private String port;
  private Socket socket;
  private String name;
  private User localUser;
  private boolean initiated;


  /**
   * Default constructor if there are no args for Username, Host and Port given with in the
   * main-method. Can be modified with its Setters.
   */
  private Client() {
    this.host = "localhost";
    this.port = "22313";
    this.initiated = false;
  }

  /**
   * Setter for this port. Validates portnumber and sets it for this user. Regex:
   * https://stackoverflow.com/ questions/12968093/regex-to-validate-port-number/12968117
   * (25.03.18)
   *
   * @param port The port.
   */
  public void setPort(String port) {
    String portPattern =
        "^([0-9]{1,4}|[1-5][0-9]{4}|6[0-4][0-9]{3}|"
            + "65[0-4][0-9]{2}|655[0-2][0-9]|6553[0-5])$";
    final Pattern pattern = Pattern.compile(portPattern);
    if (!pattern.matcher(port).matches()) {
      this.port = "22313";
    } else {
      this.port = port;
    }
  }

  /**
   * Getter for this Port.
   *
   * @return Port of this user.
   */
  public String getPort() {
    return this.port;
  }

  /**
   * Setter for this IP. Validates IP and sets it for the this server. Regex:
   * https://stackoverflow.com/ questions/15875013/extract-ip-addresses-from-strings-using-regex
   * (25.03.28)
   *
   * @param host IP/Hostname to set.
   */
  public void setHost(String host) {
    String ipAdressPattern =
        "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
            + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
            + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
            + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
    final Pattern pattern = Pattern.compile(ipAdressPattern);
    if (!pattern.matcher(host).matches()) {
      this.host = host;
    } else {
      this.host = host;
    }
  }

  /**
   * Getter for this IP/Hostname.
   *
   * @return IP/Hostname of this user.
   */
  public String getHost() {
    return this.host;
  }

  /**
   * Getter for this Socket.
   *
   * @return Socket of the user.
   */
  public Socket getSocket() {
    return this.socket;
  }

  public boolean isInitiated() {
    return initiated;
  }

  /**
   * Checks if there is already an Instance of the user.
   *
   * @return the singleton instance of the user.
   */
  public static synchronized Client getInstance() {
    if (Client.instance == null) {
      Client.instance = new Client();
    }
    return Client.instance;
  }

  public User getLocalUser() {
    return this.localUser;
  }

  public void setLocalUser(User p) {
    this.localUser = p;
  }

  public void setName(String s) {
    this.name = s;
  }

  public String getName() {
    return name;
  }

  public void reset() {
    ConnectionManager.getInstance().sendQuit();
    instance = null;
  }

  /**
   * If no username is entered it starts with the system username otherwise it gets the entered
   * username and starts the connection to server with it.
   */
  public void start() {
    if (name == null) {
      ConsoleLog.info("Starting user connection to Server: " + getHost()
          + ":" + getPort());
      name = System.getProperty("user.name");
      System.out.println("Your Username is: " + name);
    }
    Client.getInstance().start(name);
  }

  /**
   * Runs the user instance where it starts all the functional threads with its matching in and out
   * streams.
   */
  public void start(String name) {

    try {
      Platform.runLater(() -> {
        Player.addSong(new Sound("koj-ingame1.mp3"));
        Player.addSong(new Sound("koj-main.mp3"));
        Player.play();
        Player.setVolume(0.02);
      });
    } catch (IllegalStateException illegalStateException) {
      ConsoleLog.info(illegalStateException.getMessage());
    }

    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
      try {
        ConnectionManager.getInstance().sendQuit();
        Thread.sleep(200);
      } catch (InterruptedException e) {
        ConsoleLog.debug(e.getMessage());
      }
    }));
    setName(name);
    try {

      socket = new Socket(getHost(), Integer.parseInt(getPort()));

      new NetworkThread(socket, true).start();
      new NetworkThread(socket, false).start();
      ProtocolManager.getInstance().registerType(PackageType.CONNECTION,
          ConnectionManager.getInstance());
      ProtocolManager.getInstance().registerType(PackageType.CHAT, ChatManager.getInstance());
      ProtocolManager.getInstance().registerType(PackageType.PING, PingManager.getInstance());
      ProtocolManager.getInstance().registerType(PackageType.USER, UserManager.getInstance());
      ProtocolManager.getInstance().registerType(PackageType.HIGHSCORE,
          HighscoreManager.getInstance());
      ProtocolManager.getInstance().registerType(PackageType.LOBBY, LobbyManager.getInstance());
      ProtocolManager.getInstance().registerType(PackageType.GAME, GameManager.getInstance());
      ProtocolManager.getInstance().registerType(PackageType.SHAQ, SurpriseManager.getInstance());
    } catch (IOException e) {
      ConsoleLog.warning("Server exception " + e.getMessage());
    }
  }

  /**
   * After a successful handshake between user and Server this method initializes important key
   * features for the game.
   */
  public void initFunctions() {
    if (!initiated) {
      ChatManager.getInstance().add(new ChatRoom("global"));
      Ping.getInstance().ping();
      PingThread.getInstance().start();
      HighscoreManager.getInstance().requestHighscore();
      MapManager.init();

      initiated = true;
    }
  }
}
