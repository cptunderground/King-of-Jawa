package client.net.connection;

import client.Client;
import shared.net.protocol.Package;
import shared.net.protocol.PackageExecutable;
import shared.util.ConsoleLog;

public class HandshakeHandle implements PackageExecutable {

  private static HandshakeHandle instance;

  private HandshakeHandle() {

  }

  /**
   * This method creates an instance if it doesn't exist. Otherwise it returns the existing one.
   *
   * @return HandshakeHandle instance.
   */
  public static synchronized HandshakeHandle getInstance() {
    if (HandshakeHandle.instance == null) {
      HandshakeHandle.instance = new HandshakeHandle();
    }
    return HandshakeHandle.instance;
  }

  @Override
  public void run(Package p) {
    if (p.getName().equals("validateHandshake")) {
      if (p.getData("outcome").equals("ok")) {
        ConsoleLog.info("Name was validated");
        Client.getInstance().initFunctions();
      }
    }
  }
}
