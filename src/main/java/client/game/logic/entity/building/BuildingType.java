package client.game.logic.entity.building;

public enum BuildingType {
  BASE, WOOD_FARM, INHABITANT_FARM, STONE_FARM, CHURCH, BROTHEL,
  SMITH, PARK, SMALLTOWER, COURT,
  ALCHEMIST, STABLE, TAVERN, RANGE;

  /**
   * Gets the building type from the corresponding class.
   *
   * @param clsName the class name.
   * @return building type as enum.
   */
  public static BuildingType getBuildingType(String clsName) {
    switch (clsName) {
      case "class server.game.logic.entity.building.resource.WoodFarm":
        return WOOD_FARM;
      case "class server.game.logic.entity.building.resource.InhabitantFarm":
        return INHABITANT_FARM;
      case "class server.game.logic.entity.building.base.Base":
        return BASE;
      case "class server.game.logic.entity.building.resource.StoneFarm":
        return STONE_FARM;
      case "class server.game.logic.entity.building.amusement.Church":
        return CHURCH;
      case "class server.game.logic.entity.building.amusement.Brothel":
        return BROTHEL;
      case "class server.game.logic.entity.building.amusement.Smith":
        return SMITH;
      case "class server.game.logic.entity.building.amusement.Park":
        return PARK;
      case "class server.game.logic.entity.building.amusement.SmallTower":
        return SMALLTOWER;
      case "class server.game.logic.entity.building.amusement.Court":
        return COURT;
      case "class server.game.logic.entity.building.amusement.Alchemist":
        return ALCHEMIST;
      case "class server.game.logic.entity.building.amusement.Stable":
        return STABLE;
      case "class server.game.logic.entity.building.amusement.Tavern":
        return TAVERN;
      case "class server.game.logic.entity.building.amusement.Range":
        return RANGE;
      default:
        return INHABITANT_FARM;
    }
  }
}
