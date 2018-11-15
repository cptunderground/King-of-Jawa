package client.command.cmd;

import client.Client;
import client.chat.ChatManager;
import client.command.CommandExecutable;
import client.game.lobby.LobbyManager;
import client.ui.WindowManager;

import java.net.Socket;


public class NewLobby implements CommandExecutable {

  private static NewLobby instance;

  private NewLobby() {
  }

  /**
   * This method creates an instance if it doesn't exist. Otherwise it returns the existing one.
   *
   * @return Ping instance.
   */
  public static NewLobby getInstance() {
    if (NewLobby.instance == null) {
      NewLobby.instance = new NewLobby();
    }
    return NewLobby.instance;
  }

  @Override
  public void run(Socket s, String[] params) {

    if (params.length == 1) {
      ChatManager.getInstance().outputMessage(Client.getInstance().getLocalUser().getLobby() + "");
    }
  }
}
