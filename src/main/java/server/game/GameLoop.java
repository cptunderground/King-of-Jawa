package server.game;

import java.util.ArrayList;
import java.util.List;
import server.game.logic.ResourceUpdatable;
import shared.util.ConsoleLog;

public class GameLoop implements Runnable {

  private GameContainer gameContainer;
  private boolean running;
  private List<ResourceUpdatable> toUpdate;

  /**
   * This is the constructor for a game loop.
   *
   * @param gameContainer the game-container the gameLoop is attached to.
   */
  public GameLoop(GameContainer gameContainer) {
    this.gameContainer = gameContainer;
    this.running = true;
    this.toUpdate = new ArrayList<>();
  }

  @Override
  public void run() {

    long lastTime = System.currentTimeMillis();
    long lastTimeSentUpdate = System.currentTimeMillis();
    final int ticks = 60;
    double tickSeconds = 1000 / ticks;
    double deltaTicks = 0;
    GameManager.getInstance().updateResourcesToClients(gameContainer);
    while (running) {
      List<ResourceUpdatable> hans = new ArrayList<>(toUpdate);
      long now = System.currentTimeMillis();
      deltaTicks += (now - lastTime) / tickSeconds;
      lastTime = now;
      if (deltaTicks >= 1) {
        for (ResourceUpdatable updatable : hans) {
          if (gameContainer.getPlayingUsers().contains(updatable.getOwner().getUser())) {
            updatable.update(now);
          }
        }
        if (now - lastTimeSentUpdate >= 5000) {
          GameManager.getInstance().updateResourcesToClients(gameContainer);
          ConsoleLog.debug("Yanny");
          lastTimeSentUpdate = now;
        }
        gameContainer.checkForWinner();
        deltaTicks--;
      }
    }
  }

  public void stop() {
    running = false;
  }

  /**
   * This method adds a resource building, which has some constant income.
   *
   * @param updatable the updatable building.
   */
  public void add(ResourceUpdatable updatable) {
    if (!toUpdate.contains(updatable)) {
      toUpdate.add(updatable);
    }
  }

  public void remove(ResourceUpdatable updatable) {
    toUpdate.remove(updatable);
  }

}
