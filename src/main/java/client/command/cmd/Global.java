package client.command.cmd;

import client.chat.ChatManager;
import client.command.CommandExecutable;
import java.net.Socket;

public class Global implements CommandExecutable {

  private static Global instance;

  private Global() {
  }


  /**
   * This method creates an instance if it doesn't exist. Otherwise it returns the existing one.
   *
   * @return Ping instance.
   */
  public static Global getInstance() {
    if (Global.instance == null) {
      Global.instance = new Global();
    }
    return Global.instance;
  }

  @Override
  public void run(Socket s, String[] params) {
    if (params.length > 1) {
      String msg = params[1];
      for (int i = 2; i < params.length; i++) {
        msg += " " + params[i];
      }
      ChatManager.getInstance().getRoom("global").send(msg);
    }
  }
}
