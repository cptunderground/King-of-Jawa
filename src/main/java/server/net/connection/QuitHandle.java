package server.net.connection;

import java.net.Socket;
import server.user.User;
import server.user.UserManager;
import shared.net.protocol.Package;
import shared.net.protocol.PackageExecutable;
import shared.util.ConsoleLog;


public class QuitHandle implements PackageExecutable {

  private static QuitHandle instance;

  private QuitHandle() {
  }

  /**
   * This method creates an instance if it doesn't exist. Otherwise it returns the existing one.
   *
   * @return HandshakeHandle instance.
   */
  public static synchronized QuitHandle getInstance() {
    if (QuitHandle.instance == null) {
      QuitHandle.instance = new QuitHandle();
    }
    return QuitHandle.instance;
  }

  @Override
  public void run(Package p) {
    Socket socket = p.getSocket();

    User user = UserManager.getInstance().getUser(socket);
    UserManager.getInstance().remove(user);
    ConsoleLog
        .info("user disconnected: " + socket.getInetAddress() + ":" + user.getName());
  }
}