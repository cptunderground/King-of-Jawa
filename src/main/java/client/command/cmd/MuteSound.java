package client.command.cmd;

import client.chat.ChatManager;
import client.command.CommandExecutable;
import client.sound.Player;
import java.net.Socket;


public class MuteSound implements CommandExecutable {

  private static MuteSound instance;

  private MuteSound() {
  }

  /**
   * This method creates an instance if it doesn't exist. Otherwise it returns the existing one.
   *
   * @return Ping instance.
   */
  public static MuteSound getInstance() {
    if (MuteSound.instance == null) {
      MuteSound.instance = new MuteSound();
    }
    return MuteSound.instance;
  }

  @Override
  public void run(Socket s, String[] params) {
    Player.toggleMute();
    String mutedString = Player.isMuted() ? "muted" : "unmuted";
    ChatManager.getInstance().outputMessage("[INFO] Sounds are now " + mutedString + ".");
  }
}
