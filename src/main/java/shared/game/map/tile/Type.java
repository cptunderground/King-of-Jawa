package shared.game.map.tile;

import java.util.Random;

import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import shared.Main;


public enum Type {
  WATER,
  GRASS,
  HILL,
  STONE,
  HOVER;

  public static Type randomType() {
    int pick = new Random().nextInt(values().length);
    return values()[pick];
  }

  /**
   * This method gets a tile-type from a given number in the mapfile.
   *
   * @param x The mapfile-tile-number.
   * @return The tile-type.
   */
  public static Type getTypeFromNumber(int x) {
    if (x == 0) {
      return Type.WATER;
    } else if (x == 1) {
      return Type.GRASS;
    } else if (x == 3) {
      return Type.HILL;
    } else {
      return Type.HILL;
    }
  }

}
