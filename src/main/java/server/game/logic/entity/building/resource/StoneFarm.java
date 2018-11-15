package server.game.logic.entity.building.resource;

import server.game.GameContainer;
import server.game.logic.entity.building.Building;
import server.game.logic.entity.component.Economy;
import server.game.logic.entity.component.Level;
import server.game.logic.entity.component.Resources;
import server.game.logic.entity.player.Player;
import shared.game.building.BuildingType;

public class StoneFarm extends ResourceBuilding {

  private static boolean levelInitiated = false;

  /**
   * Creates an Building and registers its component.
   */
  public StoneFarm(GameContainer gameContainer, BuildingType buildingType, Player owner, int x,
      int y) {
    super(gameContainer, buildingType, owner, x, y);
    if (!levelInitiated) {
      StoneFarm.initLevels();
    }
    setLevel(1);
  }

  /**
   * Here is were all the balancing is happening. Magic!
   */
  public static void initLevels() {
    Building.init(StoneFarm.class);
    getPrice(StoneFarm.class).price.coin = 15;
    add(new Level(), Level.class, StoneFarm.class);
    add(new Economy(), Economy.class, StoneFarm.class);
    getLevel().maxLevel = 7;

    Resources resourcesIpm = new Resources();
    Resources resourcesPpm = new Resources();
    Resources resourcesUp = new Resources();

    //LVL 1
    resourcesIpm.stone = 10;
    resourcesPpm.coin = 10;
    resourcesUp.stone = 60;
    resourcesUp.coin = 50;
    getEconomy(StoneFarm.class)
        .setLevelEconomy(getLevel(), 1, resourcesIpm, resourcesPpm, resourcesUp);

    resourcesIpm = new Resources();
    resourcesPpm = new Resources();
    resourcesUp = new Resources();

    //LVL 2
    resourcesIpm.stone = 25;
    resourcesPpm.coin = 20;
    resourcesUp.stone = 90;
    resourcesUp.coin = 170;
    getEconomy(StoneFarm.class)
        .setLevelEconomy(getLevel(), 2, resourcesIpm, resourcesPpm, resourcesUp);

    resourcesIpm = new Resources();
    resourcesPpm = new Resources();
    resourcesUp = new Resources();

    //LVL 3
    resourcesIpm.stone = 35;
    resourcesPpm.coin = 30;
    resourcesUp.stone = 130;
    resourcesUp.coin = 370;
    getEconomy(StoneFarm.class)
        .setLevelEconomy(getLevel(), 3, resourcesIpm, resourcesPpm, resourcesUp);

    resourcesIpm = new Resources();
    resourcesPpm = new Resources();
    resourcesUp = new Resources();

    //LVL 4
    resourcesIpm.stone = 50;
    resourcesPpm.coin = 35;
    resourcesUp.stone = 170;
    resourcesUp.coin = 450;
    getEconomy(StoneFarm.class)
        .setLevelEconomy(getLevel(), 4, resourcesIpm, resourcesPpm, resourcesUp);

    resourcesIpm = new Resources();
    resourcesPpm = new Resources();
    resourcesUp = new Resources();

    //LVL 5
    resourcesIpm.stone = 90;
    resourcesPpm.coin = 70;
    resourcesUp.stone = 220;
    resourcesUp.coin = 520;
    getEconomy(StoneFarm.class)
        .setLevelEconomy(getLevel(), 5, resourcesIpm, resourcesPpm, resourcesUp);

    resourcesIpm = new Resources();
    resourcesPpm = new Resources();
    resourcesUp = new Resources();

    //LVL 6
    resourcesIpm.stone = 170;
    resourcesPpm.coin = 90;
    resourcesUp.stone = 300;
    resourcesUp.coin = 650;
    getEconomy(StoneFarm.class)
        .setLevelEconomy(getLevel(), 6, resourcesIpm, resourcesPpm, resourcesUp);

    resourcesIpm = new Resources();
    resourcesPpm = new Resources();
    resourcesUp = new Resources();

    //LVL 7
    resourcesIpm.stone = 300;
    resourcesPpm.coin = 100;
    resourcesUp.stone = 0;
    resourcesUp.coin = 0;
    getEconomy(StoneFarm.class)
        .setLevelEconomy(getLevel(), 7, resourcesIpm, resourcesPpm, resourcesUp);
  }

  private static Level getLevel() {
    return getLevel(StoneFarm.class);
  }
}
