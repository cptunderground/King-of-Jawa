package shared.game.building;

import server.game.logic.entity.building.amusement.Alchemist;
import server.game.logic.entity.building.amusement.Brothel;
import server.game.logic.entity.building.amusement.Church;
import server.game.logic.entity.building.amusement.Court;
import server.game.logic.entity.building.amusement.Park;
import server.game.logic.entity.building.amusement.Range;
import server.game.logic.entity.building.amusement.SmallTower;
import server.game.logic.entity.building.amusement.Smith;
import server.game.logic.entity.building.amusement.Stable;
import server.game.logic.entity.building.amusement.Tavern;
import server.game.logic.entity.building.base.Base;
import server.game.logic.entity.building.resource.InhabitantFarm;
import server.game.logic.entity.building.resource.StoneFarm;
import server.game.logic.entity.building.resource.WoodFarm;

public enum BuildingType {
  INHABITANT, WOOD, BASE, STONE, COPPER, IRON, BROTHEL, CHURCH, COURT,
  ALCHEMIST, STABLE, TAVERN, SMITH, RANGE, PARK, SMALLTOWER, NONE;

  /**
   * returns the Class of the specific BuildingType.
   *
   * @return the Class of the specific BuildingType.
   */
  public Class getClassByType() {
    switch (this) {
      case BASE:
        return Base.class;
      case WOOD:
        return WoodFarm.class;
      case STONE:
        return StoneFarm.class;
      case INHABITANT:
        return InhabitantFarm.class;
      case BROTHEL:
        return Brothel.class;
      case CHURCH:
        return Church.class;
      case COURT:
        return Court.class;
      case ALCHEMIST:
        return Alchemist.class;
      case STABLE:
        return Stable.class;
      case TAVERN:
        return Tavern.class;
      case SMITH:
        return Smith.class;
      case RANGE:
        return Range.class;
      case PARK:
        return Park.class;
      case SMALLTOWER:
        return SmallTower.class;
      default:
        return null;
    }
  }
}
