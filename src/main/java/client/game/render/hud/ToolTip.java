package client.game.render.hud;

import java.util.Map;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.text.Font;
import shared.util.Area;
import shared.util.Point;

public class ToolTip extends HudElement {

  private Area positions;
  private String text;
  private Font font = Font.font("Arial", 20);

  @Override
  public void render(GraphicsContext gc) {

  }

  @Override
  public void renderHover(GraphicsContext gc) {

  }

  @Override
  public void handleMouseInteraction(MouseButton btn, int posX, int posY) {

  }

  @Override
  public void updateWindowArea(double width, double height) {

  }

  @Override
  public Area getArea() {
    return null;
  }

  @Override
  public void setPosition(int x, int y) {

  }
}
