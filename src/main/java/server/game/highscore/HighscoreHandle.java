package server.game.highscore;

import server.user.User;
import server.user.UserManager;
import shared.net.protocol.Package;
import shared.net.protocol.PackageExecutable;

public class HighscoreHandle implements PackageExecutable {

  private static HighscoreHandle instance;

  /**
   * This method creates an instance if it doesn't exist. Otherwise it returns the existing one.
   *
   * @return HighscoreHandle instance..
   */
  public static synchronized HighscoreHandle getInstance() {
    if (HighscoreHandle.instance == null) {
      HighscoreHandle.instance = new HighscoreHandle();
    }
    return HighscoreHandle.instance;
  }

  @Override
  public void run(Package p) {
    if (p.getName().equals("requestHighscore")) {
      handleRequest(p);
    }
  }

  private void handleRequest(Package p) {
    User user = UserManager.getInstance().getUser(p.getSocket());
    HighscoreManager.getInstance().sendAllHighscores(user);
  }

}
