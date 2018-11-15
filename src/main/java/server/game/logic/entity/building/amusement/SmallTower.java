package server.game.logic.entity.building.amusement;

import server.game.GameContainer;
import server.game.logic.entity.building.Building;
import server.game.logic.entity.component.AmusementStat;
import server.game.logic.entity.component.Level;
import server.game.logic.entity.player.Player;
import shared.game.building.BuildingType;

public class SmallTower extends AmusementBuilding {

  private static boolean levelInitiated = false;

  /**
   * Creates an Building and registers its component.
   */
  public SmallTower(GameContainer gameContainer, BuildingType buildingType,
      Player owner, int x, int y) {
    super(gameContainer, buildingType, owner, x, y);

    if (!levelInitiated) {
      SmallTower.initLevels();
    }
    setLevel(1);
  }

  /**
   * Here is were all the balancing is happening. Magic!
   */
  public static void initLevels() {
    Building.init(SmallTower.class);
    getPrice(SmallTower.class).price.coin = 100;
    getPrice(SmallTower.class).price.wood = 50;
    add(new Level(), Level.class, SmallTower.class);
    add(new AmusementStat(), AmusementStat.class, SmallTower.class);
    getLevel().maxLevel = 1;
    getStat(SmallTower.class).wood = 0.3f;
    getStat(SmallTower.class).stone = 0.1f;
    levelInitiated = true;
  }

  private static Level getLevel() {
    return getLevel(SmallTower.class);
  }



}
