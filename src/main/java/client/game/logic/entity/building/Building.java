package client.game.logic.entity.building;

import client.game.GameContainer;
import client.game.logic.map.tile.TileManager;
import client.game.render.GameRenderable;
import client.game.render.PlayerCamera;
import client.game.render.TextureManager;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Building implements GameRenderable {

  private int posX;
  private int posY;
  private BuildingType buildingType;

  /**
   * Building constructor generates a building with the given fields.
   *
   * @param posX X position of tile where the building is built.
   * @param posY Y position of tile where the building is built.
   * @param buildingClass buildingClass, from building.
   */
  public Building(int posX, int posY, String buildingClass) {
    this.buildingType = BuildingType.getBuildingType(buildingClass);
    this.posX = posX;
    this.posY = posY;
    GameContainer.setTileBuilding(posX, posY, this);
  }

  @Override
  public void render(GraphicsContext gc) {
    PlayerCamera camera = GameContainer.getCamera();
    int tileSize = TextureManager.getTileSize();
    double isoX = TextureManager.toIsoX(posX * tileSize, posY * tileSize);
    double isoY = TextureManager.toIsoY(posX * tileSize, posY * tileSize);
    double posX = camera.getPosition().getX() + isoX;
    double posY = camera.getPosition().getY() + isoY;
    Image image = TextureManager.getBuildingTexture(buildingType);

    gc.drawImage(image, posX - tileSize, posY - (image.getHeight()
            * TextureManager.tileScale - tileSize) - 10,
        tileSize * 2, image.getHeight() * TextureManager.tileScale);

  }

  @Override
  public void renderHover(GraphicsContext gc) {
    PlayerCamera camera = GameContainer.getCamera();
  }

  public String getType() {
    return buildingType.toString();
  }
}
