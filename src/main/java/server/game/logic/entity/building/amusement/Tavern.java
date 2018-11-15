package server.game.logic.entity.building.amusement;

import server.game.GameContainer;
import server.game.logic.entity.building.Building;
import server.game.logic.entity.component.AmusementStat;
import server.game.logic.entity.component.Level;
import server.game.logic.entity.player.Player;
import shared.game.building.BuildingType;

public class Tavern extends AmusementBuilding {

  private static boolean levelInitiated = false;

  /**
   * Creates an Building and registers its component.
   */
  public Tavern(GameContainer gameContainer, BuildingType buildingType,
      Player owner, int x, int y) {
    super(gameContainer, buildingType, owner, x, y);
    if (!levelInitiated) {
      Tavern.initLevels();
    }
    setLevel(1);
  }

  /**
   * Here is were all the balancing is happening. Magic!
   */
  public static void initLevels() {
    Building.init(Tavern.class);
    getPrice(Tavern.class).price.coin = 1200;
    getPrice(Tavern.class).price.wood = 500;
    getPrice(Tavern.class).price.stone = 200;
    add(new Level(), Level.class, Tavern.class);
    add(new AmusementStat(), AmusementStat.class, Tavern.class);
    getLevel().maxLevel = 1;
    getStat(Tavern.class).coin = 1.2f;
    levelInitiated = true;
  }

  private static Level getLevel() {
    return getLevel(Tavern.class);
  }
}
