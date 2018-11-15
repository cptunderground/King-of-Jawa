package client.game.render;

import client.game.GameContainer;
import client.game.GameManager;
import client.game.logic.map.Map;
import client.game.logic.map.MapManager;
import client.game.logic.map.tile.Tile;
import client.game.logic.map.tile.TileManager;
import client.ui.WindowManager;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import shared.game.building.BuildingType;

public class GameRender implements Renderable {

  private Tile[][] tilesToRender;

  /**
   * Creates a GameRender with the PlayerCamera and map.
   */
  public GameRender() {
    PlayerCamera camera = GameContainer.getCamera();
    int posX = -camera.getPosition().getX();
    int posY = -camera.getPosition().getY();
    int width = camera.getResolution().getX();
    int height = camera.getResolution().getY();

    this.tilesToRender = getTilesInCameraArea(posX, posY, width, height);
  }

  private Tile[][] getTilesInCameraArea(int posX, int posY, int width, int height) {
    return GameContainer.getTilesInCameraArea(posX, posY, width, height);
  }

  /**
   * Renders the GraphicsContent.
   *
   * @param gc GraphicsContent that wants to be rendered.
   */
  public void render(GraphicsContext gc) {
    //Resetting the canvas
    gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());

    //Render Map!
    for (int i = 0; i < tilesToRender.length; i++) {
      int x = tilesToRender.length - 1 - i;
      for (int j = 0; j < tilesToRender[i].length; j++) {
        int y = tilesToRender[x].length - 1 - j;
        if (tilesToRender[i][j] != null) {
          tilesToRender[i][j].render(gc);
        }
      }
    }

    //Render Entities!
    //TODO ADD.
  }

  /**
   * Updates Tiles in CameraArea.
   */
  public void updateTilesToRender() {
    PlayerCamera camera = GameContainer.getCamera();
    int posX = -camera.getPosition().getX();
    int posY = -camera.getPosition().getY();
    int width = camera.getResolution().getX();
    int height = camera.getResolution().getY();

    this.tilesToRender = GameContainer.getTilesInCameraArea(posX, posY, width, height);
  }


  /**
   * Updates the position of the camera and renders the tiles in the position.
   *
   * @param x x-Position.
   * @param y y-Position.
   */
  public void updatePosition(int x, int y) {
    PlayerCamera camera = GameContainer.getCamera();
    camera.getPosition().setX(camera.getPosition().getX() + 10 * x);
    camera.getPosition().setY(camera.getPosition().getY() + 10 * y);
    updateTilesToRender();
  }

  /**
   * Sets the position of the camera.
   *
   * @param x x-Position.
   * @param y y-Position.
   */
  public void setPosition(int x, int y) {
    PlayerCamera camera = GameContainer.getCamera();
    camera.getPosition().setX(-x);
    camera.getPosition().setY(-y);
    updateTilesToRender();
  }

  /**
   * Gets the tile where the mouse is hovering over.
   *
   * @return Tile at the position, where the mouse is hovering.
   */
  public Tile getHoveredTile() {
    PlayerCamera camera = GameContainer.getCamera();
    double mouseX1 = WindowManager.getGameController().getMouseX();
    double mouseY1 = WindowManager.getGameController().getMouseY();
    HudRender hudRender = GameContainer.getHudRender();
    if (!hudRender.isInArea(mouseX1, mouseY1)) {
      double mouseX = mouseX1 - camera.getPosition().getX();
      double mouseY = mouseY1 - camera.getPosition().getY();
      int x = (int) (TextureManager.toCartesianX(mouseX, mouseY) / TextureManager.getTileSize());
      int y = (int) (TextureManager.toCartesianY(mouseX, mouseY) / TextureManager.getTileSize());
      return MapManager.getLoadedMap().getTile(x, y);
    } else {
      return null;
    }
  }

  /**
   * Sends the specific MouseClick.
   *
   * @param b MouseClick, which wants to be sent.
   */
  public void sendClick(MouseButton b) {
    if (getHoveredTile() != null) {
      GameContainer.getHudRender().removeBuildingInfo();
      if (b.equals(MouseButton.PRIMARY)) {
        BuildingType buildingMode = GameContainer.getBuildingMode();
        if (buildingMode != BuildingType.NONE) {
          GameManager.getInstance().requestBuild(getHoveredTile());
        }
        if (!getHoveredTile().isOccupied()) {
          getHoveredTile().interact(b);
        }
      } else if (b.equals(MouseButton.SECONDARY)) {
        openBuildingInfo(getHoveredTile());
      }
    }
  }

  private void openBuildingInfo(Tile hoveredTile) {
    if (hoveredTile != null) {
      GameContainer.getHudRender().requestRenderBuildingInfo(hoveredTile.getBuilding());
    }
  }

}
