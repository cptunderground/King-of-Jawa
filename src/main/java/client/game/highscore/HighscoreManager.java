package client.game.highscore;

import client.Client;
import client.ui.WindowManager;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import server.game.highscore.HighscoreElement;

import shared.net.protocol.PackageManager;
import shared.net.protocol.ProtocolManagement;
import shared.net.protocol.ProtocolManager;

import shared.net.protocol.packages.HighscorePackage;

public class HighscoreManager extends ProtocolManagement {

  private static HighscoreManager instance;
  private List<HighscoreElement> highscores;

  private HighscoreManager() {
    highscores = new ArrayList<>();
    ProtocolManager.getInstance().registerCaller("sendHighscore", HighscoreHandle.getInstance());
    ProtocolManager.getInstance().registerCaller("refreshHighscore", HighscoreHandle.getInstance());
  }

  /**
   * This method creates an instance if it doesn't exist. Otherwise it returns the existing one.
   *
   * @return HighscoreManager instance.
   */
  public static synchronized HighscoreManager getInstance() {
    if (HighscoreManager.instance == null) {
      instance = new HighscoreManager();
    }
    return instance;
  }

  /**
   * Requests current highscore from server.
   */
  public void requestHighscore() {
    Socket socket = Client.getInstance().getSocket();
    HighscorePackage highscorePackage = new HighscorePackage(socket, "requestHighscore");
    PackageManager.getInstance().add(highscorePackage);
  }

  /**
   *Adds highscoreElement to WindowManager.
   */
  public void add(HighscoreElement highscoreElement) {
    highscores.add(highscoreElement);
    if (WindowManager.getMainController() != null) {
      WindowManager.getMainController().getHighscoreTable().add(highscoreElement);
    }
  }

  /**
   * Clears the HighscoreTable.
   */
  public void clear() {
    highscores = new ArrayList<>();
    if (WindowManager.getMainController() != null) {
      WindowManager.getMainController().getHighscoreTable().removeAll();
    }
  }

  public void refresh() {
    clear();
    requestHighscore();
  }

  public List<HighscoreElement> getHighscores() {
    return highscores;
  }
}
