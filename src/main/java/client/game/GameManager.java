package client.game;

import client.Client;
import client.game.logic.entity.EntityManager;
import client.game.logic.entity.building.Building;
import client.game.logic.map.tile.Tile;

import client.sound.Player;
import client.sound.Sound;
import client.ui.KeyBinder;
import client.user.User;
import client.user.UserManager;
import java.util.UUID;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import shared.game.building.BuildingType;
import shared.net.protocol.Package;
import shared.net.protocol.PackageExecutable;
import shared.net.protocol.PackageManager;
import shared.net.protocol.ProtocolManagement;
import shared.net.protocol.ProtocolManager;
import shared.net.protocol.packages.GamePackage;


public class GameManager extends ProtocolManagement implements PackageExecutable {

  private static GameManager instance;
  private int seasoundId;


  private GameManager() {
    ProtocolManager.getInstance().registerCaller("initGameStart", this);
    ProtocolManager.getInstance().registerCaller("updateResources", this);
    ProtocolManager.getInstance().registerCaller("broadcastBuild", this);
    ProtocolManager.getInstance().registerCaller("broadcastIslandUpdate", this);
    ProtocolManager.getInstance().registerCaller("sendBuildingInfo", this);

    ProtocolManager.getInstance().registerCaller("updateResource", this);
    seasoundId = -1;
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
  public <T extends Package> void run(T pkg) {
    GamePackage gamePackage = pkg.cast();

    switch (gamePackage.getName()) {
      case "initGameStart":
        initGame(gamePackage);
        break;
      case "broadcastBuild":
        build(gamePackage);
        break;
      case "updateResources":
        updateResources(gamePackage);
        break;
      case "broadcastIslandUpdate":
        updateIsland(gamePackage);
        break;
      case "sendBuildingInfo":
        showBuildingInfo(gamePackage);
        break;
      default:
        break;
    }
  }

  private void showBuildingInfo(GamePackage gamePackage) {
    GameContainer.getHudRender().setRenderBuildingInfo(gamePackage);
  }

  private void updateResources(GamePackage p) {
    if (GameContainer.getHudRender() != null) {
      GameContainer.getHudRender().updateCoins(p.getResourceToUpdate("coin"));
      GameContainer.getHudRender().updateWood(p.getResourceToUpdate("wood"));
      GameContainer.getHudRender().updateStone(p.getResourceToUpdate("stone"));
      GameContainer.getHudRender().updatePoints(p.getResourceToUpdate("points"));
      GameContainer.getHudRender().updateInhabitants(
          p.getResourceToUpdate("inhabitants"));
    }
  }

  private void build(GamePackage p) {
    int posX = p.getTilePosition("x");
    int posY = p.getTilePosition("y");
    UUID ownerId = p.getBuildingOwner();
    User owner = UserManager.getInstance().getUser(ownerId);
    if (owner == Client.getInstance().getLocalUser()) {
      Player.playSound(new Sound("build.mp3"), 0.7f);
    }
    String buildingClass = p.getBuildingToBuild();
    UUID entityUUid = p.getEntityUUid();
    EntityManager.register(entityUUid, new Building(posX, posY, buildingClass));
  }

  private void updateIsland(GamePackage p) {
    int islandId = p.getIslandId();
    Color color = p.getColor();
    if (GameContainer.getIslands() != null) {
      GameContainer.getIslands().get(islandId).setColor(color);
    }
    if (GameContainer.getHudRender() != null) {
      GameContainer.getHudRender().updateMinimap();
    }
  }

  private void initGame(GamePackage p) {
    GameContainer.init(p.getMapName());
    seasoundId = Player.playSound(new Sound("seasound.mp3"),0.02, true);
  }

  /**
   * This method requests to build a building.
   *
   * @param hoveredTile the tile the building should be built on.
   */
  public void requestBuild(Tile hoveredTile) {
    GamePackage gamePackage = new GamePackage(Client.getInstance().getSocket(), "requestBuild");
    BuildingType buildingMode = GameContainer.getBuildingMode();
    gamePackage.setRequestedBuilding(buildingMode);
    gamePackage.setTilePosition("x", hoveredTile.getX());
    gamePackage.setTilePosition("y", hoveredTile.getY());
    PackageManager.getInstance().add(gamePackage);
  }

  /**
   * This method requests the info for a specific building.
   *
   * @param buildingId the building uuid to identify the building.
   */
  public void requestBuildingInfo(UUID buildingId) {
    GamePackage gamePackage = new GamePackage(Client.getInstance().getSocket(),
        "requestBuildingInfo");
    gamePackage.setEntityUUid(buildingId);
    PackageManager.getInstance().add(gamePackage);
  }

  /**
   * This method requests a level-up for a specific building.
   *
   * @param currentBuilding the building which should be getting a level up.
   */
  public void requestLevelUp(Building currentBuilding) {
    GamePackage gamePackage = new GamePackage(Client.getInstance().getSocket(),
        "requestLevelUp");
    gamePackage.setEntityUUid(EntityManager.getBuildingId(currentBuilding));
    PackageManager.getInstance().add(gamePackage);
  }

  public int getSeasoundId() {
    return seasoundId;
  }
}

