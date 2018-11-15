package server;

import org.junit.Assert;
import org.junit.Test;

public class ServerTest {

  /**
   * Tests setPort method with an allowed Port.
   */
  @Test
  public void testRegexSetPort() {
    String toTest = "21318";
    Server.getInstance().setPort(toTest);
    Assert.assertEquals(toTest, Server.getInstance().getPort());
  }

  /**
   * Tests setPort method with Port which is not allowed.
   */
  @Test
  public void testRegexSetPortFailed() {
    String toTest = "12312312312a31";
    Server.getInstance().setPort(toTest);
    Assert.assertEquals("22313", Server.getInstance().getPort());
  }

  /**
   * Redundant! Tests setIp method with an allowed IP.
   */
  @Test
  public void testRegexSetIp() {
    String toTest = "12.234.12.2";
    Server.getInstance().setIp(toTest);
    Assert.assertEquals(toTest, Server.getInstance().getIp());
  }

  /**
   * Redundant! Tests setIp method with IP which is not allowed.
   */
  @Test
  public void testRegexSetIpFailed() {
    String toTest = "kingofjava";
    Server.getInstance().setIp(toTest);
    Assert.assertEquals("localhost", Server.getInstance().getIp());
  }


}
