package server.game.logic.entity.building.amusement;

import server.game.GameContainer;
import server.game.logic.entity.building.Building;
import server.game.logic.entity.component.AmusementStat;
import server.game.logic.entity.component.Level;
import server.game.logic.entity.player.Player;
import shared.game.building.BuildingType;

public class Church extends AmusementBuilding {

  private static boolean levelInitiated = false;

  /**
   * Creates an Building and registers its component.
   */
  public Church(GameContainer gameContainer, BuildingType buildingType, Player owner, int x,
      int y) {
    super(gameContainer, buildingType, owner, x, y);
    if (!levelInitiated) {
      Church.initLevels();
    }
    setLevel(1);

  }

  /**
   * Here is were all the balancing is happening. Magic!
   */
  public static void initLevels() {
    Building.init(Church.class);
    getPrice(Church.class).price.coin = 250;
    getPrice(Church.class).price.wood = 100;
    getPrice(Church.class).price.stone = 50;
    add(new Level(), Level.class, Church.class);
    add(new AmusementStat(), AmusementStat.class, Church.class);
    getLevel().maxLevel = 1;
    getStat(Church.class).coin = 0.7f;
    getStat(Church.class).wood = 0.3f;
    getStat(Church.class).stone = 0.5f;
    levelInitiated = true;
  }

  private static Level getLevel() {
    return getLevel(Church.class);
  }

}
