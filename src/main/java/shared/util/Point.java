package shared.util;

public class Point {

  private int pointX;
  private int pointY;

  public Point(int x, int y) {
    this.pointX = x;
    this.pointY = y;
  }

  public Point(Point p) {
    this.pointX = p.getX();
    this.pointY = p.getY();
  }

  public int getX() {
    return pointX;
  }

  public void setX(int x) {
    this.pointX = x;
  }

  public int getY() {
    return pointY;
  }

  public void setY(int y) {
    this.pointY = y;
  }
}
