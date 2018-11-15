package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.regex.Pattern;

import server.chat.ChatManager;
import server.chat.ChatRoom;
import server.game.GameManager;
import server.game.highscore.HighscoreManager;
import server.game.lobby.LobbyManager;
import server.net.connection.ConnectionManager;
import server.net.session.PingManager;
import server.user.User;
import shared.net.NetworkThread;
import shared.net.protocol.ProtocolManager;
import shared.net.protocol.packages.PackageType;
import shared.util.ConsoleLog;

/**
 * Creates a singleton Server instance.
 */
public class Server {

  private static Server instance;
  private String ip;
  private String port;
  private List<User> users;

  /**
   * Default constructor if there are no args for IP-adress and portnumber given with in the
   * shared-method. Can be modified with its Setters.
   */
  private Server() {
    this.port = "22313";
    this.ip = "localhost";
  }

  /**
   * Setter for this Port. Validates portnumber and sets it for this Server. Regex:
   * https://stackoverflow.com/ questions/12968093/regex-to-validate-port-number/12968117
   * (25.03.18)
   *
   * @param port A string containing the port.
   */
  public void setPort(String port) {
    String portPattern =
        "^([0-9]{1,4}|[1-5][0-9]{4}"
            + "|6[0-4][0-9]{3}|65[0-4][0-9]"
            + "{2}|655[0-2][0-9]|6553[0-5])$";
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
   * @return Port of this Server.
   */
  public String getPort() {
    return this.port;
  }

  /**
   * Validates IP and sets it for the this server. Regex: https://stackoverflow.com/
   * questions/15875013/extract-ip-addresses-from-strings-using-regex (25.03.28)
   *
   * @param ip IP to set.
   */
  public void setIp(String ip) {
    String ipAdressPattern =
        "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
            + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
            + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
            + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
    final Pattern pattern = Pattern.compile(ipAdressPattern);
    if (!pattern.matcher(ip).matches()) {
      this.ip = "localhost";
    } else {
      this.ip = ip;
    }
  }

  /**
   * Getter for this private IP.
   *
   * @return IP of this Server.
   */
  public String getIp() {
    return this.ip;
  }


  /**
   * Checks if there is already an Instance of the Server.
   *
   * @return the singleton instance of the Server.
   */
  public static synchronized Server getInstance() {
    if (Server.instance == null) {
      Server.instance = new Server();
    }
    return Server.instance;
  }

  /**
   * Runs the Server instance where it starts all the functional threads with its in and out
   * streams.
   */
  public void start() {

    ChatManager.getInstance().add(new ChatRoom("global"));

    try {
      ServerSocket serverSocket = new ServerSocket(Integer.parseInt(getPort()));
      ConsoleLog.info("Starting Server with port: " + getPort());

      while (true) {
        Socket socket = serverSocket.accept();
        new NetworkThread(socket, true).start();
        new NetworkThread(socket, false).start();
        ProtocolManager.getInstance()
            .registerType(PackageType.CONNECTION, ConnectionManager.getInstance());
        ProtocolManager.getInstance().registerType(PackageType.CHAT, ChatManager.getInstance());
        ProtocolManager.getInstance().registerType(PackageType.PING, PingManager.getInstance());
        ProtocolManager.getInstance().registerType(PackageType.HIGHSCORE,
            HighscoreManager.getInstance());
        ProtocolManager.getInstance().registerType(PackageType.LOBBY, LobbyManager.getInstance());
        ProtocolManager.getInstance().registerType(PackageType.GAME, GameManager.getInstance());
      }

    } catch (IOException e) {
      ConsoleLog.debug("Server exception " + e.getMessage());
    }
  }
}
