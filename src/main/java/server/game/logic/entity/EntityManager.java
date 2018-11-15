package server.game.logic.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import server.game.GameContainer;
import server.game.logic.entity.building.Building;
import server.game.logic.entity.building.BuildingManager;
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
import server.game.logic.entity.component.Wallet;
import server.game.logic.entity.player.Player;
import server.game.logic.entity.player.PlayerManager;
import server.game.logic.entity.ship.Ship;
import server.game.logic.map.island.Island;
import server.game.logic.map.tile.Tile;
import server.user.User;
import shared.util.ConsoleLog;

public class EntityManager {

  private GameContainer gameContainer;
  private PlayerManager playerManager;
  private Map<Island, Base> islands;
  private Map<Player, Ship> ships;

  /**
   * Creates a EntityManager object for a specific gameContainer where it operates.
   *
   * @param gameContainer the gameContainer where it operates.
   */
  public EntityManager(GameContainer gameContainer) {
    this.gameContainer = gameContainer;
    this.playerManager = new PlayerManager(gameContainer);
    islands = new HashMap<>();
    Base.initLevels();
    InhabitantFarm.initLevels();
    StoneFarm.initLevels();
    WoodFarm.initLevels();
    Church.initLevels();
    Brothel.initLevels();
    Smith.initLevels();
    Park.initLevels();
    SmallTower.initLevels();
    Court.initLevels();
    Alchemist.initLevels();
    Stable.initLevels();
    Tavern.initLevels();
    Range.initLevels();
  }

  /**
   * Gets the buildingManager of an island.
   *
   * @param island the island of which the buildingManager should be gotten of.
   * @return the buildingManager of the island given in the params.
   */
  public BuildingManager getBuildingManager(Island island) {
    if (islands.containsKey(island)) {
      return island.getBuildingManager();
    }
    ConsoleLog.warning("No BuildingManager found for island " + island);
    return null;
  }

  /**
   * Registers an entity to this entityManager.
   *
   * @param entity the entity which should be added.
   * @param cls the entity's corresponding class.
   */
  public void register(Entity entity, Class cls) {
    if (cls.equals(Player.class)) {
      playerManager.register((Player) entity);
      return;
    }
    if (cls.cast(entity) instanceof Base) {
      Base base = (Base) entity;
      Island island = gameContainer.getIslandByTilePos(base.getPosX(), base.getPosY());
      islands.putIfAbsent(island, base);
      return;
    }
    if (cls.cast(entity) instanceof Building) {
      Building building = (Building) entity;

      building.getIsland().getBuildingManager().addBuilding(building, cls);
      return;
    }
  }

  public void register(Island island, Base base) {
    islands.put(island, base);
  }

  /**
   * Gets all the entities of this entityManager by its type.
   *
   * @param typeClass the corresponding class to the type.
   * @param <T> a generic T type.
   * @return all the entities of the determined type in the params as a List.
   */
  public <T extends Entity> List<T> getEntitiesByType(Class<T> typeClass) {

    List<T> ret = (List<T>) playerManager.getPlayers(typeClass);
    if (ret != null) {
      return ret;
    }
    List<T> islandBuildings = new ArrayList<>();

    for (Island island : islands.keySet()) {
      if (island != null) {
        if (island.getBuildings(typeClass) != null) {
          islandBuildings.addAll(island.getBuildings(typeClass));
        }
      }
    }
    if (islandBuildings.size() == 0) {
      islandBuildings = null;
    }

    ret = islandBuildings;
    if (ret != null) {
      return ret;
    }

    return ret;
  }

  /**
   * Gets an island from the position of a tile.
   *
   * @param tile the tile to get the island from.
   * @return the island of the tile given in the params.
   */
  public Island getIslandByTilePosition(Tile tile) {
    for (Island island : islands.keySet()) {
      if (island.isIslandTile(tile)) {
        return island;
      }
    }
    return null;
  }

