package server.game.logic.entity.building.resource;

import server.game.GameContainer;
import server.game.logic.entity.building.Building;
import server.game.logic.entity.component.Economy;
import server.game.logic.entity.component.Inhabitant;
import server.game.logic.entity.component.Level;
import server.game.logic.entity.component.Resources;
import server.game.logic.entity.player.Player;
import shared.game.building.BuildingType;

public class InhabitantFarm extends ResourceBuilding {

  private static boolean levelInitiated = false;

  /**
   * Creates an Building and registers its component.
   */
  public InhabitantFarm(GameContainer gameContainer, BuildingType buildingType, Player owner, int x,
      int y) {
    super(gameContainer, buildingType, owner, x, y);
    if (!levelInitiated) {
      InhabitantFarm.initLevels();
    }
    setLevel(1);
  }

  /**
   * This method initializes all the costs for the InhabitantBuilding.
   */
  public static void initLevels() {
    Building.init(InhabitantFarm.class);
    getPrice(InhabitantFarm.class).price.coin = 10;
    add(new Level(), Level.class, InhabitantFarm.class);
    add(new Economy(), Economy.class, InhabitantFarm.class);
    getLevel().maxLevel = 7;

    //LVL 1
    Resources resourcesIpm = new Resources();
    resourcesIpm.coin = 40;

    Resources resourcesUp = new Resources();
    resourcesUp.wood = 45;
    resourcesUp.stone = 30;

    Resources resourcesPpm = new Resources();
    getEconomy(InhabitantFarm.class)
        .setLevelEconomy(getLevel(), 1, resourcesIpm, resourcesPpm, resourcesUp);

    resourcesIpm = new Resources();
    resourcesPpm = new Resources();
    resourcesUp = new Resources();

    //LVL 2
    resourcesIpm.coin = 55;
    resourcesPpm.wood = 10;
    resourcesPpm.stone = 5;
    resourcesUp.wood = 70;
    resourcesUp.stone = 50;
    getEconomy(InhabitantFarm.class)
        .setLevelEconomy(getLevel(), 2, resourcesIpm, resourcesPpm, resourcesUp);

    resourcesIpm = new Resources();
    resourcesPpm = new Resources();
    resourcesUp = new Resources();

    //LVL 3
    resourcesIpm.coin = 90;
    resourcesPpm.wood = 20;
    resourcesPpm.stone = 10;
    resourcesUp.wood = 130;
    resourcesUp.stone = 90;
    getEconomy(InhabitantFarm.class)
        .setLevelEconomy(getLevel(), 3, resourcesIpm, resourcesPpm, resourcesUp);

    resourcesIpm = new Resources();
    resourcesPpm = new Resources();
    resourcesUp = new Resources();

    //LVL 4
    resourcesIpm.coin = 140;
    resourcesPpm.wood = 40;
    resourcesPpm.stone = 15;
    resourcesUp.wood = 250;
    resourcesUp.stone = 130;
    getEconomy(InhabitantFarm.class)
        .setLevelEconomy(getLevel(), 4, resourcesIpm, resourcesPpm, resourcesUp);

    resourcesIpm = new Resources();
    resourcesPpm = new Resources();
    resourcesUp = new Resources();

    //LVL 5
    resourcesIpm.coin = 170;
    resourcesPpm.wood = 70;
    resourcesPpm.stone = 35;
    resourcesUp.wood = 310;
    resourcesUp.stone = 180;
    getEconomy(InhabitantFarm.class)
        .setLevelEconomy(getLevel(), 5, resourcesIpm, resourcesPpm, resourcesUp);

    resourcesIpm = new Resources();
    resourcesPpm = new Resources();
    resourcesUp = new Resources();

    //LVL 6
    resourcesIpm.coin = 200;
    resourcesPpm.wood = 90;
    resourcesPpm.stone = 45;
    resourcesUp.wood = 370;
    resourcesUp.stone = 210;
    getEconomy(InhabitantFarm.class)
        .setLevelEconomy(getLevel(), 6, resourcesIpm, resourcesPpm, resourcesUp);

    resourcesIpm = new Resources();
    resourcesPpm = new Resources();
    resourcesUp = new Resources();

    //LVL 7
    resourcesIpm.coin = 300;
    resourcesPpm.wood = 120;
    resourcesPpm.stone = 50;
    resourcesUp.wood = 0;
    resourcesUp.stone = 0;
    getEconomy(InhabitantFarm.class)
        .setLevelEconomy(getLevel(), 7, resourcesIpm, resourcesPpm, resourcesUp);
  }

  private static Level getLevel() {
    return getLevel(InhabitantFarm.class);
  }

  public void setLevel(int level) {
    super.setLevel(level);
    getInhabitants().inhabitants = level * 5;
  }

  public Inhabitant getInhabitants() {
    return getComponent(Inhabitant.class);
  }
}
