package server.game.logic.entity.building.amusement;

import server.game.GameContainer;
import server.game.logic.entity.building.Building;
import server.game.logic.entity.component.AmusementStat;
import server.game.logic.entity.component.Level;
import server.game.logic.entity.player.Player;
import shared.game.building.BuildingType;

public class Stable extends AmusementBuilding {

  private static boolean levelInitiated = false;

  /**
   * Creates an Building and registers its component.
   */
  public Stable(GameContainer gameContainer, BuildingType buildingType,
      Player owner, int x, int y) {
    super(gameContainer, buildingType, owner, x, y);
    if (!levelInitiated) {
      Stable.initLevels();
    }
    setLevel(1);
  }

  /**
   * Here is were all the balancing is happening. Magic!
   */
  public static void initLevels() {
    Building.init(Stable.class);
    getPrice(Stable.class).price.coin = 200;
    getPrice(Stable.class).price.wood = 300;
    getPrice(Stable.class).price.stone = 100;
    add(new Level(), Level.class, Stable.class);
    add(new AmusementStat(), AmusementStat.class, Stable.class);
    getLevel().maxLevel = 1;
    getStat(Stable.class).stone = 0.45f;
    getStat(Stable.class).wood = 0.45f;
    levelInitiated = true;
  }

  private static Level getLevel() {
    return getLevel(Stable.class);
  }
}
