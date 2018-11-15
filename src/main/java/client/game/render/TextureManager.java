package client.game.render;

import client.game.logic.entity.building.BuildingType;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import shared.game.map.tile.Type;

public class TextureManager {

  private static Image[] imgWater = new Image[18];
  private static Image[] imgGrass = new Image[15];
  private static Image imgHill;
  private static WritableImage imgHover;

  private static Image imgBASE;
  private static Image imgWoodFarm;
  private static Image imgInhabitant;
  private static Image imgCourt;
  private static Image imgChurch;
  private static Image imgRange;
  private static Image imgStable;
  private static Image imgBrothel;
  private static Image imgSmith;
  private static Image imgStoneFarm;
  private static Image imgSmallTower;
  private static Image imgAlchemsit;
  private static Image imgPark;
  private static Image imgTavern;

  public static final int tileSize = 128;
  public static double tileScale = 0.5;

  /**
   * This method initiates the tile-textures, if the game is a client instance.
   */
  public static void initTextures() {

    for (int i = 0; i < imgGrass.length; i++) {
      imgGrass[i] = new Image("/tex/grass/" + i + ".png", tileSize * 2,
          tileSize * 2, true, false);
    }
    for (int i = 0; i < imgWater.length; i++) {
      imgWater[i] = new Image("/tex/water/" + i + ".png", tileSize * 2,
          tileSize * 2, true, false);
    }

    imgHill = new Image("/tex/NOTUSED/hillTile.png", tileSize * 2, tileSize,
        false, false);
    imgHover = new WritableImage(tileSize * 2, tileSize);
    Color colorGrey = new Color(0.3, 0.3, 0.3, 0.3);
    for (int x = 0; x < tileSize; x++) {
      for (int y = 0; y < tileSize; y++) {
        int posX = toIsoX(x, y) + tileSize;
        int posY = toIsoY(x, y);
        imgHover.getPixelWriter().setColor(posX, posY, colorGrey);
      }
    }
    initBuildingTextures();
  }

  private static void initBuildingTextures() {
    imgBASE = new Image("/tex/imgBase.png", tileSize * 2,
        1000, true, false);
    imgWoodFarm = new Image("/tex/imgWoodFarm.png", tileSize * 2,
        1000, true, false);
    imgInhabitant = new Image("/tex/imgInhabitant.png", tileSize * 2,
        1000, true, false);
    imgCourt = new Image("/tex/imgCourt.png", tileSize * 2,
        1000, true, false);
    imgChurch = new Image("/tex/imgChurch.png", tileSize * 2,
        1000, true, false);
    imgRange = new Image("/tex/imgRange.png", tileSize * 2,
        1000, true, true);
    imgStable = new Image("/tex/imgStable.png", tileSize * 2,
        1000, true, true);
    imgBrothel = new Image("/tex/imgBrothel.png", tileSize * 2,
        1000, true, true);
    imgSmith = new Image("/tex/imgSmith.png", tileSize * 2,
        1000, true, true);
    imgStoneFarm = new Image("/tex/imgStoneFarm.png", tileSize * 2,
        1000, true, true);
    imgSmallTower = new Image("/tex/imgSmallTower.png",
        tileSize * 2, 1000, true, true);
    imgAlchemsit = new Image("/tex/imgAlchemist.png", tileSize * 2,
        1000, true, true);
    imgPark = new Image("/tex/imgPark.png", tileSize * 2, 1000,
        true, true);
    imgTavern = new Image("/tex/imgTavern.png", tileSize * 2, 1000,
        true, true);

  }

  /**
   * This method get's a texture by its tile-type.
   *
   * @return Texture.F
   */
  public static Image getTileTexture(Type type) {
    switch (type) {
      case HILL:
        return imgHill;
      case HOVER:
        return (Image) imgHover;
      default:
        return imgHill;
    }
  }

  /**
   * returns the Image of a specific textureType.
   *
   * @param type TileType.
   * @param textureType TextureType.
   * @return returns Image of the TextureType.
   */
  public static Image getTileTexture(Type type, int textureType) {
    if (type.equals(Type.GRASS)) {
      return imgGrass[textureType];
    } else {
      return imgWater[textureType];
    }
  }

  /**
   * Gets the building texture from the building type.
   *
   * @param type the building type.
   * @return the building texture as Image.
   */
  public static Image getBuildingTexture(BuildingType type) {
    switch (type) {
      case BASE:
        return imgBASE;
      case WOOD_FARM:
        return imgWoodFarm;
      case INHABITANT_FARM:
        return imgInhabitant;
      case STONE_FARM:
        return imgStoneFarm;
      case COURT:
        return imgCourt;
      case CHURCH:
        return imgChurch;
      case RANGE:
        return imgRange;
      case STABLE:
        return imgStable;
      case BROTHEL:
        return imgBrothel;
      case SMITH:
        return imgSmith;
      case SMALLTOWER:
        return imgSmallTower;
      case PARK:
        return imgPark;
      case ALCHEMIST:
        return imgAlchemsit;
      case TAVERN:
        return imgTavern;
      default:
        return imgWoodFarm;
    }
  }


  public static int toCartesianX(int x, int y) {
    return (2 * y + x) / 2;
  }

  public static double toCartesianX(double x, double y) {
    return (2 * y + x) / 2;
  }

  public static int toCartesianY(int x, int y) {
    return (2 * y - x) / 2;
  }

  public static double toCartesianY(double x, double y) {
    return (2 * y - x) / 2;
  }

  public static int toIsoX(int x, int y) {
    return x - y;
  }

  public static double toIsoX(double x, double y) {
    return x - y;
  }

  public static int toIsoY(int x, int y) {
    return (x + y) / 2;
  }

  public static double toIsoY(double x, double y) {
    return (x + y) / 2;
  }

  public static int getTileSize() {
    return (int) (tileSize * tileScale);
  }
}
