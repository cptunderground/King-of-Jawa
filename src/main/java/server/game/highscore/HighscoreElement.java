package server.game.highscore;

public class HighscoreElement {

  private String timeNeededString;
  private String gameName;
  private String userName;
  private int points;
  private long timeNeeded;

  /**
   * Creates a HighScoreElement with the name of the game, username, points and gameTime.
   *
   * @param gameName Name of the game.
   * @param userName Name of the user.
   * @param points points of the game.
   * @param timeString time that was played, but now FORMATTED :D.
   * @param timeNeeded time that was played.
   */
  public HighscoreElement(String gameName, String userName, int points, String timeString,
      long timeNeeded) {
    this.gameName = gameName;
    this.userName = userName;
    this.points = points;
    this.timeNeeded = timeNeeded;
    this.timeNeededString = timeString;
  }

  public String getGameName() {
    return gameName;
  }

  public String getUserName() {
    return userName;
  }

  public int getPoints() {
    return points;
  }

  public double getPointsPerMinute() {
    return (double) points / Math.max(1,timeNeeded) * 60000;
  }

  public long getTimeNeeded() {
    return timeNeeded;
  }

  public String getTimeNeededString() {
    return timeNeededString;
  }
}
