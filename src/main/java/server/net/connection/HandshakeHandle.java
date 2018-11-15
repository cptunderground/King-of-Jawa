package server.net.connection;

import java.net.Socket;

import server.user.User;
import server.user.UserManager;
import shared.Main;
import shared.net.protocol.Package;
import shared.net.protocol.PackageExecutable;
import shared.net.protocol.packages.ConnectionPackage;
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

    String name = UserManager.getInstance().validateName(p.getData("name"));
    Socket socket = p.getSocket();
    ConnectionPackage conPackage;

    User c = new User(socket, name, p.getData("name"));
    UserManager.getInstance().add(c);
    conPackage = new ConnectionPackage(socket, "validateHandshake");
    conPackage.setData("outcome", "ok");

    ConnectionPackage connectionPackage = p.cast();
    if (connectionPackage.getData("version") != null) {
      if (!connectionPackage.getData("version").equals(Main.version)) {
        c.sendMessage("Please download the latest version of King of Jawa at: https://koj.hidin.de"
            + "\n Server-version: " + Main.version + "/ Client-version: " + connectionPackage
            .getData("version"));
      }
    } else {
      c.sendMessage("Please download the latest version of King of Jawa at: https://koj.hidin.de"
          + "\n Server-version: " + Main.version + "/ Client-version: UNKNOWN");
    }
    c.sendPackage(conPackage);
    ConsoleLog
        .info("user connected: " + socket.getInetAddress() + ":" + c.getName());
  }
}
