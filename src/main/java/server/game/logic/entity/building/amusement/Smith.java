package server.game.logic.entity.building.amusement;

import server.game.GameContainer;
import server.game.logic.entity.building.Building;
import server.game.logic.entity.component.AmusementStat;
import server.game.logic.entity.component.Level;
import server.game.logic.entity.player.Player;
import shared.game.building.BuildingType;

public class Smith extends AmusementBuilding {

  private static boolean levelInitiated = false;

  /**
   * Creates an Building and registers its component.
   */
  public Smith(GameContainer gameContainer, BuildingType buildingType,
      Player owner, int x, int y) {
    super(gameContainer, buildingType, owner, x, y);

    if (!levelInitiated) {
      Smith.initLevels();
    }
    setLevel(1);
  }

  /**
   * Here is were all the balancing is happening. Magic!
   */
  public static void initLevels() {
    Building.init(Smith.class);
    getPrice(Smith.class).price.stone = 300;
    getPrice(Smith.class).price.wood = 700;
    add(new Level(), Level.class, Smith.class);
    add(new AmusementStat(), AmusementStat.class, Smith.class);
    getLevel().maxLevel = 1;
    getStat(Smith.class).wood = 1.2f;
    getStat(Smith.class).stone = 1.5f;
    levelInitiated = true;
  }

  private static Level getLevel() {
    return getLevel(Smith.class);
  }

}
