package server.game.logic.entity.building.amusement;

import server.game.GameContainer;
import server.game.logic.entity.building.Building;
import server.game.logic.entity.component.AmusementStat;
import server.game.logic.entity.component.Level;
import server.game.logic.entity.player.Player;
import shared.game.building.BuildingType;

public class Alchemist extends AmusementBuilding {

  private static boolean levelInitiated = false;

  /**
   * Creates an Building and registers its component.
   */
  public Alchemist(GameContainer gameContainer, BuildingType buildingType,
      Player owner, int x, int y) {
    super(gameContainer, buildingType, owner, x, y);
    if (!levelInitiated) {
      Alchemist.initLevels();
    }
    setLevel(1);
  }

  /**
   * Here is were all the balancing is happening. Magic!
   */
  public static void initLevels() {
    Building.init(Alchemist.class);
    getPrice(Alchemist.class).price.coin = 1800;
    getPrice(Alchemist.class).price.wood = 900;
    add(new Level(), Level.class, Alchemist.class);
    add(new AmusementStat(), AmusementStat.class, Alchemist.class);
    getLevel().maxLevel = 1;
    getStat(Alchemist.class).wood = 1.2f;
    getStat(Alchemist.class).stone = 0.8f;
    levelInitiated = true;
  }

  private static Level getLevel() {
    return getLevel(Alchemist.class);
  }
}
