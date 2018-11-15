package client.game;

import client.Client;
import client.game.chat.IngameChat;
import client.game.lobby.Lobby;
import client.game.logic.entity.EntityManager;
import client.game.logic.entity.building.Building;
import client.game.logic.map.MapManager;
import client.game.logic.map.island.Island;
import client.game.logic.map.tile.Tile;
import client.game.render.GameRender;
import client.game.render.HudRender;
import client.game.render.PlayerCamera;
import client.game.render.TextureManager;
import client.sound.Player;
import client.ui.KeyBinder;
import client.ui.Window;
import client.ui.WindowManager;
import client.ui.controller.GameController;

import java.util.List;
import javafx.application.Platform;
import javafx.scene.input.KeyCode;
import shared.game.building.BuildingType;

public class GameContainer {

  private static GameRender gameRender;
  private static HudRender hudRender;

  private static PlayerCamera playerCamera;
  private static EntityManager entityManager;

  private static boolean initiated = false;
  private static BuildingType buildingMode;

  protected static void init(String mapName) {
    Platform.runLater(() -> {
      WindowManager.setWindow(Window.GAME);

      MapManager.loadMap(mapName);
      setBuildingMode(BuildingType.NONE);
      KeyBinder.unbindAll();
      KeyBinder.bind(KeyCode.UP, () -> {
        gameRender.updatePosition(0, 10);
      });
      KeyBinder.bind(KeyCode.DOWN, () -> {
        gameRender.updatePosition(0, -10);
      });
      KeyBinder.bind(KeyCode.LEFT, () -> {
        gameRender.updatePosition(10, 0);
      });
      KeyBinder.bind(KeyCode.RIGHT, () -> {
        gameRender.updatePosition(-10, 0);
      });
      KeyBinder.bind(KeyCode.PLUS, () -> {
        if (gameRender != null) {
          TextureManager.tileScale = Math.min(1, TextureManager.tileScale + 0.1);
          gameRender.updateTilesToRender();
        }
      });
      KeyBinder.bind(KeyCode.MINUS, () -> {
        TextureManager.tileScale = Math.max(0.1, TextureManager.tileScale - 0.1);
        if (gameRender != null) {
          gameRender.updateTilesToRender();
        }
      });
      KeyBinder.bind(WindowManager.getKeyCombinationAltEnter(), WindowManager::toggleFullScreen);
      KeyBinder.bind(KeyCode.M, Player::toggleMute);
      KeyBinder.bind(KeyCode.ENTER, IngameChat::toggleInput);

      entityManager = new EntityManager();
      GameController gameController = WindowManager.getGameController();
      playerCamera = new PlayerCamera((int) gameController.getWidth(),
          (int) gameController.getHeight());
      gameRender = new GameRender();
      hudRender = new HudRender();
      WindowManager.initGame(gameRender, hudRender);
    });


  }

  protected static void reset() {
    GameController gameController = WindowManager.getGameController();
    gameRender = new GameRender();
    hudRender = new HudRender();
    playerCamera = new PlayerCamera((int) gameController.getWidth(),
        (int) gameController.getHeight());
    WindowManager.setWindow(Window.MAIN);


  }

  public static GameRender getGameRender() {
    return gameRender;
  }

  public static HudRender getHudRender() {
    return hudRender;
  }

  public static PlayerCamera getCamera() {
    return playerCamera;
  }

  public static Lobby getLobby() {
    return Client.getInstance().getLocalUser().getLobby();
  }

  public static Tile[][] getTilesInCameraArea(int posX, int posY, int width, int height) {
    return MapManager.getLoadedMap().getTilesInCameraArea(posX, posY, width, height);
  }

  public static List<Island> getIslands() {
    return MapManager.getIslands();
  }

  public static BuildingType getBuildingMode() {
    return buildingMode;
  }

  public static void setBuildingMode(BuildingType buildingMode) {
    GameContainer.buildingMode = buildingMode;
  }

  public static void setTileBuilding(int posX, int posY, Building building) {
    MapManager.getLoadedMap().getTile(posX, posY).setBuilding(building);
  }
}
