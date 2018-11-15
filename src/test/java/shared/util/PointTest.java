package shared.util;

import org.junit.Assert;
import org.junit.Test;

public class PointTest {

  /**
   * Tests the whole Point class, with all getters and setters.
   */
  @Test
  public void initPoint() {
    int x = 0;
    int y = 0;
    Point point = new Point(x, y);
    Assert.assertEquals(x, point.getX());
    Assert.assertEquals(y, point.getY());
    x = 5;
    y = 6;
    point.setX(x);
    point.setY(y);
    Assert.assertEquals(x, point.getX());
    Assert.assertEquals(y, point.getY());
  }

}