  /**
   * Gets the player object of a specific user.
   *
   * @param user the user to get the player from.
   * @return the player of the user given in the params.
   */
  public Player getPlayerByUser(User user) {
    List<Player> players = getEntitiesByType(Player.class);
    for (Player player : players) {
      if (player.getUser().equals(user)) {
        return player;
      }
    }
    return null;
  }

  public boolean checkCost(Class toBuild, Wallet wallet) {
    return wallet.sufficientCurrencies(Building.getPrice(toBuild).price);
  }

  /**
   * Gets the owner of an island.
   *
   * @param island the island to get the owner from
   * @return the owner of the island given in the params.
   */
  public Player getOwner(Island island) {
    if (!islands.containsKey(island)) {
      return null;
    }
    if (islands.get(island) == null) {
      return null;
    }
    return islands.get(island).getOwner();

  }

  public List<Base> getBases() {
    return new ArrayList<>(islands.values());
  }

  /**
   * This method gets a base for an island.
   *
   * @param island the island the base should be located on.
   * @return a base, the island, is occupied.
   */
  public Base getBase(Island island) {
    if (islands.containsKey(island)) {
      return islands.get(island);
    }
    return null;
  }

  /**
   * This method gets an entity based on its id.
   *
   * @param cls the entity-type-class.
   * @param id th uuid of the entity.
   * @param <T> extended by Entity.
   * @return the entity or null, if no entity was found.
   */
  public <T extends Entity> T getEntityById(Class<T> cls, UUID id) {
    List<T> entities = getEntitiesByType(cls);
    for (T entity : entities) {
      if (entity.getId().equals(id)) {
        return cls.cast(entity);
      }
    }
    return null;
  }

  /**
   * This method gets the building base on an uuid.
   *
   * @param id the uuid of a building.
   * @return the building or null if no building was found.
   */
  public Building getBuildingById(UUID id) {
    Class cls = Building.class;
    List<Building> entities = getEntitiesByType(cls);
    if (entities == null) {
      entities = new ArrayList<>();
    }
    entities.addAll(islands.values());
    for (Building entity : entities) {
      if (entity.getId().equals(id)) {
        return entity;
      }
    }
    return null;
  }

  /**
   * This method gets the buildings one player is owning.
   *
   * @param owner the owner the buildings are requested for.
   * @return the buildings the player owns.
   */
  public List<Building> getBuildingsByOwner(Player owner) {
    Class cls = Building.class;
    List<Building> entities = getEntitiesByType(cls);
    if (entities == null) {
      entities = new ArrayList<>();
    }
    List<Building> buildings = new ArrayList<>();

    for (Base base : islands.values()) {
      if (base != null) {
        if (base.getOwner().equals(owner)) {
          entities.add(base);
        }
      }
    }

    for (Building entity : entities) {
      if (entity.getOwner().equals(owner)) {
        buildings.add(entity);
      }
    }
    return buildings;
  }

  /**
   * This method gets the buildings one player is owning.
   *
   * @param owner the owner the buildings are requested for.
   * @return the buildings the player owns.
   */
  public int getInhabitantsByOwner(Player owner) {
    List<Building> entities = getBuildingsByOwner(owner);
    int ret = 0;
    for (Building entity : entities) {
      ret += entity.getInhabitants().inhabitants;
    }
    return ret;
  }

  /**
   * This method gets the buildings one player is owning defined by a specific type.
   *
   * @param owner the owner the buildings are requested for.
   * @param type the building type.
   * @return the buildings the player owns.
   */
  public List<Building> getBuildingsByOwnerAndType(Player owner, Class type) {
    List<Building> entities = getEntitiesByType(type);
    if (entities == null) {
      entities = new ArrayList<>();
    }
    List<Building> buildings = new ArrayList<>();
    if (Base.class.isInstance(type)) {
      for (Base base : islands.values()) {
        if (base.getOwner().equals(owner)) {
          entities.add(base);
        }
      }
    }
    for (Building entity : entities) {
      if (entity.getOwner().equals(owner)) {
        buildings.add(entity);
      }
    }
    return buildings;
  }


}
