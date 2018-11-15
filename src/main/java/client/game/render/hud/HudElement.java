package client.game.render.hud;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import shared.util.Area;

public abstract class HudElement {

  private static List<Area> hudAreas = new ArrayList<>();
  private boolean updateOnResize;

  public abstract void render(GraphicsContext gc);

  public abstract void renderHover(GraphicsContext gc);

  public abstract void handleMouseInteraction(MouseButton btn, int posX, int posY);

  public abstract void updateWindowArea(double width, double height);

  public abstract Area getArea();

  public static List<Area> getHudAreas() {
    return hudAreas;
  }

  /**
   * This method adds an specific area to the list of HudAreas.
   *
   * @param area specific Area.
   */
  public static void add(Area area) {
    if (!hudAreas.contains(area)) {
      hudAreas.add(area);
    }
  }

  /**
   * Removes an HudArea, if it exists.
   *
   * @param area specific Area.
   */
  public static void remove(Area area) {
    if (hudAreas.contains(area)) {
      hudAreas.remove(area);
    }
  }

  public boolean isUpdateOnResize() {
    return updateOnResize;
  }

  public void setUpdateOnResize(boolean b) {
    updateOnResize = b;
  }

  public abstract void setPosition(int x, int y);

  public static double getTextWidth(String text) {
    return getTextWidth(text, Font.font("Arial", 20));

  }

  /**
   * Returns TextWidth.
   *
   * @param text specific Text.
   * @param font specific Font.
   * @return Returns TextWidth.
   */
  public static double getTextWidth(String text, Font font) {
    final Text textMeassure = new Text(text);
    textMeassure.setFont(font);

    return textMeassure.getLayoutBounds().getWidth();

  }

  public static double getTextHeight(String text) {
    return getTextHeight(text, Font.font("Arial", 20));

  }

  /**
   * Returns TextHeight.
   *
   * @param text specific Text.
   * @param font specific Font.
   * @return Returns TextHeight.
   */
  public static double getTextHeight(String text, Font font) {
    final Text textMeassure = new Text(text);
    textMeassure.setFont(font);

    return textMeassure.getLayoutBounds().getHeight();

  }
}
