package server.game.highscore;

import java.util.List;

import server.user.User;
import shared.net.protocol.ProtocolManagement;
import shared.net.protocol.ProtocolManager;
import shared.net.protocol.packages.HighscorePackage;

public class HighscoreManager extends ProtocolManagement {

  public static HighscoreManager instance;

  private HighscoreManager() {
    ProtocolManager.getInstance().registerCaller("requestHighscore", HighscoreHandle.getInstance());
  }

  /**
   * This method creates an instance if it doesn't exist. Otherwise it returns the existing one.
   *
   * @return HighscoreManager instance.
   */
  public static synchronized HighscoreManager getInstance() {
    if (HighscoreManager.instance == null) {
      HighscoreManager.instance = new HighscoreManager();
    }
    return HighscoreManager.instance;
  }

  /**
   * Sends all HighScores to a specific User.
   *
   * @param user specific User.
   */
  public void sendAllHighscores(User user) {
    List<HighscoreElement> highscores = Highscore.getInstance().getAll();
    for (HighscoreElement highscoreElement : highscores) {
      sendHighscore(user, highscoreElement);
    }
  }

  /**
   * Sends a HighScoreElement to a specific User.
   *
   * @param user             specific User.
   * @param highscoreElement HighScoreElement.
   */
  public void sendHighscore(User user, HighscoreElement highscoreElement) {
    HighscorePackage highscorePackage = new HighscorePackage(user.getSession().getSocket(),
        "sendHighscore");
    highscorePackage
        .setHighscoreRowData(highscoreElement.getGameName(), highscoreElement.getUserName(),
            highscoreElement.getPoints(), highscoreElement.getTimeNeeded());
    user.sendPackage(highscorePackage);
  }

  /**
   * Sends the refreshed HighScore to a specific User.
   *
   * @param user specific User.
   */
  public void refreshHighscore(User user) {
    HighscorePackage highscorePackage = new HighscorePackage(user.getSession().getSocket(),
        "refreshHighscore");
    user.sendPackage(highscorePackage);
  }

}
