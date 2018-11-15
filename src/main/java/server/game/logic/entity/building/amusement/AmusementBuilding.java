package server.game.logic.entity.building.amusement;

import server.game.GameContainer;
import server.game.logic.entity.building.Building;
import server.game.logic.entity.component.AmusementStat;
import server.game.logic.entity.player.Player;
import shared.game.building.BuildingType;

public class AmusementBuilding extends Building {

  /**
   * Creates an Building and registers its component.
   */
  public AmusementBuilding(GameContainer gameContainer, BuildingType buildingType, Player owner,
      int x, int y) {
    super(gameContainer, buildingType, owner, x, y);
  }

  public static AmusementStat getStat(Class owner) {
    return Building.get(AmusementStat.class, owner);
  }

}
