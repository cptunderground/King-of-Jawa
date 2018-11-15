package server.game.logic.entity.building.resource;

import java.util.List;
import server.game.GameContainer;
import server.game.logic.ResourceUpdatable;
import server.game.logic.entity.building.Building;
import server.game.logic.entity.building.BuildingManager;
import server.game.logic.entity.building.amusement.AmusementBuilding;
import server.game.logic.entity.component.Level;
import server.game.logic.entity.player.Player;
import server.game.logic.map.island.Island;
import shared.game.building.BuildingType;

public class ResourceBuilding extends Building implements ResourceUpdatable {

  private long startTime;

  /**
   * Creates an Building and registers its component.
   */
  public ResourceBuilding(GameContainer gameContainer, BuildingType buildingType, Player owner,
      int x, int y) {
    super(gameContainer, buildingType, owner, x, y);
    startTime = System.currentTimeMillis();
    gameContainer.addUpdatingBuilding(this);
  }


  @Override
  public void update(long now) {

    float factor = 0.0f;
    GameContainer gameContainer = getOwner().getGameContainer();
    Island island = gameContainer.getIslandByTilePos(getPosX(), getPosY());
    BuildingManager buildingManager = island.getBuildingManager();

    List<AmusementBuilding> amusementBuildings = buildingManager.getAllAmusementBuildings();
    if (amusementBuildings != null) {
      for (AmusementBuilding amusementBuilding : amusementBuildings) {
        factor += AmusementBuilding.getStat(amusementBuilding.getCls())
            .getStatByType(getBuildingType());
      }
    }
    if (now - startTime >= 30000) {
      int currentLevel = getComponent(Level.class).currentLevel;
      getOwner().getWallet()
          .increaseAmount(getEconomy(getCls()).getIncomePerMinute(currentLevel), 0.5f + factor);
      getOwner().getWallet()
          .decreaseAmount(getEconomy(getCls()).getPricePerMinute(currentLevel), 0.5f);
      startTime = now;
    }
  }


}
