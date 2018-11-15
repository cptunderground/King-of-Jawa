package client.game.render.hud;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import shared.util.Area;

public class ResourceLabel extends HudElement {

  private Font font;
  private Image img;
  private Area positions;
  private String text;
  private Color color;
  private int iconSize = 25;

  public ResourceLabel(String text, int x, int y, String img) {

    this(text, x, y, new Image("/tex/hud/" + img));
  }

  /**
   * Creates a ResourceLabel object with specific text, position and image.
   *
   * @param text  specific text.
   * @param x     x-position.
   * @param y     y-position.
   * @param image specific image.
   */
  public ResourceLabel(String text, int x, int y, Image image) {
    this.img = image;
    this.text = text;
    this.color = new Color(1, 1, 1, 1);
    this.font = Font.font("Arial", 20);
    this.positions = new Area(x, y, getTextWidth(text)
        + iconSize + 5, Math.max(getTextHeight(text, font), iconSize));
    HudElement.add(positions);
    setUpdateOnResize(true);
  }

  @Override
  public void render(GraphicsContext gc) {
    gc.drawImage(img, positions.getX(), positions.getY(), iconSize, iconSize);
    gc.setFill(color);
    gc.fillText(text, positions.getX() + iconSize + 5, positions.getY()
        + getTextHeight(text, font) / 4 + positions.getHeight() / 2);
  }

  @Override
  public void renderHover(GraphicsContext gc) {
    render(gc);
  }

  @Override
  public void handleMouseInteraction(MouseButton btn, int posX, int posY) {

  }

  @Override
  public void updateWindowArea(double width, double height) {

  }

  @Override
  public Area getArea() {
    return positions;
  }

  @Override
  public void setPosition(int x, int y) {
    positions.setX(x);
    positions.setY(y);
  }

  /**
   * Sets a specific text to the ResourceLabel.
   *
   * @param text specific text.
   */
  public void setText(String text) {
    this.text = text;
    this.positions.setWidth(getTextWidth(text) + iconSize + 5);
    this.positions.setHeight(Math.max(getTextHeight(text, font), iconSize));
  }

  public String getText() {
    return text;
  }
}
