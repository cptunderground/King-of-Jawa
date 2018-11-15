package client.net.connection;

import shared.net.protocol.ProtocolManagement;
import shared.net.protocol.ProtocolManager;

public class PingManager extends ProtocolManagement {

  private static PingManager instance;


  private PingManager() {
    ProtocolManager.getInstance().registerCaller("ping", Ping.getInstance());
    ProtocolManager.getInstance().registerCaller("pong", Ping.getInstance());
  }

  /**
   * This method creates an instance if it doesn't exist. Otherwise it returns the existing one.
   *
   * @return PingManager instance.
   */
  public static synchronized PingManager getInstance() {
    if (PingManager.instance == null) {
      PingManager.instance = new PingManager();
    }
    return PingManager.instance;
  }

}
