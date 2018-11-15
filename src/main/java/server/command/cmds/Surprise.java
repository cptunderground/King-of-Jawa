package server.command.cmds;

import java.net.Socket;
import server.command.CommandExecutable;
import server.user.User;
import server.user.UserManager;
import shared.net.protocol.packages.ShaqPackage;
import shared.util.ConsoleLog;

public class Surprise implements CommandExecutable {

  private static Surprise instance;

  private Surprise() {
  }

  /**
   * This method creates an instance if it doesn't exist. Otherwise it returns the existing one.
   *
   * @return Surprise instance.
   */
  public static synchronized Surprise getInstance() {
    if (Surprise.instance == null) {
      Surprise.instance = new Surprise();
    }
    return Surprise.instance;
  }


  @Override
  public void run(Socket s, String[] params) {
    for (User user: UserManager.getInstance().getUsers()) {
      ShaqPackage shaqPackage = new ShaqPackage(user.getSession().getSocket(), "play");
      shaqPackage.setSound("surprise.mp3");
      user.sendPackage(shaqPackage);
    }
  }

}
