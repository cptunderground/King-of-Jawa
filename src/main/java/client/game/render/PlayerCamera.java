package client.game.render;

import shared.util.Point;

public class PlayerCamera {

  private Point position; // x,y
  private Point resolution; // width, height


  public PlayerCamera(int width, int height) {
    position = new Point(0, 0);
    resolution = new Point(width, height);
  }

  public Point getPosition() {
    return position;
  }

  public Point getResolution() {
    return new Point(resolution);
  }

  public void setResolution(int width, int height) {
    resolution.setX(width);
    resolution.setY(height);
  }
}
