package server.game.logic.entity.building.amusement;

import server.game.GameContainer;
import server.game.logic.entity.building.Building;
import server.game.logic.entity.component.AmusementStat;
import server.game.logic.entity.component.Level;
import server.game.logic.entity.player.Player;
import shared.game.building.BuildingType;

public class Park extends AmusementBuilding {

  private static boolean levelInitiated = false;

  /**
   * Creates an Building and registers its component.
   */
  public Park(GameContainer gameContainer, BuildingType buildingType,
      Player owner, int x, int y) {
    super(gameContainer, buildingType, owner, x, y);

    if (!levelInitiated) {
      Park.initLevels();
    }
    setLevel(1);
  }

  /**
   * Here is were all the balancing is happening. Magic!
   */
  public static void initLevels() {
    Building.init(Park.class);
    getPrice(Park.class).price.wood = 1200;
    getPrice(Park.class).price.stone = 600;
    add(new Level(), Level.class, Park.class);
    add(new AmusementStat(), AmusementStat.class, Park.class);
    getLevel().maxLevel = 1;
    getStat(Park.class).wood = 1.2f;
    getStat(Park.class).stone = 1.2f;
    levelInitiated = true;
  }

  private static Level getLevel() {
    return getLevel(Park.class);
  }
}
