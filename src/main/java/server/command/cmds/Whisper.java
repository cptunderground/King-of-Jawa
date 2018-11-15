package server.command.cmds;

import java.net.Socket;
import server.command.CommandExecutable;
import server.user.User;
import server.user.UserManager;
import shared.util.ConsoleLog;

public class Whisper implements CommandExecutable {

  private static Whisper instance;

  private Whisper() {
  }

  /**
   * This method creates an instance if it doesn't exist. Otherwise it returns the existing one.
   *
   * @return Whisper instance.
   */
  public static synchronized Whisper getInstance() {
    if (Whisper.instance == null) {
      Whisper.instance = new Whisper();
    }
    return Whisper.instance;
  }


  @Override
  public void run(Socket s, String[] params) {
    User c = UserManager.getInstance().getUser(s);
    if (params.length > 1) {
      if (params.length > 2) {
        if (!params[1].startsWith("\"")) {
          ConsoleLog.warning("Wrong usage of whisper");
          c.sendMessage("Usage: /whisper \"[target]\" [message]", "info");
        }
        String name = parseName(params);

        if (UserManager.getInstance().findByName(name) != null) {
          User userToSend = UserManager.getInstance().findByName(name);
          String message = extractMessage(params);
          userToSend.sendMessage(c, message);
        } else {
          ConsoleLog.warning("user to whisper to does not exist");
          c.sendMessage("user " + name + " does not exist", "error");
        }
      } else {
        ConsoleLog.warning("user tried to whisper without any message");
        c.sendMessage("You need to enter a message", "error");
      }
    } else {
      ConsoleLog.warning("user tried to whisper to a nameless user");
      c.sendMessage("No whispertarget found", "error");
    }

  }

  /**
   * Parses the username form " to ".
   *
   * @param params the array created in CommandManager from the users input.
   * @return username.
   */
  public String parseName(String[] params) {
    String name = "";

    for (int i = 1; i < params.length; i++) {
      name += params[i] + " ";
      if (params[i].endsWith("\"")) {
        break;
      }
    }
    name = name.substring(1, name.length() - 2);
    return name;
  }

  /**
   * Extracts the message out ouf the userinput array.
   *
   * @param params the array created in the CommandManager from the users input.
   * @return message.
   */
  public String extractMessage(String[] params) {
    String message = "";
    int i;
    for (i = 1; i < params.length; i++) {
      if (params[i].endsWith("\"")) {
        break;
      }
    }

    if (params.length == i + 1) {
      return params[i + 1];
    } else {
      message = params[i + 1];
      for (int j = i + 2; j < params.length; j++) {
        message += " " + params[j];
      }
      return message;
    }
  }
}
