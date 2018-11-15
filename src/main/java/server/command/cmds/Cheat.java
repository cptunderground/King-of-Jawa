package server.command.cmds;

import java.net.Socket;
import server.command.CommandExecutable;
import server.game.lobby.Lobby;
import server.game.lobby.LobbyManager;
import server.game.lobby.State;
import server.user.User;
import server.user.UserManager;

public class Cheat implements CommandExecutable {

  private static Cheat instance;
  private boolean cheatOn = false;

  private Cheat() {
  }

  /**
   * This method creates an instance if it doesn't exist. Otherwise it returns the existing one.
   *
   * @return Cheat instance.
   */
  public static Cheat getInstance() {
    if (Cheat.instance == null) {
      Cheat.instance = new Cheat();
    }
    return Cheat.instance;
  }

  @Override
  public void run(Socket s, String[] params) {
    User user = UserManager.getInstance().getUser(s);
    if (user != null) {
      Lobby lobby = LobbyManager.getInstance().getUserLobby(user);
      if (lobby != null) {
        if (lobby.getState() == State.RUNNING) {
          cheatOn = !cheatOn;
          user.sendMessage("Now activating super secret cheat!");
        }
      }
    }
  }

  public boolean isCheatOn() {
    return cheatOn;
  }
}
