package server.game;

import java.util.List;
import javafx.scene.paint.Color;
import server.game.lobby.Lobby;
import server.game.lobby.LobbyManager;
import server.game.logic.entity.building.Building;
import server.game.logic.entity.component.Economy;
import server.game.logic.entity.component.Level;
import server.game.logic.entity.component.Resources;
import server.game.logic.entity.component.Storage;
import server.game.logic.entity.player.Player;
import server.user.User;
import server.user.UserManager;
import shared.net.protocol.Package;
import shared.net.protocol.PackageExecutable;
import shared.net.protocol.ProtocolManagement;
import shared.net.protocol.ProtocolManager;
import shared.net.protocol.packages.GamePackage;
import shared.util.ConsoleLog;

public class GameManager extends ProtocolManagement implements PackageExecutable {

  private static GameManager instance;

  private GameManager() {
    ProtocolManager.getInstance().registerCaller("requestBuild", this);
    ProtocolManager.getInstance().registerCaller("requestBuildingInfo", this);
    ProtocolManager.getInstance().registerCaller("requestLevelUp", this);
    ProtocolManager.getInstance().registerCaller("mapLoaded", this);
  }

  /**
   * This method creates an instance if it doesn't exist. Otherwise it returns the existing one.
   *
   * @return GameManager instance.
   */
  public static GameManager getInstance() {
    if (GameManager.instance == null) {
      GameManager.instance = new GameManager();
    }
    return GameManager.instance;
  }


  @Override
  public void run(Package pkg) {
    GamePackage gamePackage = pkg.cast();
    User user = UserManager.getInstance().getUser(pkg.getSocket());
    Lobby lobby = LobbyManager.getInstance().getUserLobby(user);
    if (lobby != null) {
      GameContainer gameContainer = lobby.getGameContainer();
      switch (gamePackage.getName()) {
        case "requestBuild":
          gameContainer.handleBuildRequest(gamePackage);
          break;
        case "requestLevelUp":
          gameContainer.handleLevelUpRequest(gamePackage);
          break;
        case "requestBuildingInfo":
          gameContainer.handleBuildingInfoRequest(gamePackage);
          break;
        case "mapLoaded":
          gameContainer.handlePlayerMapLoaded(gamePackage);
          break;
        default:
          break;
      }
    } else {
      ConsoleLog.debug("No lobby found for a game-manager.");
    }
  }

  /**
   * This method initiates a game and tells the client to load a specific map.
   *
   * @param user the user, the game has started for.
   * @param mapName the map, which should be loaded.
   */
  public void broadcastGameStart(User user, String mapName) {
    GamePackage gamePackage = new GamePackage(user.getSession().getSocket(),
        "initGameStart");
    gamePackage.setMap(mapName);
    user.sendPackage(gamePackage);
  }

  /**
   * This method updates the resources for every player in the game.
   *
   * @param gameContainer the container the game is running in.
   */
  public void updateResourcesToClients(GameContainer gameContainer) {
    List<Player> players = gameContainer.getEntityManager().getEntitiesByType(Player.class);
    for (Player player : players) {
      User user = player.getUser();
      if (user != null) {
        if (player.getWallet().wallet.coin == 0) {
          gameContainer.initiateLose(player);
        }
        updateResourcesToClient(gameContainer, user);
      }
    }
  }

  /**
   * This method updates the resources displayed in the hud for a specific client.
   *
   * @param gameContainer the container the game is running in.
   * @param user the user, who should receive an resource-update.
   */
  public void updateResourcesToClient(GameContainer gameContainer, User user) {
    Player player = gameContainer.getEntityManager().getPlayerByUser(user);
    if (user != null) {
      if (player != null) {
        GamePackage gamePackage = new GamePackage(user.getSession().getSocket(),
            "updateResources");
        int inhabitants = gameContainer.getEntityManager().getInhabitantsByOwner(player);
        gamePackage.setResourceToUpdate("coin", player.getWallet().wallet.coin);
        gamePackage.setResourceToUpdate("stone", player.getWallet().wallet.stone);
        gamePackage.setResourceToUpdate("wood", player.getWallet().wallet.wood);
        gamePackage.setResourceToUpdate("copper", player.getWallet().wallet.copper);
        gamePackage.setResourceToUpdate("iron", player.getWallet().wallet.iron);
        gamePackage.setResourceToUpdate("inhabitants", inhabitants);
        gamePackage.setResourceToUpdate("points", gameContainer
            .getPlayerPoints(player));

        user.sendPackage(gamePackage);
      }
    }
  }

