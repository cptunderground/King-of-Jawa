package server.game.logic.entity.building.base;

import server.game.GameContainer;
import server.game.logic.entity.building.Building;
import server.game.logic.entity.component.Amusements;
import server.game.logic.entity.component.Economy;
import server.game.logic.entity.component.Level;
import server.game.logic.entity.component.Resources;
import server.game.logic.entity.component.Storage;
import server.game.logic.entity.player.Player;
import shared.game.building.BuildingType;

public class Base extends Building {

  private static boolean levelInitiated = false;

  /**
   * Creates an Building and registers its component.
   */
  public Base(GameContainer gameContainer, BuildingType buildingType, Player owner, int x, int y) {
    super(gameContainer, buildingType, owner, x, y);
    if (!levelInitiated) {
      Base.initLevels();
      levelInitiated = true;
    }
    setLevel(1);
  }

  /**
   * sets whole data for all Levels. currenciesUP = upgradePoints. currenciesIS = inventoryStorage.
   * buildingLimit = Limit of Buildings that can be built on this island at that level.
   */
  public static void initLevels() {
    Building.init(Base.class);
    getPrice(Base.class).price.coin = 200;
    add(new Level(), Level.class, Base.class);
    add(new Storage(), Storage.class, Base.class);
    add(new Economy(), Economy.class, Base.class);
    getLevel().maxLevel = 5;

    //LVL 1
    Resources resourcesUp = new Resources();
    resourcesUp.coin = 450;
    resourcesUp.wood = 150;
    resourcesUp.stone = 100;

    Resources resourcesIs = new Resources();
    resourcesIs.coin = 500;
    resourcesIs.wood = 200;
    resourcesIs.stone = 150;

    Resources buildingLimit = new Resources();
    buildingLimit.coin = 3;
    buildingLimit.wood = 2;
    buildingLimit.stone = 2;


    Amusements amusementLimit = new Amusements();
    amusementLimit.church = 1;
    amusementLimit.smallTower = 2;

    getEconomy(Base.class).setLevelEconomy(getLevel(), 1, null, null, resourcesUp);
    getStorage(Base.class)
        .setLevelStorage(getLevel(), 1, resourcesIs, buildingLimit, amusementLimit);

    resourcesUp = new Resources();
    resourcesIs = new Resources();
    buildingLimit = new Resources();
    amusementLimit = new Amusements();

    //LVL 2
    resourcesUp.coin = 950;
    resourcesUp.wood = 450;
    resourcesUp.stone = 350;
    resourcesIs.coin = 1000;
    resourcesIs.wood = 500;
    resourcesIs.stone = 400;
    buildingLimit.coin = 4;
    buildingLimit.wood = 3;
    buildingLimit.stone = 3;

    amusementLimit.church = 2;
    amusementLimit.smallTower = 1;
    amusementLimit.brothel = 2;
    amusementLimit.stable = 4;

    getEconomy(Base.class).setLevelEconomy(getLevel(), 2, null, null, resourcesUp);
    getStorage(Base.class)
        .setLevelStorage(getLevel(), 2, resourcesIs, buildingLimit, amusementLimit);

    resourcesUp = new Resources();
    resourcesIs = new Resources();
    buildingLimit = new Resources();
    amusementLimit = new Amusements();

    //LVL 3
    resourcesUp.coin = 1750;
    resourcesUp.wood = 1450;
    resourcesUp.stone = 950;
    resourcesIs.coin = 1800;
    resourcesIs.wood = 1500;
    resourcesIs.stone = 1000;
    buildingLimit.coin = 6;
    buildingLimit.wood = 5;
    buildingLimit.stone = 5;

    amusementLimit.church = 1;
    amusementLimit.smallTower = 1;
    amusementLimit.brothel = 2;
    amusementLimit.stable = 1;
    amusementLimit.smith = 1;
    amusementLimit.tavern = 2;

    getEconomy(Base.class).setLevelEconomy(getLevel(), 3, null, null, resourcesUp);
    getStorage(Base.class)
        .setLevelStorage(getLevel(), 3, resourcesIs, buildingLimit, amusementLimit);

    resourcesUp = new Resources();
    resourcesIs = new Resources();
    buildingLimit = new Resources();
    amusementLimit = new Amusements();

    //LVL 4
    resourcesUp.coin = 2450;
    resourcesUp.wood = 1950;
    resourcesUp.stone = 1450;
    resourcesIs.coin = 2500;
    resourcesIs.wood = 2000;
    resourcesIs.stone = 1500;
    buildingLimit.coin = 7;
    buildingLimit.wood = 6;
    buildingLimit.stone = 6;

    amusementLimit.church = 1;
    amusementLimit.smallTower = 1;
    amusementLimit.brothel = 1;
    amusementLimit.stable = 1;
    amusementLimit.smith = 1;
    amusementLimit.tavern = 1;
    amusementLimit.alchemist = 1;
    amusementLimit.court = 1;
    amusementLimit.park = 1;
    amusementLimit.range = 2;

    getEconomy(Base.class).setLevelEconomy(getLevel(), 4, null, null, resourcesUp);
    getStorage(Base.class)
        .setLevelStorage(getLevel(), 4, resourcesIs, buildingLimit, amusementLimit);

    resourcesUp = new Resources();
    resourcesIs = new Resources();
    buildingLimit = new Resources();
    amusementLimit = new Amusements();

    //LVL 5
    resourcesUp.coin = 3450;
    resourcesUp.wood = 2650;
    resourcesUp.stone = 1950;
    resourcesIs.coin = 3500;
    resourcesIs.wood = 2700;
    resourcesIs.stone = 2000;
    buildingLimit.coin = 7;
    buildingLimit.wood = 7;
    buildingLimit.stone = 7;

    amusementLimit.church = 1;
    amusementLimit.smallTower = 1;
    amusementLimit.brothel = 4;
    amusementLimit.stable = 2;
    amusementLimit.smith = 2;
    amusementLimit.tavern = 3;
    amusementLimit.alchemist = 2;
    amusementLimit.court = 1;
    amusementLimit.range = 2;
    amusementLimit.park = 1;

    getEconomy(Base.class).setLevelEconomy(getLevel(), 5, null, null, resourcesUp);
    getStorage(Base.class)
        .setLevelStorage(getLevel(), 5, resourcesIs, buildingLimit, amusementLimit);
  }

  public void setLevel(int level) {
    super.setLevel(level);
    getOwner().updateLimitations();
  }

  private static Level getLevel() {
    return getLevel(Base.class);
  }

}
