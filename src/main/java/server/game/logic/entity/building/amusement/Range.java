package server.game.logic.entity.building.amusement;

import java.util.Random;
import server.game.GameContainer;
import server.game.logic.entity.building.Building;
import server.game.logic.entity.component.AmusementStat;
import server.game.logic.entity.component.Level;
import server.game.logic.entity.player.Player;
import shared.game.building.BuildingType;

public class Range extends AmusementBuilding {

  private static boolean levelInitiated = false;

  /**
   * Creates an Building and registers its component.
   */
  public Range(GameContainer gameContainer, BuildingType buildingType,
      Player owner, int x, int y) {
    super(gameContainer, buildingType, owner, x, y);
    if (!levelInitiated) {
      Range.initLevels();
    }
    setLevel(1);
  }

  /**
   * Here is were all the balancing is happening. Magic!
   */
  public static void initLevels() {
    Building.init(Range.class);
    getPrice(Range.class).price.coin = 1200;
    getPrice(Range.class).price.wood = 1200;
    getPrice(Range.class).price.stone = 800;
    add(new Level(), Level.class, Range.class);
    add(new AmusementStat(), AmusementStat.class, Range.class);
    getLevel().maxLevel = 1;
    getStat(Range.class).coin = 0.8f;
    getStat(Range.class).wood = 1.2f;
    getStat(Range.class).stone = 0.9f;
    levelInitiated = true;
  }

  private static Level getLevel() {
    return getLevel(Range.class);
  }
}
