package server.net.connection;

import shared.net.protocol.ProtocolManagement;
import shared.net.protocol.ProtocolManager;


public class ConnectionManager extends ProtocolManagement {

  private static ConnectionManager instance;

  private ConnectionManager() {
    ProtocolManager.getInstance().registerCaller("requestHandshake", HandshakeHandle.getInstance());
    ProtocolManager.getInstance().registerCaller("onClientQuit", QuitHandle.getInstance());
  }

  /**
   * This method creates an instance if it doesn't exist. Otherwise it returns the existing one.
   *
   * @return ConnectionManager instance.
   */
  public static synchronized ConnectionManager getInstance() {
    if (ConnectionManager.instance == null) {
      ConnectionManager.instance = new ConnectionManager();
    }
    return ConnectionManager.instance;
  }
}
