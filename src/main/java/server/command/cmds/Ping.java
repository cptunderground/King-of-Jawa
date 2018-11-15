package server.command.cmds;

import java.net.Socket;
import server.command.CommandExecutable;
import server.user.User;
import server.user.UserManager;


public class Ping implements CommandExecutable {

  private static Ping instance;

  private Ping() {
  }


  /**
   * This method creates an instance if it doesn't exist. Otherwise it returns the existing one.
   *
   * @return Ping instance.
   */
  public static Ping getInstance() {
    if (Ping.instance == null) {
      Ping.instance = new Ping();
    }
    return Ping.instance;
  }

  @Override
  public void run(Socket s, String[] params) {
    User c = UserManager.getInstance().getUser(s);
    if (params.length == 1) {
      c.sendMessage("Ping: " + c.getPing() + " ms", "info");
    }
  }
}
