package server.game.logic.entity.component;

import shared.game.building.BuildingType;

public class AmusementStat extends Component {

  public float wood;
  public float stone;
  public float coin;
  public float iron;
  public float copper;

  /**
   * Gets the float of the specific BuildingType.
   *
   * @param buildingType BuildingType that is searched for.
   * @return float of the specific BuildingType.
   */
  public float getStatByType(BuildingType buildingType) {
    switch (buildingType) {
      case WOOD:
        return wood;
      case STONE:
        return stone;
      case COPPER:
        return copper;
      case IRON:
        return iron;
      case INHABITANT:
        return coin;
      default:
        return 0;
    }
  }
}

