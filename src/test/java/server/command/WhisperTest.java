package server.command;

import java.lang.reflect.Field;
import java.net.Socket;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import server.command.cmds.Whisper;

public class WhisperTest {

  Socket socket = new Socket();
  String whisper = "/whisper \"userName\" Hallo Test, wie laufts mit dem testen?";

  /**
   * Sets the singleton to null.
   */
  @Before
  public void resetWhisperSingleton()
      throws SecurityException, NoSuchFieldException,
      IllegalArgumentException, IllegalAccessException {
    Field instance = Whisper.class.getDeclaredField("instance");
    instance.setAccessible(true);
    instance.set(null, null);
  }

  /**
   * Tests the method ParseName, which splits the param to get the username.
   */
  @Test
  public void testParseName() {
    String[] split = whisper.split(" ");
    String name = Whisper.getInstance().parseName(split);

    Assert.assertEquals("userName", name);
  }

  /**
   * Tests if the message is extracted from the rest.
   */
  @Test
  public void testExtractMesser() {
    String[] split = whisper.split(" ");
    String msg = Whisper.getInstance().extractMessage(split);

    Assert.assertEquals("Hallo Test, wie laufts mit dem testen?", msg);
  }

}
