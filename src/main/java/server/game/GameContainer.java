package server.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javafx.scene.paint.Color;
import server.command.cmds.Cheat;
import server.game.highscore.Highscore;
import server.game.highscore.HighscoreElement;
import server.game.highscore.HighscoreManager;
import server.game.lobby.Lobby;
import server.game.logic.ResourceUpdatable;
import server.game.logic.entity.Entity;
import server.game.logic.entity.EntityManager;
import server.game.logic.entity.building.Building;
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
import server.game.logic.entity.component.Resources;
import server.game.logic.entity.player.Player;
import server.game.logic.map.MapManager;
import server.game.logic.map.island.Island;
import server.game.logic.map.tile.Tile;
import server.user.User;
import server.user.UserManager;
import shared.game.building.BuildingType;
import shared.game.map.tile.Type;
import shared.net.protocol.packages.GamePackage;
import shared.util.ConsoleLog;
import shared.util.Pair;


public class GameContainer {

  private Lobby lobby;
  private MapManager mapManager;
  private EntityManager entityManager;
  private GameLoop gameLoop;
  private Color[] colors = {Color.RED, Color.GREEN, Color.YELLOW, Color.DARKBLUE};
  private Map<Integer, Pair<String, Pair<Long, Integer>>> placings;
  private List<User> oldPlayers;
  private long startTime;

  /**
   * Creates a gameContainer object with all its managers and the gameLoop.
   *
   * @param lobby the lobby for which the gameContainer operates.
   * @param mapName the name of the map which shall be loaded.
   */
  public GameContainer(Lobby lobby, String mapName) {
    this.lobby = lobby;
    this.entityManager = new EntityManager(this);
    setupPlayers(lobby.getPlayers(), mapName);
    this.mapManager = new MapManager(this, mapName);
    this.gameLoop = new GameLoop(this);
    new Thread(this.gameLoop).start();
    startTime = System.currentTimeMillis();
    placings = new HashMap<>();
    for (int i = 0; i < 4; i++) {
      placings.put(i, null);
    }
    oldPlayers = new ArrayList<>(lobby.getPlayers());


  }

  /**
   * This methods handles a request to build a building.
   *
   * @param gamePackage the requesting package which contains all the request-information.
   */
  public void handleBuildRequest(GamePackage gamePackage) {
    User user = UserManager.getInstance().getUser(gamePackage.getSocket());
    if (lobby.isPlayer(user)) {
      Player player = entityManager.getPlayerByUser(user);
      String buildingTypeString = gamePackage.getRequestedBuilding();
      BuildingType buildingType = BuildingType.valueOf(buildingTypeString);
      if (buildingType != null) {
        boolean enoughMoney = entityManager
            .checkCost(buildingType.getClassByType(), player.getWallet());
        if (enoughMoney || Cheat.getInstance().isCheatOn()) {
          buildBuilding(buildingType, player, gamePackage.getTilePosition("x"),
              gamePackage.getTilePosition("y"));
        } else {
          user.sendMessage("Not enough MONEY AMK");
        }
      }
    } else {
      user.sendMessage("You are spectating");
    }
  }

