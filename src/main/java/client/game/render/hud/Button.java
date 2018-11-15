package client.game.render.hud;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import shared.util.Area;

public class Button extends HudElement {

  private static Image imgDefault = new Image("/tex/hud/button.jpg");
  private Image img;
  private Area positions;
  private String text;
  private ButtonRunnable runnable;
  private Font font = Font.font("Arial", 20);

  /**
   * Creates a Button object with specific text, position, size and boolean.
   *
   * @param text specific text.
   * @param x x-position.
   * @param y y-position.
   * @param width Width.
   * @param height Height.
   * @param runnable boolean, whether a Button is runnable or not.
   */
  public Button(String text, int x, int y, int width, int height, ButtonRunnable runnable) {
    this.runnable = runnable;
    this.img = Button.imgDefault;
    this.positions = new Area(x, y, width, height);
    this.text = text;
    HudElement.add(positions);
    setUpdateOnResize(true);
  }

  @Override
  public void render(GraphicsContext gc) {
    gc.drawImage(img, positions.getX(), positions.getY(),
        positions.getWidth(), positions.getHeight());
    gc.setFill(new Color(0, 0, 0, 1));
    double textWidth = getTextWidth(text);
    gc.fillText(text, positions.getX()
            + (positions.getWidth() - textWidth / 2) / 2,positions.getY()
        + (positions.getHeight() + getTextHeight(text, font) / 4) / 2);
  }

  @Override
  public void renderHover(GraphicsContext gc) {
    gc.drawImage(img, positions.getX(), positions.getY(),
        positions.getWidth(), positions.getHeight());
    gc.setFill(new Color(0.5, 0.5, 0.5, 0.6));
    gc.fillRect(positions.getX(), positions.getY(),
        positions.getWidth(), positions.getHeight());
    double textWidth = getTextWidth(text);
    gc.setFill(new Color(1, 1, 1, 1));
    gc.fillText(text, positions.getX()
        + (positions.getWidth() - textWidth / 2) / 2,positions.getY()
        + (positions.getHeight() + getTextHeight(text, font) / 4) / 2);
  }

  @Override
  public void handleMouseInteraction(MouseButton btn, int posX, int posY) {
    if (btn == MouseButton.PRIMARY) {
      runnable.run(btn, posX, posY);
    }
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
    this.positions.setX(x);
    this.positions.setY(y);
  }

  public void setText(String text) {
    this.text = text;
  }
}
