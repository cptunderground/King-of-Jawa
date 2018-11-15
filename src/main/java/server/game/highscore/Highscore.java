package server.game.highscore;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import shared.util.Chain;
import shared.util.ConsoleLog;
import shared.util.Serialization;

public class Highscore {

  private List<HighscoreElement> highscores;
  //private URI filepath;
  private String filepath;
  private static Highscore instance;

  private Highscore() {
    initHighscoreFile();
    readHighscore();
  }

  private void initHighscoreFile() {
    InputStream in = getClass().getResourceAsStream("/txt/highscore.txt");
    BufferedReader br = new BufferedReader(new InputStreamReader(in));
    try {
      File f = new File("highscore.txt");
      if (!f.exists()) {
        f.createNewFile();
        FileWriter fw = new FileWriter(f);
        String line = br.readLine();
        while (line != null) {
          fw.append(line + "\n");
          fw.flush();
          line = br.readLine();
        }
        fw.close();
        br.close();
      }
    } catch (IOException e) {
      ConsoleLog.debug(e.getMessage() + "Hans");
    }


  }

  /**
   * This method creates an instance if it doesn't exist. Otherwise it returns the existing one.
   *
   * @return Highscore instance.
   */
  public static synchronized Highscore getInstance() {
    if (Highscore.instance == null) {
      Highscore.instance = new Highscore();
    }
    return Highscore.instance;
  }

  /**
   * This method reads the highscore from the highscore.txt file.
   */
  public void readHighscore() {
    highscores = new ArrayList<>();
    try {
      File f = new File("highscore.txt");
      InputStream fileInputStream = new FileInputStream(f);
      BufferedReader br = new BufferedReader(new InputStreamReader(fileInputStream));
      Serialization serialization = new Serialization();

      String line = br.readLine();

      while (line != null) {
        Chain c = serialization.unpack(line);
        if (c.find("head") != null) {
          Chain headData = (Chain) c.find("head").getValue();
          if (headData.find("gameName") != null) {
            if (headData.find("winner") != null) {
              if (headData.find("points") != null) {
                if (headData.find("timeNeeded") != null) {
                  String gameName = (String) headData.find("gameName").getValue();
                  String winner = (String) headData.find("winner").getValue();
                  int points = Integer.parseInt((String) headData.find("points").getValue());
                  long timeNeeded = Long.parseLong((String) headData.find("timeNeeded").getValue());
                  highscores.add(new HighscoreElement(gameName, winner, points, "", timeNeeded));
                }
              }
            }
          }
        }

        line = br.readLine();
      }
      br.close();

    } catch (IOException e) {
      ConsoleLog.debug(e.getMessage());
    }
  }

  /**
   * Writes the new winner into highscore.txt
   *
   * @param highscoreElement contains the gameName, winner, points and timeNeeded
   */
  public void writeHighscore(HighscoreElement highscoreElement) {
    String gameName = highscoreElement.getGameName();
    String winner = highscoreElement.getUserName();
    int points = highscoreElement.getPoints();
    long timeNeeded = highscoreElement.getTimeNeeded();

    if (points > 0) {
      try {
        Chain c = new Chain("gameName", gameName);
        c.append("timeNeeded", timeNeeded + "");
        c.append("points", points + "");
        c.append("winner", winner);
        Serialization serialization = new Serialization();
        serialization.append("head", c);
        String data = serialization.pack();

        File f = new File("highscore.txt");
        FileWriter fw = new FileWriter(f, true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(data);
        bw.newLine();
        bw.flush();
        highscores.add(highscoreElement);
      } catch (IOException e) {
        ConsoleLog.debug(e.getMessage());
      }
    }
  }

  public ArrayList<HighscoreElement> getAll() {
    return new ArrayList<HighscoreElement>(highscores);
  }


}

