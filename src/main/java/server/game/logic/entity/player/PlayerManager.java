package server.game.logic.entity.player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import server.game.GameContainer;
import server.game.logic.entity.Entity;
import shared.util.ConsoleLog;

public class PlayerManager {

  private Map<UUID, Player> playerMap;
  private GameContainer gameContainer;

  /**
   * Creates a PlayerManager object for a specific gameContainer.
   *
   * @param gameContainer the gameContainer where the manager should operate.
   */
  public PlayerManager(GameContainer gameContainer) {
    this.playerMap = new HashMap<>();
    this.gameContainer = gameContainer;

  }

  /**
   * Registers a player in the PlayerManager.
   *
   * @param entity the player to be registered
   */
  public void register(Player entity) {
    if (playerMap.containsKey(entity.getId())) {
      ConsoleLog.warning("The UUID is already registered. Did not add the player.");
      return;
    }
    if (playerMap.containsValue(entity)) {
      ConsoleLog.warning("The Player is already registered. Not going to add him twice");
      return;
    }

    playerMap.put(entity.getId(), entity);
  }

  /**
   * Gets all players in a GameContainer.
   *
   * @param typeClass the class to be searched in.
   * @return the players in the gameContainer as an ArrayList.
   */
  public ArrayList<Player> getPlayers(Class typeClass) {
    if (typeClass.equals(Player.class)) {
      return new ArrayList<>(playerMap.values());
    }
    return null;
  }
}