  private void buildBuilding(BuildingType buildingType, Player player, int x, int y) {
    boolean buildingBuilt = false;
    Tile tile = getTile(x, y);
    Island island = getIslandByTile(tile);
    Player owner = entityManager.getOwner(island);
    Building builtBuilding = null;
    if (tile != null) {
      if (!tile.isOccupied()) {
        if (tile.getType() != Type.WATER) {
          if (buildingType.equals(BuildingType.BASE)) {
            if (owner == null) {
              buildingBuilt = true;
              Base base = new Base(this, buildingType, player, x, y);
              owner = player;
              tile.setTileBuilding(base);
              builtBuilding = base;
              GameManager.getInstance()
                  .broadcastUpdateIsland(this, mapManager.getIslandId(base.getIsland()),
                      player.getColor());
            }
          }
          if (owner == player) {
            int lim = player.getBuildingLimitation(buildingType);
            int current = getEntityManager()
                .getBuildingsByOwnerAndType(player, buildingType.getClassByType()).size();
            if (current < lim || Cheat.getInstance().isCheatOn()) {
              switch (buildingType) {
                case BASE:
                  break;
                case WOOD:
                  WoodFarm woodFarm = new WoodFarm(this, buildingType, player, x, y);
                  builtBuilding = woodFarm;
                  buildingBuilt = true;
                  tile.setTileBuilding(woodFarm);
                  break;
                case STONE:
                  StoneFarm stoneFarm = new StoneFarm(this, buildingType, player, x, y);
                  builtBuilding = stoneFarm;
                  tile.setTileBuilding(stoneFarm);
                  buildingBuilt = true;
                  break;
                case INHABITANT:
                  InhabitantFarm inhabitantFarm = new InhabitantFarm(this, buildingType, player, x,
                      y);
                  builtBuilding = inhabitantFarm;
                  tile.setTileBuilding(inhabitantFarm);
                  buildingBuilt = true;
                  break;
                case CHURCH:
                  Church church = new Church(this, buildingType, player, x,
                      y);
                  builtBuilding = church;
                  tile.setTileBuilding(church);
                  buildingBuilt = true;
                  break;
                case SMITH:
                  Smith smith = new Smith(this, buildingType, player, x,
                      y);
                  builtBuilding = smith;
                  tile.setTileBuilding(smith);
                  buildingBuilt = true;
                  break;
                case BROTHEL:
                  Brothel brothel = new Brothel(this, buildingType, player, x,
                      y);
                  builtBuilding = brothel;
                  tile.setTileBuilding(brothel);
                  buildingBuilt = true;
                  break;
                case PARK:
                  Park park = new Park(this, buildingType, player, x,
                      y);
                  builtBuilding = park;
                  tile.setTileBuilding(park);
                  buildingBuilt = true;
                  break;
                case SMALLTOWER:
                  SmallTower smallTower = new SmallTower(this, buildingType, player, x,
                      y);
                  builtBuilding = smallTower;
                  tile.setTileBuilding(smallTower);
                  buildingBuilt = true;
                  break;
                case COURT:
                  Court court = new Court(this, buildingType, player, x,
                      y);
                  builtBuilding = court;
                  tile.setTileBuilding(court);
                  buildingBuilt = true;
                  break;
                case ALCHEMIST:
                  Alchemist alchemist = new Alchemist(this, buildingType, player, x,
                      y);
                  builtBuilding = alchemist;
                  tile.setTileBuilding(alchemist);
                  buildingBuilt = true;
                  break;
                case STABLE:
                  Stable stable = new Stable(this, buildingType, player, x,
                      y);
                  builtBuilding = stable;
                  tile.setTileBuilding(stable);
                  buildingBuilt = true;
                  break;
                case TAVERN:
                  Tavern tavern = new Tavern(this, buildingType, player, x,
                      y);
                  builtBuilding = tavern;
                  tile.setTileBuilding(tavern);
                  buildingBuilt = true;
                  break;
                case RANGE:
                  Range range = new Range(this, buildingType, player, x,
                      y);
                  builtBuilding = range;
                  tile.setTileBuilding(range);
                  buildingBuilt = true;
                  break;

                default:
                  ConsoleLog.warning("Irgendwas ist falsch musst "
                      + "selber rausfinden wo ich bin lan!");
              }

            }
          }
        } else {
          player.getUser().sendMessage("You can't build here. This is not your island LOL");
        }

        if (buildingBuilt) {
          if (owner != null) {
            if (!Cheat.getInstance().isCheatOn()) {
              player.getWallet()
                  .decreaseAmount(Building.getPrice(buildingType.getClassByType()).price, 1);
            }
            GameManager.getInstance().updateResourcesToClient(this, player.getUser());
          } else {
            return;
          }
          GameManager.getInstance()
              .broadcastBuild(this, builtBuilding, buildingType.getClassByType(), x, y);
        }
      } else {
        player.getUser()
            .sendMessage("You can't build here, because it's way too deep down there!");
      }
    } else {
      player.getUser()
          .sendMessage("You can't build here. Someone was faster to settle down here");
    }
  }


  public void handlePlayerMapLoaded(GamePackage gamePackage) {
  }

  public List<User> getPlayingUsers() {
    return lobby.getPlayers();
  }

  public List<User> getSpecatingUsers() {
    return lobby.getSpectators();
  }

