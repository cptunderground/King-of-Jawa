package client.game.highscore;

import server.game.highscore.HighscoreElement;
import shared.net.protocol.Package;
import shared.net.protocol.PackageExecutable;


public class HighscoreHandle implements PackageExecutable {

  private static HighscoreHandle instance;

  /**
   * This method creates an instance if it doesn't exist. Otherwise it returns the existing one.
   *
   * @return HighscoreHandle instance.
   */
  public static synchronized HighscoreHandle getInstance() {
    if (HighscoreHandle.instance == null) {
      HighscoreHandle.instance = new HighscoreHandle();
    }
    return HighscoreHandle.instance;
  }

  @Override
  public void run(Package p) {
    if (p.getName().equals("sendHighscore")) {
      setHighscore(p);
    } else if (p.getName().equals("refreshHighscore")) {
      handleRefresh();
    }
  }

  /**
   * Sets highscore out of a package.
   */
  private void setHighscore(Package p) {
    long timeNeeded = Long.parseLong(p.getData("timeNeeded"));

    int hours = (int) ((timeNeeded / (1000 * 60 * 60)) % 24);
    String hrsString = hours + "";
    if (hrsString.length() == 1) {
      hrsString = "0" + hrsString;
    }

    int minutes = (int) ((timeNeeded / (1000 * 60)) % 60);
    String minString = minutes + "";
    if (minString.length() == 1) {
      minString = "0" + minString;
    }

    int seconds = (int) (timeNeeded / 1000) % 60;
    String secString = seconds + "";
    if (secString.length() == 1) {
      secString = "0" + secString;
    }

    String str = hrsString + ":" + minString + ":" + secString;
    String gameName = p.getData("gameName");
    String userName = p.getData("userName");
    int points = Integer.parseInt(p.getData("points"));
    HighscoreElement highscoreElement =
        new HighscoreElement(gameName, userName, points, str, timeNeeded);
    HighscoreManager.getInstance().add(highscoreElement);
  }

  private void handleRefresh() {
    HighscoreManager.getInstance().refresh();
  }
}
