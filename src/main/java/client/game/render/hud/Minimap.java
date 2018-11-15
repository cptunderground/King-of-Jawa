package client.game.render.hud;

//import client.game.GameManager;

import client.game.GameContainer;
import client.game.logic.map.MapManager;
import client.game.logic.map.island.Island;
import client.game.logic.map.tile.Tile;
import client.game.render.PlayerCamera;
import client.game.render.TextureManager;
import client.ui.WindowManager;

import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import shared.game.map.tile.Type;
import shared.util.Area;
import shared.util.Point;

public class Minimap extends HudElement {


  private Tile[][] tiles;
  private int[][] types;
  private Point minimapPosition;
  private double minimapSize;
  private double minimapTileSize;
  private Area minimapArea;
  private WritableImage mapImage;
  private boolean noCamera;
  private boolean update;

  /**
   * Creates a MiniMap with specific size and tiles.
   *
   * @param size specific size.
   * @param tiles specific tiles.
   */
  public Minimap(double size, int[][] tiles) {
    this.minimapSize = size;
    this.minimapTileSize = minimapSize / tiles.length;
    this.minimapPosition = new Point(0, 0);
    this.types = tiles;
    this.noCamera = true;
    preRenderMapPreview();
  }

  public Minimap(int x, int y, double size) {
    this(x, y, size, true);
  }

  /**
   * Creates a MiniMap with a specific position, size and boolean for resizing.
   *
   * @param x x-position.
   * @param y y-position.
   * @param size specific size.
   * @param updateOnResize boolean, whether a MiniMap is updatable on resizing the window.
   */
  public Minimap(int x, int y, double size, boolean updateOnResize) {
    this(x, y, size, MapManager.getTiles(), updateOnResize,
        false);
  }

  /**
   * Creates a MiniMap with a specific position, size and boolean for resizing and camera.
   *
   * @param x x-position.
   * @param y y-position.
   * @param size specific size.
   * @param tiles specific tiles.
   * @param updateOnResize boolean, whether a MiniMap is updatable on resizing the window.
   * @param noCamera boolean, whether a MiniMap has a camera.
   */
  public Minimap(int x, int y, double size, Tile[][] tiles,
      boolean updateOnResize, boolean noCamera) {
    this.minimapSize = size;
    this.minimapTileSize = minimapSize / tiles.length;
    this.minimapPosition = new Point(x, y);
    this.tiles = tiles;
    this.noCamera = noCamera;
    setUpdateOnResize(updateOnResize);
    this.minimapArea = new Area(minimapPosition.getX(), minimapPosition.getY(), minimapSize,
        minimapSize);
    HudElement.add(minimapArea);
    int localSize = (int) minimapSize;
    mapImage = new WritableImage(localSize, localSize);
    preRenderMap();
  }

  private void preRenderMap() {
    List<Island> islands = MapManager.getIslands();

    for (int i = 0; i < tiles.length; i++) {
      for (int j = 0; j < tiles[i].length; j++) {
        if (tiles[i][j] != null) {
          double miniTileSize = Math.round(minimapTileSize);
          if (minimapTileSize < 1) {
            miniTileSize = minimapTileSize;
          }
          double x = tiles[i][j].getX() * miniTileSize;
          double y = tiles[i][j].getY() * miniTileSize;
          if (tiles[i][j].getType() == Type.WATER) {
            Geometry.drawRectangle((int) x, (int) y, Math.max((int) miniTileSize, 1),
                Math.max((int) miniTileSize, 1), mapImage,
                new Color(0.9607843f, 0.9607843f, 0.8627451f, .5));
          } else {
            for (int k = 0; k < islands.size(); k++) {
              Island island = islands.get(k);
              if (island.isIslandTile(tiles[i][j])) {
                Geometry.drawRectangle((int) x, (int) y, Math.max((int) miniTileSize, 1),
                    Math.max((int) miniTileSize, 1), mapImage, island.getColor());
              }
            }
          }
        }
      }
    }
  }

  private void preRenderMapPreview() {
    int localSize = (int) minimapSize;
    mapImage = new WritableImage(localSize, localSize);
    for (int i = 0; i < types.length; i++) {
      for (int j = 0; j < types[i].length; j++) {
        if (Type.getTypeFromNumber(types[i][j]) != null) {
          double mapTileSize = Math.round(minimapTileSize);
          if (minimapTileSize < 1) {
            mapTileSize = minimapTileSize;
          }
          double x = i * mapTileSize;
          double y = j * mapTileSize;
          if (Type.getTypeFromNumber(types[i][j]) == Type.WATER) {
            Geometry.drawRectangle((int) x, (int) y, Math.max((int) mapTileSize, 1),
                Math.max((int) mapTileSize, 1), mapImage,
                new Color(0.9607843f, 0.9607843f, 0.8627451f, .5));
          } else {
            Geometry.drawRectangle((int) x, (int) y, Math.max((int) mapTileSize, 1),
                Math.max((int) mapTileSize, 1), mapImage, Color.FORESTGREEN);
          }
        }
      }
    }
  }

