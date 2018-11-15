package server.command;

import java.util.HashMap;
import java.util.Map;
import server.command.cmds.Cheat;
import server.command.cmds.Nick;
import server.command.cmds.Ping;
import server.command.cmds.Surprise;
import server.command.cmds.TestLobby;
import server.command.cmds.Whisper;
import shared.net.protocol.Package;
import shared.util.ConsoleLog;

public class CommandManager {

  private static CommandManager instance;
  private Map<String, CommandExecutable> commands;

  private CommandManager() {
    commands = new HashMap<>();
    register("nick", Nick.getInstance());
    register("whisper", Whisper.getInstance());
    register("ping", Ping.getInstance());
    register("tlobby", TestLobby.getInstance());
    register("sixthsensemta", Cheat.getInstance());
    register("shaq", Surprise.getInstance());
  }

  /**
   * This method creates an instance if it doesn't exist. Otherwise it returns the existing one.
   *
   * @return CommandManager instance.
   */
  public static synchronized CommandManager getInstance() {
    if (CommandManager.instance == null) {
      CommandManager.instance = new CommandManager();
    }
    return CommandManager.instance;
  }

  /**
   * This method handles incoming chat-packages which have been declared as a chat command.
   *
   * @param p The incoming chat-package.
   */
  public void handle(Package p) {
    String msg = p.getData("message").substring(1);
    String[] params = msg.split(" ");
    if (commands.containsKey(params[0])) {
      commands.get(params[0]).run(p.getSocket(), params);
    } else {

      /* Removed due to clientside command management.
      user user = UserManager.getInstance().getUser(p.getSocket());

      user.sendMessage(
          "Your command does not exist."
              + "\nCommands are:"
              + "\n/nick [newname]\t\tto change your current username to a new one."
              + "\n/whisper [\"username\"]\tto whisper to another user."
              + "\n/ping \t\tfor checking your current ping", "error");
    */

    }
  }

  public void register(String cmd, CommandExecutable cmdExec) {
    commands.put(cmd, cmdExec);
    ConsoleLog.info("(CommandManager) Command " + cmd + " was registerd.");
  }

}
