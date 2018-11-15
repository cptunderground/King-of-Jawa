package client;

import client.user.User;
import java.util.UUID;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;

public class ClientTest {

  /**
   * Rests the client singleton before each test.
   */
  @Before
  public void init() {
    Client.getInstance().reset();
  }

  /**
   * Tests the regex given in the setPort method for a valid port number.
   */
  @Test
  public void testRegexSetPort() {
    String toTest = "21318";
    Client.getInstance().setPort(toTest);
    Assert.assertEquals(toTest, Client.getInstance().getPort());
  }

  /**
   * Tests the regex given in the setPort method for an invalid port number. Asserts the default
   * port (22313) set in the constructor and checks if the method sets it as port.
   */
  @Test
  public void testRegexSetPortFailed() {
    String toTest = "12312312312a31";
    Client.getInstance().setPort(toTest);
    Assert.assertEquals("22313", Client.getInstance().getPort());
  }

  /**
   * Tests the regex given in the setHost method for a valid IP.
   */
  @Test
  public void testRegexSetIp() {
    String toTest = "12.234.12.2";
    Client.getInstance().setHost(toTest);
    Assert.assertEquals(toTest, Client.getInstance().getHost());
  }

  /**
   * Tests the regex given in the setHost method for an invalid IP. Asserts the default Host
   * (localhost) set in the constructor and checks if the method sets it as IP.
   */
  @Test
  public void testRegexSetIpFailed() {
    String toTest = "kingofjava";
    Client.getInstance().setHost(toTest);
    Assert.assertEquals("kingofjava", Client.getInstance().getHost());
  }

  /**
   * Tests the correct initialisation of a new client without a given username. Checks if the
   * username is set to the system username.
   */
  @Test
  public void testWithoutName() {
    Client.getInstance().start();
    Assert.assertEquals(System.getProperty("user.name"), Client.getInstance().getName());
  }

  /**
   * Tests the correct initialisation of a new client with a given username. Checks if the username
   * is set to the chosen username.
   */
  @Test
  public void testWithName() {
    String toTest = "cpt.underground";
    Client.getInstance().start(toTest);
    Assert.assertEquals(toTest, Client.getInstance().getName());
  }

}
