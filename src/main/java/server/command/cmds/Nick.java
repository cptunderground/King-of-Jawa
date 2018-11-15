package server.command.cmds;

import java.net.Socket;
import server.chat.ChatManager;
import server.command.CommandExecutable;
import server.user.User;
import server.user.UserManager;
import shared.util.ConsoleLog;

public class Nick implements CommandExecutable {

  private static Nick instance;

  private Nick() {
  }

  /**
   * This method creates an instance if it doesn't exist. Otherwise it returns the existing one.
   *
   * @return Nick instance.
   */
  public static synchronized Nick getInstance() {
    if (Nick.instance == null) {
      Nick.instance = new Nick();
    }
    return Nick.instance;
  }

  @Override
  public void run(Socket s, String[] params) {
    User c = UserManager.getInstance().getUser(s);
    if (params.length > 1) {
      String oldName = c.getName();
      String newName = params[1];

      for (int i = 2; i < params.length; i++) {
        newName += " " + params[i];
      }
      if (oldName.equals(newName)) {
        ConsoleLog.client(oldName + " is already the clients username.");
        c.sendMessage("Your name is already " + oldName, "error");
        return;
      }

      newName = UserManager.getInstance().validateName(newName);
      if (newName.length() <= 16) {
        c.setName(newName);
        //TODO: Add namechange infotext for ingamechat only.
        ChatManager.getInstance().getRoom("global")
            .send(oldName + " has changed their name to " + newName, "info");
        ConsoleLog.client(oldName + " changed his/her/its name to " + newName);
      } else {
        c.sendMessage("Name is too long, try agian", "error");
      }
    } else {
      c.sendMessage("Usage: /nick [newName]", "info");
    }
  }
}