  @Override
  public void render(GraphicsContext gc) {
    //Render Map;
    if (update) {
      preRenderMap();
      update = false;
    }
    gc.drawImage(mapImage, minimapPosition.getX(), minimapPosition.getY(), minimapSize,
        minimapSize);
    if (minimapArea != null) {
      minimapArea.setX(minimapPosition.getX());
      minimapArea.setY(minimapPosition.getY());
    }

    //render Camera area
    if (!noCamera) {
      PlayerCamera camera = GameContainer.getCamera();
      double posX = camera.getPosition().getX();
      double posY = camera.getPosition().getY();
      double width = camera.getResolution().getX();
      double height = camera.getResolution().getY();
      double x = TextureManager.toCartesianX(-posX, -posY)
          / TextureManager.getTileSize() * minimapTileSize;
      double y = TextureManager.toCartesianY(-posX, -posY)
          / TextureManager.getTileSize() * minimapTileSize;
      double x1 =
          TextureManager.toCartesianX(-posX + width, -posY)
              / TextureManager.getTileSize() * minimapTileSize;
      double y1 =
          TextureManager.toCartesianY(-posX + width, -posY)
              / TextureManager.getTileSize() * minimapTileSize;
      double x2 = TextureManager.toCartesianX(-posX + width, -posY + height)
          / TextureManager.getTileSize()
          * minimapTileSize;
      double y2 = TextureManager.toCartesianY(-posX + width, -posY + height)
          / TextureManager.getTileSize()
          * minimapTileSize;
      double x3 =
          TextureManager.toCartesianX(-posX, -posY + height)
              / TextureManager.getTileSize() * minimapTileSize;
      double y3 =
          TextureManager.toCartesianY(-posX, -posY + height)
              / TextureManager.getTileSize() * minimapTileSize;
      gc.setStroke(Color.BLACK);
      gc.setLineWidth(1);
      gc.strokeLine(minimapPosition.getX() + x, minimapPosition.getY() + y,
          minimapPosition.getX() + x1, minimapPosition.getY() + y1);
      gc.strokeLine(minimapPosition.getX() + x1, minimapPosition.getY() + y1,
          minimapPosition.getX() + x2, minimapPosition.getY() + y2);
      gc.strokeLine(minimapPosition.getX() + x2, minimapPosition.getY() + y2,
          minimapPosition.getX() + x3, minimapPosition.getY() + y3);
      gc.strokeLine(minimapPosition.getX() + x3, minimapPosition.getY() + y3,
          minimapPosition.getX() + x, minimapPosition.getY() + y);
    }
  }

  @Override
  public void renderHover(GraphicsContext gc) {
    render(gc);
  }

  @Override
  public void handleMouseInteraction(MouseButton btn, int posX, int posY) {
    int tileSize = TextureManager.getTileSize();
    handleMouseInteraction(btn, posX, posY, tileSize);
  }

  /**
   * This method handles the mouseInteraction.
   *
   * @param btn MouseButton.
   * @param posX xPosition.
   * @param posY yPosition.
   * @param tileSize size of the Tile.
   */
  public void handleMouseInteraction(MouseButton btn, int posX, int posY, int tileSize) {
    if (btn == MouseButton.PRIMARY) {

      int width = GameContainer.getCamera().getResolution().getX();
      int height = GameContainer.getCamera().getResolution().getY();
      double x1 =
          TextureManager.toCartesianX(-posX + width, -posY) / tileSize * minimapTileSize;
      double y1 =
          TextureManager.toCartesianY(-posX + width, -posY) / tileSize * minimapTileSize;
      double x2 =
          TextureManager.toCartesianX(-posX, -posY + height) / tileSize * minimapTileSize;
      double y2 =
          TextureManager.toCartesianY(-posX, -posY + height)
              / TextureManager.getTileSize() * minimapTileSize;

      double x3 = TextureManager.toCartesianX(-posX, -posY) / tileSize * minimapTileSize;
      double y3 = TextureManager.toCartesianY(-posX, -posY) / tileSize * minimapTileSize;
      double x4 = TextureManager.toCartesianX(-posX + width, -posY + height) / tileSize
          * minimapTileSize;
      double y4 = TextureManager.toCartesianY(-posX + width, -posY + height) / tileSize
          * minimapTileSize;
      double distWidth = Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
      double distHeight = Math.sqrt(Math.pow(x3 - x4, 2) + Math.pow(y3 - y4, 2));

      double x = WindowManager.getGameController().getMouseX() - distWidth / 2;
      double y = WindowManager.getGameController().getMouseY() - distHeight / 2 + (y3 - y1);
      if (minimapArea.isInArea(x - (x3 - x1), y)) {
        double relativeCursorX = x - minimapPosition.getX();
        double relativeCursorY = y - minimapPosition.getY();
        double miniTileSize = Math.round(minimapTileSize);
        if (minimapTileSize < 1) {
          miniTileSize = minimapTileSize;
        }
        int tilePositionX = (int) (relativeCursorX / miniTileSize);
        int tilePositionY = (int) (relativeCursorY / miniTileSize);

        GameContainer.getGameRender()
            .setPosition(TextureManager.toIsoX(tilePositionX, tilePositionY) * tileSize,
                TextureManager.toIsoY(tilePositionX, tilePositionY) * tileSize);
      }
    }
  }

  @Override
  public Area getArea() {
    return minimapArea;
  }

  @Override
  public void setPosition(int x, int y) {
    this.minimapPosition.setX(x);
    this.minimapPosition.setY(y);
  }

  @Override
  public void updateWindowArea(double width, double height) {

  }

  public Image getMapImage() {
    return mapImage;
  }

  public void update() {
    update = true;
  }
}
