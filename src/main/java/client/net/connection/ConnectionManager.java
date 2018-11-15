package client.net.connection;

import client.Client;
import shared.Main;
import shared.net.protocol.PackageManager;
import shared.net.protocol.ProtocolManagement;
import shared.net.protocol.ProtocolManager;
import shared.net.protocol.packages.ConnectionPackage;

public class ConnectionManager extends ProtocolManagement {

  private static ConnectionManager instance;

  private ConnectionManager() {
    ProtocolManager.getInstance()
        .registerCaller("validateHandshake", HandshakeHandle.getInstance());
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

  /**
   * This method requests the handshake between the client and the server.
   *
   * @param name suggested name to be registered at server.
   */
  public void requestHandshake(String name) {
    ConnectionPackage conPackage = new ConnectionPackage(Client.getInstance().getSocket(),
        "requestHandshake");
    conPackage.setData("name", name);
    conPackage.setData("version", Main.version);
    PackageManager.getInstance().add(conPackage);
  }

  /**
   * This method is telling the server, that the client is going to quit.
   */
  public void sendQuit() {
    ConnectionPackage conPackage = new ConnectionPackage(Client.getInstance().getSocket(),
        "onClientQuit");
    conPackage.setData("name", "none");
    PackageManager.getInstance().add(conPackage);

  }
}
