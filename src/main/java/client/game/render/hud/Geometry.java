package client.game.render.hud;

import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class Geometry {

  /**
   * Draws an Rectangle.
   *
   * @param x x-position.
   * @param y y-position.
   * @param w Width.
   * @param h Height.
   * @param writableImage WriteAbleImage.
   * @param c Color.
   */
  public static void drawRectangle(int x, int y, int w, int h,
                                   WritableImage writableImage, Color c) {
    PixelWriter pixelWriter = writableImage.getPixelWriter();
    for (int i = x; i < x + w; i++) {
      for (int j = y; j < y + h; j++) {
        pixelWriter
            .setColor(Math.min(i, (int) writableImage.getWidth() - 1),
                Math.min(j, (int) writableImage.getHeight() - 1), c);
      }
    }
  }
}
