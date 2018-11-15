package server.game.logic.entity.building.resource;

import server.game.GameContainer;

import server.game.logic.entity.building.Building;
import server.game.logic.entity.component.Economy;
import server.game.logic.entity.component.Level;
import server.game.logic.entity.component.Resources;
import server.game.logic.entity.player.Player;
import shared.game.building.BuildingType;

public class WoodFarm extends ResourceBuilding {

  private static boolean levelInitiated = false;

  /**
   * Creates an Building and registers its component.
   */
  public WoodFarm(GameContainer gameContainer, BuildingType buildingType, Player owner, int x,
      int y) {
    super(gameContainer, buildingType, owner, x, y);

    if (!levelInitiated) {
      WoodFarm.initLevels();
    }
    setLevel(1);
  }


  /**
   * Here is were all the balancing is happening. Magic!
   */
  public static void initLevels() {
    Building.init(WoodFarm.class);
    getPrice(WoodFarm.class).price.coin = 10;
    add(new Level(), Level.class, WoodFarm.class);
    add(new Economy(), Economy.class, WoodFarm.class);
    getLevel().maxLevel = 7;

    Resources resourcesIpm = new Resources();
    Resources resourcesPpm = new Resources();
    Resources resourcesUp = new Resources();

    //LVL 1
    resourcesIpm.wood = 15;
    resourcesPpm.coin = 5;
    resourcesUp.wood = 60;
    resourcesUp.coin = 70;
    getEconomy(WoodFarm.class)
        .setLevelEconomy(getLevel(), 1, resourcesIpm, resourcesPpm, resourcesUp);

    resourcesIpm = new Resources();
    resourcesPpm = new Resources();
    resourcesUp = new Resources();

    //LVL 2
    resourcesIpm.wood = 35;
    resourcesPpm.coin = 15;
    resourcesUp.wood = 150;
    resourcesUp.coin = 170;
    getEconomy(WoodFarm.class)
        .setLevelEconomy(getLevel(), 2, resourcesIpm, resourcesPpm, resourcesUp);

    resourcesIpm = new Resources();
    resourcesPpm = new Resources();
    resourcesUp = new Resources();

    //LVL 3
    resourcesIpm.wood = 45;
    resourcesPpm.coin = 20;
    resourcesUp.wood = 240;
    resourcesUp.coin = 350;
    getEconomy(WoodFarm.class)
        .setLevelEconomy(getLevel(), 3, resourcesIpm, resourcesPpm, resourcesUp);

    resourcesIpm = new Resources();
    resourcesPpm = new Resources();
    resourcesUp = new Resources();

    //LVL 4
    resourcesIpm.wood = 70;
    resourcesPpm.coin = 30;
    resourcesUp.wood = 370;
    resourcesUp.coin = 450;
    getEconomy(WoodFarm.class)
        .setLevelEconomy(getLevel(), 4, resourcesIpm, resourcesPpm, resourcesUp);

    resourcesIpm = new Resources();
    resourcesPpm = new Resources();
    resourcesUp = new Resources();

    //LVL 5
    resourcesIpm.wood = 90;
    resourcesPpm.coin = 35;
    resourcesUp.wood = 450;
    resourcesUp.coin = 520;
    getEconomy(WoodFarm.class)
        .setLevelEconomy(getLevel(), 5, resourcesIpm, resourcesPpm, resourcesUp);

    resourcesIpm = new Resources();
    resourcesPpm = new Resources();
    resourcesUp = new Resources();

    //LVL 6
    resourcesIpm.wood = 150;
    resourcesPpm.coin = 70;
    resourcesUp.wood = 520;
    resourcesUp.coin = 600;
    getEconomy(WoodFarm.class)
        .setLevelEconomy(getLevel(), 6, resourcesIpm, resourcesPpm, resourcesUp);

    resourcesIpm = new Resources();
    resourcesPpm = new Resources();
    resourcesUp = new Resources();

    //LVL 7
    resourcesIpm.wood = 250;
    resourcesPpm.coin = 100;
    resourcesUp.wood = 0;
    resourcesUp.coin = 0;
    getEconomy(WoodFarm.class)
        .setLevelEconomy(getLevel(), 7, resourcesIpm, resourcesPpm, resourcesUp);
  }

  private static Level getLevel() {
    return getLevel(WoodFarm.class);
  }
}