  /**
   * This method updates the island on the minimap.
   *
   * @param gameContainer the gamecontainer, in which the building should be built.
   * @param island the island, which has now a new color.
   * @param color the new color.
   */
  public void broadcastUpdateIsland(GameContainer gameContainer, int island, Color color) {
    List<User> players = gameContainer.getAllUser();
    for (User user : players) {
      if (user != null) {
        GamePackage gamePackage = new GamePackage(user.getSession().getSocket(),
            "broadcastIslandUpdate");
        gamePackage.setIslandId(island);
        gamePackage.setColor(color.toString());
        user.sendPackage(gamePackage);
      }
    }
  }

  /**
   * This method broadcasts the build instruction for a specific building.
   *
   * @param gameContainer the gamecontainer, in which the building should be built.
   * @param building the building.
   * @param toBuild the type of building, to let the client know what building should be rendered.
   * @param x the x position.
   * @param y the y position.
   */
  public void broadcastBuild(GameContainer gameContainer, Building building, Class toBuild,
      int x, int y) {
    List<User> players = gameContainer.getAllUser();
    for (User user : players) {
      if (user != null) {
        GamePackage gamePackage = new GamePackage(user.getSession().getSocket(),
            "broadcastBuild");
        gamePackage.setBuildingToBuild(toBuild.toString());
        gamePackage.setTilePosition("x", x);
        gamePackage.setTilePosition("y", y);
        gamePackage.setEntityUUid(building.getId());
        gamePackage.setBuildingOwner(building.getOwner().getUser());
        user.sendPackage(gamePackage);
      }
    }
  }

  /**
   * This method sends the building info, requested by the user for a specific building.
   *
   * @param user the requesting user.
   * @param building the requested building.
   */
  public void sendBuildingInfo(User user, Building building) {
    GamePackage gamePackage = new GamePackage(user.getSession().getSocket(),
        "sendBuildingInfo");
    Level level = Building.getLevel(building.getCls());
    Storage storage = Building.getStorage(building.getCls());
    Economy economy = Building.getEconomy(building.getCls());
    int currentLevel = building.getCurrentLevel();
    Resources nxtLevelIncomePm = economy.getIncomePerMinute(currentLevel + 1);
    Resources currentIncomePm = economy.getIncomePerMinute(currentLevel);
    Resources currentPricePerMinute = economy.getPricePerMinute(currentLevel);
    Resources nxtLevlPricePerMinute = economy.getPricePerMinute(currentLevel + 1);
    Resources upgradePrice = economy.getUpgradePrice(currentLevel);

    gamePackage.setBuildingInfo("currentLevel", currentLevel);
    gamePackage.setBuildingInfo("maxLevel", level.maxLevel);
    gamePackage.setBuildingInfo("upgradeCostStone", upgradePrice.stone);
    gamePackage.setBuildingInfo("upgradeCostWood", upgradePrice.wood);
    gamePackage.setBuildingInfo("upgradeCostCoin", upgradePrice.coin);
    gamePackage.setBuildingInfo("nextLevelIncomeStone", nxtLevelIncomePm.stone);
    gamePackage.setBuildingInfo("nextLevelIncomeWood", nxtLevelIncomePm.wood);
    gamePackage.setBuildingInfo("nextLevelIncomeCoin", nxtLevelIncomePm.coin);
    gamePackage.setBuildingInfo("nextLevelCoinCostPM", nxtLevlPricePerMinute.coin);

    gamePackage.setBuildingInfo("currentIncomeStone", currentIncomePm.stone);
    gamePackage.setBuildingInfo("currentIncomeWood", currentIncomePm.wood);
    gamePackage.setBuildingInfo("currentIncomeCoin", currentIncomePm.coin);
    gamePackage.setBuildingInfo("currentCoinCostPM", currentPricePerMinute.coin);

    gamePackage.setEntityUUid(building.getId());
    user.sendPackage(gamePackage);
  }
}