  /**
   * This method initiates the end of game routine.
   */
  public void stopGame() {
    gameLoop.stop();
    StringBuilder str = new StringBuilder();
    for (int i = 0; i < 4; i++) {
      if (placings.get(i) != null) {
        System.out.println("Hi: " + i);
        str.append("\n ").append(i + 1).append(") ").append(placings.get(i).getKey())
            .append(" | Time needed: ").append(placings.get(i).getElement().getKey())
            .append(" | Points: ")
            .append(placings.get(i).getElement().getElement());
      }
    }
    for (User user : oldPlayers) {
      user.sendMessage(str.toString());
    }
    Pair<String, Pair<Long, Integer>> highest = null;
    for (int i = 0; i < 4; i++) {
      if (placings.get(i) != null) {
        if (highest == null) {
          highest = placings.get(i);
        } else {
          if (highest.getElement().getElement() <= placings.get(i).getElement().getElement()) {
            highest = placings.get(i);
          }
        }
      }
    }
    HighscoreElement highscoreElement = new HighscoreElement(lobby.getName(), highest.getKey(),
        highest.getElement().getElement(), "", highest.getElement().getKey());
    Highscore.getInstance().writeHighscore(highscoreElement);
    List<User> users = UserManager.getInstance().getUsers();
    for (User user : users) {
      HighscoreManager.getInstance().refreshHighscore(user);
    }
    lobby.stopGame();
  }

  public void register(Entity entity, Class cls) {
    entityManager.register(entity, cls);
  }

  public void register(Island island, Base base) {
    entityManager.register(island, base);
  }

  public EntityManager getEntityManager() {
    return entityManager;
  }

  public Island getIslandByTilePosition(int posX, int posY) {
    return entityManager.getIslandByTilePosition(getTile(posX, posY));
  }


  public Tile getTile(int posX, int posY) {
    return mapManager.getTile(posX, posY);
  }

  public Island getIslandByTilePos(int posX, int posY) {
    return getIslandByTile(getTile(posX, posY));
  }

  public Island getIslandByTile(Tile tile) {
    return mapManager.getIslandByTile(tile);
  }

  public Island getIsland(int i) {
    return mapManager.getIsland(i);
  }

  public void addUpdatingBuilding(ResourceUpdatable resourceUpdatable) {
    gameLoop.add(resourceUpdatable);
  }

  public MapManager getMapManager() {
    return mapManager;
  }

  /**
   * Sets up all Users for the upcoming game.
   *
   * @param playingUsers all playing users.
   * @param mapName the name of the map.
   */
  public void setupPlayers(ArrayList<User> playingUsers, String mapName) {
    int i = 0;
    for (User user : playingUsers) {
      new Player(this, user, colors[i]);
      GameManager.getInstance().broadcastGameStart(user, mapName);
      i++;
    }
  }

  /**
   * This method handles th request for a specific building info.
   *
   * @param gamePackage the requesting package which contains all the request-information.
   */
  public void handleBuildingInfoRequest(GamePackage gamePackage) {
    UUID entityId = gamePackage.getEntityUUid();
    User requester = UserManager.getInstance().getUser(gamePackage.getSocket());
    Building building = entityManager.getBuildingById(entityId);
    if (building.getOwner().getUser() == requester) {
      if (getPlayingUsers().contains(requester)) {
        GameManager.getInstance().sendBuildingInfo(requester, building);
      }
    }
  }

  /**
   * This method makes a player lose.
   *
   * @param player the losing player.
   */
  public synchronized void initiateLose(Player player) {
    User user = player.getUser();
    if (getPlayingUsers().contains(user)) {
      for (int i = 3; i >= 0; i--) {
        if (placings.get(i) == null) {
          Pair<String, Pair<Long, Integer>> pair = generatePlayerScore(user);
          placings.put(i, pair);
          lobby.changeUserMode(user);
          user.sendMessage("You are #" + (i + 1));
          break;
        }
      }
      if (getPlayingUsers().size() == 1) {
        User winner = getPlayingUsers().get(0);
        if (placings.get(0) == null) {
          Pair<String, Pair<Long, Integer>> pair = generatePlayerScore(winner);
          placings.put(0, pair);
          lobby.changeUserMode(winner);
          winner.sendMessage("You are #1");
        }
        stopGame();
      }
    }
  }

