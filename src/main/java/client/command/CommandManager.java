package client.command;

import client.command.cmd.Global;
import client.command.cmd.MuteSound;
import client.command.cmd.NewLobby;
import client.command.cmd.Ping;

import java.util.HashMap;
import java.util.Map;

import shared.net.protocol.Package;
import shared.util.ConsoleLog;

public class CommandManager {

  private static CommandManager instance;
  private Map<String, CommandExecutable> commands;

  private CommandManager() {
    commands = new HashMap<>();
    register("ping", Ping.getInstance());
    register("nlobby", NewLobby.getInstance());
    register("g", Global.getInstance());
    register("mute", MuteSound.getInstance());
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
      //TODO ADD Clientside output
    }
  }

  /**
   * Registers a command and its corresponding executable.
   */
  public void register(String cmd, CommandExecutable cmdExec) {
    commands.put(cmd, cmdExec);
    ConsoleLog.debug("(CommandManager) Command " + cmd + " was registerd.");
  }

}
