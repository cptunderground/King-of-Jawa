package shared.util;

import org.junit.Assert;
import org.junit.Test;


public class SerializationTest {

  /**
   * Tests if all the characters which should be escaped actually get escaped as defined in the
   * following pattern: public Serialization() { Chain.registerEscapeString("'", "*'");
   * Chain.registerEscapeString("}", "*}"); Chain.registerEscapeString("{", "*{");
   * Chain.registerEscapeString("[", "*["); Chain.registerEscapeString("[", "*]");
   * Chain.registerEscapeString(",", "*,"); Chain.registerEscapeString("|", "*|");
   * Chain.registerEscapeString("\n", "*n"); Chain.registerEscapeString("*", "**"); clearAll(); }
   */
  @Test
  public void testEscapeStrings() {
    Chain head = new Chain("name", "sendMessage");
    head.insert("id", "33");
    head.insert("priority", "12");

    Chain body = new Chain("message", "Hallo ich bin ein 'Stringa', \\ asda| LOL");
    Chain chainToChange = new Chain("head", head);
    chainToChange.insert("body", body);
    String result = chainToChange.toString();
    Serialization s = new Serialization();
    Chain c = s.unpack(result);
    String resultingString = "'head'|{'name'|'sendMessage','id'|'33','priority'|'12'},"
        + "'body'|{'message'|'Hallo ich bin ein *'Stringa*'*, \\ asda*| LOL'}";
    Assert.assertEquals(resultingString, c.toString());
  }

  /**
   * Tests stuff, I guess :).
   */
  @Test
  public void testStuff() {
    Chain head = new Chain("name", "sendMessage");
    head.insert("id", "33");
    head.insert("priority", "12");

    Chain body = new Chain("message", "Hallo ich bin ein \"'String'\" LOL");
    Chain chainToChange = new Chain("head", head);
    chainToChange.insert("body", body);

    String resultingString = "'head'|{'name'|'sendMessage','id'|'33','priority'|'12'},"
        + "'body'|{'message'|'Hallo ich bin ein \"*'String*'\" LOL'}";

    Serialization s = new Serialization();
    Chain c = s.unpack(resultingString);
    Assert.assertEquals(resultingString, c.toString());
  }

  /**
   * Tests the unpack method for multi layered chains.
   */
  @Test
  public void testMultipleLayer() {
    String resultingString = "'head'|{'name'|'setName','id'|'33','priority'|'12',"
        + "'hens'|{'boy'|'works'},'popo'|'pipi'},"
        + "'body'|{'oldName'|'hidin','hens'|{'boy'|'works'},"
        + "'popo'|'pipi','newName'|'brklyn'}";
    Serialization s = new Serialization();
    Chain c = s.unpack(resultingString);

    Assert.assertEquals(resultingString, c.toString());
  }

  /**
   * Tests if an empty value is parsed correctly in a chain.
   */
  @Test
  public void testEmptyStringAsValue() {
    String resultingString = "'head'|''";
    Serialization s = new Serialization();
    Chain c = s.unpack(resultingString);

    Assert.assertEquals(resultingString, c.toString());
  }

  /**
   * Tests appending a chain to an empty serialization. Afterwards appending a chain to the last
   * one.
   */
  @Test
  public void testAppendToSerialization() {
    Serialization s = new Serialization();
    s.append("Hidin", "Horst");
    s.append("Horst", "Hans");

    Assert.assertEquals("Hans", s.find("Horst").getValue().toString());
  }

  /**
   * Tests appending a chain to an empty serialization. Afterwards appending a chain to the last
   * one.
   */
  @Test
  public void testChainInChainSerialized() {
    String test = "{'head'|{'name'|{'tim'|'hallo'},'tom'|'ate'}}";
    Serialization s = new Serialization();
    s.unpack(test);
    Chain head = s.find("head");
    Chain layer1 = (Chain) head.getValue();
    Chain layer2 = (Chain) layer1.find("name").getValue();

    Assert.assertEquals("hallo", layer2.getValue().toString());
  }

  /**
   * Tests appending if the depth calculations are done correctly.
   */
  @Test
  public void testDepthBehaviour() {
    String test = "*{'head'|{'name'|{'tim'|'hallo'},'tom'|'ate'}}";
    Serialization s = new Serialization();
    s.unpack(test);
    Chain head = s.find("head");
    Assert.assertNull(head);

    String test2 = "{}'head'|{'name'|{'tim'|'hallo'},'tom'|'ate'}}";
    Serialization s2 = new Serialization();
    s2.unpack(test2);
    Chain head2 = s2.find("head");
    Assert.assertEquals("'head'|'','name'|'','tim'|'hallo'", head2.toString());

    String test3 = "{{}'head'|{'name'|{'tim'|'hallo'},'tom'|'ate'}}";
    Serialization s3 = new Serialization();
    s3.unpack(test3);
    Chain head3 = s3.find("head");
    Assert.assertEquals("'head'|'','name'|'','tim'|'hallo'",head3.toString());

    String test4 = "}{'head'|{'name'|{'tim'|'hallo'},'tom'|'ate'}}";
    Serialization s4 = new Serialization();
    s4.unpack(test4);
    Chain head4 = s4.find("head");
    Assert.assertEquals("'head'|'','name'|'','tim'|'hallo'",head4.toString());
  }


  /**
   * Tests appending a chain to an empty serialization. Afterwards appending a chain to the last
   * one.
   */
  @Test
  public void testLayeringBehaviour() {
    String test = "!{'head'|{'name'|{'tim'|'hallo'},'tom'|'ate'}}";
    Serialization s = new Serialization();
    s.unpack(test);
    Assert.assertNull(s.find("as"));

  }
}

