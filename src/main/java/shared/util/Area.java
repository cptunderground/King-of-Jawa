package shared.util;

public class Area {

  private double posX;
  private double posY;
  private double width;
  private double height;

  /**
   * This is the constructor of an Area, which is often used to store screen areas.
   * @param x The x-position of the area.
   * @param y The y-position of the area.
   * @param w The width of the area.
   * @param h The height of the area.
   */
  public Area(double x, double y, double w, double h) {
    this.posX = x;
    this.posY = y;
    this.width = w;
    this.height = h;
  }

  /**
   * This is the constructor of an Area, which is often used to store screen areas.
   * @param p The source-Area which has to be "copied".
   */
  public Area(Area p) {
    this.posX = p.getX();
    this.posY = p.getY();
    this.width = p.getWidth();
    this.height = p.getHeight();
  }

  public double getX() {
    return posX;
  }

  public void setX(double x) {
    this.posX = x;
  }

  public double getY() {
    return posY;
  }

  public void setY(double y) {
    this.posY = y;
  }

  public double getWidth() {
    return width;
  }

  public void setWidth(double width) {
    this.width = width;
  }

  public double getHeight() {
    return height;
  }

  public void setHeight(double height) {
    this.height = height;
  }

  public boolean isInArea(double pointX, double pointY) {
    return (pointX >= posX && pointX <= posX + width && pointY >= posY && pointY <= posY + height);
  }
}
