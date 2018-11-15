package server.command.cmds;

import java.net.Socket;
import server.command.CommandExecutable;
import server.user.User;
import server.user.UserManager;


public class TestLobby implements CommandExecutable {

  private static TestLobby instance;

  private TestLobby() {
  }


  /**
   * This method creates an instance if it doesn't exist. Otherwise it returns the existing one.
   *
   * @return Ping instance.
   */
  public static TestLobby getInstance() {
    if (TestLobby.instance == null) {
      TestLobby.instance = new TestLobby();
    }
    return TestLobby.instance;
  }

  @Override
  public void run(Socket s, String[] params) {
    User c = UserManager.getInstance().getUser(s);
    if (params.length == 1) {
      if (c.getLobby() != null) {
        c.sendMessage(c.getLobby().getName());
      } else {
        c.sendMessage("nope");
      }
    }
  }
}