  /**
   * This method initiates the lose routine for a specific user.
   *
   * @param user the losing user.
   */
  public synchronized void initiateLose(User user) {
    if (getPlayingUsers().contains(user)) {
      for (int i = 3; i >= 0; i--) {
        if (placings.get(i) == null) {
          Pair<String, Pair<Long, Integer>> pair = generatePlayerScore(user, true);
          placings.put(i, pair);
          user.sendMessage("You are #" + (i + 1));
          break;
        }
      }
    }
    if (getPlayingUsers().size() == 1) {
      User winner = getPlayingUsers().get(0);
      if (placings.get(0) == null) {
        Pair<String, Pair<Long, Integer>> pair = generatePlayerScore(winner);
        placings.put(0, pair);
        winner.sendMessage("You are #1");
      }
      stopGame();
    }
  }

  public List<User> getAllUser() {
    return lobby.getUsers();
  }

  /**
   * This method checks for a winner.
   */
  public synchronized void checkForWinner() {
    for (User user : getPlayingUsers()) {
      if (getPlayingUsers().contains(user)) {
        Player player = entityManager.getPlayerByUser(user);
        int points = getPlayerPoints(player);
        if (points >= 3000) {
          for (int i = 0; i < 4; i++) {
            if (placings.get(i) == null) {
              Pair<String, Pair<Long, Integer>> pair = generatePlayerScore(user);
              placings.put(i, pair);
              lobby.changeUserMode(user);
              user.sendMessage("You are #" + (i + 1));
              break;
            }
          }
        }
      }
      if (getPlayingUsers().size() == 1 && !ConsoleLog.debugMode) {
        User winner = getPlayingUsers().get(0);
        if (placings.get(0) == null) {
          Pair<String, Pair<Long, Integer>> pair = generatePlayerScore(winner);
          placings.put(0, pair);
          lobby.changeUserMode(winner);
          winner.sendMessage("You are #1");
        }
        stopGame();
      }
    }
  }

  /**
   * Gets the points by a given Player.
   *
   * @param player the player.
   * @return the requested points
   */
  public int getPlayerPoints(Player player) {
    int points = 0;
    points += entityManager.getInhabitantsByOwner(player) * 4;
    points += player.getWallet().wallet.stone * 2;
    points += player.getWallet().wallet.wood * 2;
    return points;
  }

  /**
   * This method handles a levelup request for a specific building.
   *
   * @param gamePackage the package containing all the important information.
   */
  public void handleLevelUpRequest(GamePackage gamePackage) {
    UUID entityId = gamePackage.getEntityUUid();
    User requester = UserManager.getInstance().getUser(gamePackage.getSocket());
    Player player = entityManager.getPlayerByUser(requester);
    Building building = entityManager.getBuildingById(entityId);
    if (building.getOwner().getUser() == requester) {
      if (getPlayingUsers().contains(requester)) {
        int maxLevel = Building.getLevel(building.getCls()).maxLevel;
        int currentLevel = building.getCurrentLevel();
        Resources upgradeCost = Building.getEconomy(building.getCls())
            .getUpgradePrice(currentLevel);
        if (player.getWallet().sufficientCurrencies(upgradeCost) || Cheat.getInstance()
            .isCheatOn()) {
          if (!Cheat.getInstance().isCheatOn()) {
            player.getWallet().decreaseAmount(upgradeCost, 1);
          }
          building.setLevel(Math.min(currentLevel + 1, maxLevel));
        }
      }
    }
  }

  private Pair<String, Pair<Long, Integer>> generatePlayerScore(User user) {
    long elapsedTime = System.currentTimeMillis() - startTime;
    int points = getPlayerPoints(entityManager.getPlayerByUser(user));
    Pair<Long, Integer> pointsPair = new Pair<>(elapsedTime, points);
    Pair<String, Pair<Long, Integer>> pair = new Pair<>(user.getName(), pointsPair);
    return pair;
  }

  private Pair<String, Pair<Long, Integer>> generatePlayerScore(User user, boolean b) {
    long elapsedTime = System.currentTimeMillis() - startTime;
    int points = getPlayerPoints(entityManager.getPlayerByUser(user));
    Pair<Long, Integer> pointsPair = new Pair<>(elapsedTime, 0);
    Pair<String, Pair<Long, Integer>> pair = new Pair<>(user.getName(), pointsPair);
    return pair;
  }

}