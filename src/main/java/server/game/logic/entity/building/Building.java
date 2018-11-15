package server.game.logic.entity.building;

import server.game.GameContainer;
import server.game.logic.entity.Entity;
import server.game.logic.entity.component.Economy;
import server.game.logic.entity.component.Inhabitant;
import server.game.logic.entity.component.Level;
import server.game.logic.entity.component.Position;
import server.game.logic.entity.component.Price;
import server.game.logic.entity.component.Storage;
import server.game.logic.entity.player.Player;
import server.game.logic.map.island.Island;
import server.game.logic.map.tile.Tile;
import shared.game.building.BuildingType;

public class Building extends Entity {

  private BuildingType buildingType;
  private Player owner;
  private static boolean buildingInitialized = false;

  /**
   * Creates an Building and registers its component.
   */
  public Building(GameContainer gameContainer, BuildingType buildingType, Player owner, int x,
      int y) {
    super(gameContainer, buildingType.getClassByType(), true);
    this.owner = owner;
    this.buildingType = buildingType;
    addComponent(new Position(), Position.class);
    addComponent(new Inhabitant(), Inhabitant.class);
    getComponent(Position.class).posX = x;
    getComponent(Position.class).posY = y;
    addComponent(new Level(), Level.class);
    super.register(buildingType.getClassByType());
  }

  public static void init(Class cls) {
    add(new Price(), Price.class, cls);
  }

  public int getPosX() {
    return getComponent(Position.class).posX;
  }

  public int getPosY() {
    return getComponent(Position.class).posY;
  }

  /**
   * Gets the tile where the building is built on.
   *
   * @return the tile connected to te building.
   */
  public Tile getTile() {
    int posX = getComponent(Position.class).posX;
    int posY = getComponent(Position.class).posY;
    return getGameContainer().getTile(posX, posY);
  }

  public Player getOwner() {
    return owner;
  }

  public static Price getPrice(Class cls) {
    return get(Price.class, cls);
  }

  public static Economy getEconomy(Class cls) {
    return get(Economy.class, cls);
  }

  public static Storage getStorage(Class cls) {
    return get(Storage.class, cls);
  }

  public static Level getLevel(Class owner) {
    return Building.get(Level.class, owner);
  }

  public Level getMyLevel() {
    return getComponent(Level.class);
  }

  /**
   * Sets the level.
   *
   * @param level the number to which the level should be set.
   */
  public void setLevel(int level) {
    Level levelComp = getMyLevel();
    if (levelComp.maxLevel <= 0 || level > levelComp.maxLevel || level <= 0) {
      levelComp.currentLevel = level;
    }
  }

  public int getCurrentLevel() {
    return getMyLevel().currentLevel;
  }

  public Island getIsland() {
    return getGameContainer().getIslandByTile(getTile());
  }

  public Inhabitant getInhabitants() {
    return getComponent(Inhabitant.class);
  }

  public BuildingType getBuildingType() {
    return buildingType;
  }
}
