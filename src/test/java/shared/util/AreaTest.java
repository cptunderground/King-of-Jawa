package shared.util;

import org.junit.Assert;
import org.junit.Test;

public class AreaTest {

  private double originX = 0;
  private double originY = 0;
  private double width = 800;
  private double height = 500;
  private Area area = new Area(originX, originY, width, height);

  /**
   * Tests the initialised area, if the values are set correctly.
   */
  @Test
  public void initArea() {
    Assert.assertEquals(originX, area.getX(), 0);
    Assert.assertEquals(originY, area.getY(), 0);
    Assert.assertEquals(width, area.getWidth(), 0);
    Assert.assertEquals(height, area.getHeight(), 0);
  }

  /**
   * Tests the copy constructor.
   */
  @Test
  public void testCopyArea() {
    Assert.assertEquals(area.getX(), new Area(area).getX(), 0);
    Assert.assertEquals(area.getY(), new Area(area).getY(), 0);
    Assert.assertEquals(area.getWidth(), new Area(area).getWidth(), 0);
    Assert.assertEquals(area.getHeight(), new Area(area).getHeight(), 0);
  }

  /**
   * Tests all Setters.
   */
  @Test
  public void testSetArea() {
    area.setX(1);
    area.setY(1);
    area.setWidth(600);
    area.setHeight(400);
    Assert.assertEquals(1, area.getX(), 0);
    Assert.assertEquals(1, area.getY(), 0);
    Assert.assertEquals(600, area.getWidth(), 0);
    Assert.assertEquals(400, area.getHeight(), 0);
    area.setX(0);
    area.setY(0);
    area.setWidth(800);
    area.setHeight(500);
  }

  /**
   * Tests if a Point is this Area.
   */
  @Test
  public void testIsInArea() {
    Point point = new Point(5, 5);
    Assert.assertTrue(area.isInArea(point.getX(), point.getY()));
    Point point1 = new Point(1000, 1000);
    Assert.assertFalse(area.isInArea(point1.getX(), point1.getY()));
    Point point2 = new Point(800, 500);
    Assert.assertTrue(area.isInArea(point2.getX(), point2.getY()));
    Point point3 = new Point(801, 501);
    Assert.assertFalse(area.isInArea(point3.getX(), point3.getY()));
  }
}
