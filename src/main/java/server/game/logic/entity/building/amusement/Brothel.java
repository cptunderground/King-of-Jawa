package server.game.logic.entity.building.amusement;

import server.game.GameContainer;
import server.game.logic.entity.building.Building;
import server.game.logic.entity.component.AmusementStat;
import server.game.logic.entity.component.Amusements;
import server.game.logic.entity.component.Level;
import server.game.logic.entity.component.Resources;
import server.game.logic.entity.player.Player;
import shared.game.building.BuildingType;

public class Brothel extends AmusementBuilding {

  private static boolean levelInitiated = false;

  /**
   * Creates an Building and registers its component.
   */
  public Brothel(GameContainer gameContainer, BuildingType buildingType,
      Player owner, int x, int y) {
    super(gameContainer, buildingType, owner, x, y);

    if (!levelInitiated) {
      Brothel.initLevels();
    }
    setLevel(1);
  }

  /**
   * Here is were all the balancing is happening. Magic!
   */
  public static void initLevels() {
    Building.init(Brothel.class);
    getPrice(Brothel.class).price.coin = 700;
    getPrice(Brothel.class).price.wood = 150;
    add(new Level(), Level.class, Brothel.class);
    add(new AmusementStat(), AmusementStat.class, Brothel.class);
    getLevel().maxLevel = 1;
    getStat(Brothel.class).coin = 1.2f;
    levelInitiated = true;
  }

  private static Level getLevel() {
    return getLevel(Brothel.class);
  }

}
