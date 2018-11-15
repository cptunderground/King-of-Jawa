package client.command.cmd;

import client.Client;
import client.command.CommandExecutable;
import client.ui.WindowManager;

import java.net.Socket;

public class Ping implements CommandExecutable {

  private static Ping instance;

  private Ping() {
  }


  /**
   * This method creates an instance if it doesn't exist.
   * Otherwise it returns the existing one.
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

    if (params.length == 1) {
      WindowManager.getMainController().sendMessage("[INFO] Ping: "
          + Client.getInstance().getLocalUser().getPing() + " ms");
    }
  }
}
