package server.game.logic.entity.building.amusement;

import server.game.GameContainer;
import server.game.logic.entity.building.Building;
import server.game.logic.entity.component.AmusementStat;
import server.game.logic.entity.component.Level;
import server.game.logic.entity.player.Player;
import shared.game.building.BuildingType;

public class Court extends AmusementBuilding {

  private static boolean levelInitiated = false;

  /**
   * Creates an Building and registers its component.
   */
  public Court(GameContainer gameContainer, BuildingType buildingType,
      Player owner, int x, int y) {
    super(gameContainer, buildingType, owner, x, y);
    if (!levelInitiated) {
      Court.initLevels();
    }
    setLevel(1);
  }

  /**
   * Here is were all the balancing is happening. Magic!
   */
  public static void initLevels() {
    Building.init(Court.class);
    getPrice(Court.class).price.coin = 2200;
    getPrice(Court.class).price.stone = 500;
    add(new Level(), Level.class, Court.class);
    add(new AmusementStat(), AmusementStat.class, Court.class);
    getLevel().maxLevel = 1;
    getStat(Court.class).coin = 2.2f;
    levelInitiated = true;
  }

  private static Level getLevel() {
    return getLevel(Court.class);
  }

}
