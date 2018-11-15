package shared.net.protocol.packages;

import java.net.Socket;

import shared.net.protocol.Package;
import shared.util.Serialization;

public class HighscorePackage extends Package {

  public HighscorePackage(Socket s, String callName) {
    super(callName, PackageType.HIGHSCORE, s);

  }

  public HighscorePackage(Package p) {
    super(p);
  }

  private void setGameName(String gameName) {
    setData("gameName", gameName);
  }


  private void setUserName(String userName) {

    setData("userName", userName);
  }

  private void setPoints(int points) {

    setData("points", points + "");
  }

  private void setTimeNeeded(long timeNeeded) {

    setData("timeNeeded", timeNeeded + "");
  }

  /**
   * This method sets the data of one highscore-row into the chain.
   *
   * @param gameName The game name.
   * @param userName The users name.
   * @param points The points, which have been scored.
   * @param timeNeeded The time, which was needed to win.
   */
  public void setHighscoreRowData(String gameName, String userName, int points, long timeNeeded) {
    setGameName(gameName);
    setUserName(userName);
    setPoints(points);
    setTimeNeeded(timeNeeded);
  }

  /**
   * Gets the winners name from the package as String.
   *
   * @return the winners name as String.
   */
  public String getUsername() {
    if (getData("userName") == null) {
      return null;
    }
    return getData("userName");
  }

  /**
   * Gets the game name from the package as String.
   *
   * @return the game name as String.
   */
  public String getGameName() {
    if (getData("gameName") == null) {
      return null;
    }
    return getData("gameName");
  }

  /**
   * Gets the received points from the package as String and parses it to an int.
   *
   * @return the received points as int.
   */
  public int getPoints() {
    if (getData("points") == null) {
      return -1;
    }
    return Integer.parseInt(getData("points"));
  }

  /**
   * Gets the elapsed time of the game from the package as String and parses it to a long.
   *
   * @return the elapsed time as long.
   */
  public long getElapsedTime() {
    if (getData("timeNeeded") == null) {
      return -1;
    }
    return Long.parseLong(getData("timeNeeded"));
  }
}
