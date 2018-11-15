package client.game.logic.map.tile;

import client.game.GameContainer;
import client.game.logic.entity.building.Building;
import client.game.logic.map.MapManager;
import client.game.render.GameRenderable;
import client.game.render.PlayerCamera;
import client.game.render.TextureManager;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import shared.game.map.tile.Type;
import shared.util.ConsoleLog;
import shared.util.Point;

public class Tile implements GameRenderable {

  private Point point;
  private Type tileType;
  private Building tileBuilding;
  private int textureType = 12;

  public Tile(int x, int y, Type type) {
    this.point = new Point(x, y);
    this.tileType = type;
  }

  public int getX() {
    return point.getX();
  }

  public int getY() {
    return point.getY();
  }

  public Type getType() {
    return tileType;
  }

  public boolean isOccupied() {
    return (tileBuilding != null);
  }


  @Override
  public void render(GraphicsContext gc) {
    PlayerCamera camera = GameContainer.getCamera();
    int tileSize = TextureManager.getTileSize();
    double isoX = TextureManager.toIsoX(getX() * tileSize, getY() * tileSize);
    double isoY = TextureManager.toIsoY(getX() * tileSize, getY() * tileSize);
    double posX = camera.getPosition().getX() + isoX;
    double posY = camera.getPosition().getY() + isoY;

    if (tileType == Type.HILL) {
      gc.drawImage(getTexture(Type.GRASS, textureType), posX - tileSize,
          posY - (getTexture(Type.GRASS, textureType).getHeight()
              * TextureManager.tileScale - tileSize),
          tileSize
              * 2, getTexture(Type.GRASS, textureType).getHeight()
              * TextureManager.tileScale);
    }
    gc.drawImage(getTexture(tileType, textureType), posX - tileSize,
        posY - (getTexture(tileType, textureType).getHeight() * TextureManager.tileScale
            - tileSize),
        tileSize * 2, getTexture(tileType, textureType).getHeight() * TextureManager.tileScale);

    if (tileBuilding != null) {
      tileBuilding.render(gc);
    }

    if (isHovering()) {
      renderHover(gc);
      if (tileBuilding != null) {
        tileBuilding.renderHover(gc);
      }
    }
    gc.setFill(new Color(1, 1, 1, 1));

  }

  private Image getTexture(Type tileType, int textureType) {
    if (tileType.equals(Type.GRASS) || tileType.equals(Type.WATER)) {
      return TextureManager.getTileTexture(tileType, textureType);
    } else {
      return getTexture(tileType);
    }
  }

  private Image getTexture(Type type) {
    return TextureManager.getTileTexture(type);
  }

  @Override
  public void renderHover(GraphicsContext gc) {
    int tileSize = TextureManager.getTileSize();
    PlayerCamera camera = GameContainer.getCamera();
    double isoX = TextureManager.toIsoX(getX() * tileSize, getY() * tileSize);
    double isoY = TextureManager.toIsoY(getX() * tileSize, getY() * tileSize);
    double posX = camera.getPosition().getX() + isoX;
    double posY = camera.getPosition().getY() + isoY;

    gc.drawImage(getTexture(Type.HOVER), posX - tileSize,
        posY - (getTexture(Type.HOVER).getHeight() * TextureManager.tileScale - tileSize),
        tileSize * 2, getTexture(Type.HOVER).getHeight() * TextureManager.tileScale);

    if (ConsoleLog.debugMode) {
      gc.setFill(new Color(1, 1, 1, 1));
      double textWidth = gc.getFont().getSize() * (getX() + "|" + getY()).length();
      gc.fillText(getX() + "|" + getY(), posX - textWidth / 4, posY + tileSize / 2);
    }
  }

  public boolean isHovering() {
    return (GameContainer.getGameRender().getHoveredTile() == this);
  }


  /**
   * This method will be called when the users clicks on this Tile.
   *
   * @param b MouseButton.
   */
  public void interact(MouseButton b) {
    if (ConsoleLog.debugMode) {
      ConsoleLog.debug(textureType + "");
      int[][] neighbours = MapManager.getLoadedMap().getNeighbours(point.getX(), point.getY());
      StringBuilder asd = new StringBuilder("{" + neighbours[0][2]);
      for (int i = 1; i < neighbours.length; i++) {
        asd.append(", ").append(neighbours[i][2]);
      }
      asd.append("};");

      StringSelection selection = new StringSelection(asd.toString());
      Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
      clipboard.setContents(selection, selection);
    }
  }

  public void setBuilding(Building building) {
    this.tileBuilding = building;
  }

  public Building getBuilding() {
    return tileBuilding;
  }

  public void setTextureId(int textureId) {
    this.textureType = textureId;
  }
}
