package shared.util;

import org.junit.Assert;
import org.junit.Test;


public class ChainTest {

  /**
   * Tests the placing pattern for a single chain element.
   */
  @Test
  public void toStringDoesCorrectlyPlaceLettersForOneChainElement() {
    Chain chainToChange = new Chain("key", "value");

    String chainToString = chainToChange.toString();
    String resultingString = "'key'|'value'";
    Assert.assertEquals(resultingString, chainToString);
  }

  /**
   * Tests the paling pattern for a chain element with another chain as value.
   */
  @Test
  public void toStringDoesCorrectlyFormatStringForOneChainElement() {
    Chain chainToChange = new Chain("mainChainKey", new Chain("subChainKey", "someValue"));

    String chainToString = chainToChange.toString();
    String resultingString = "'mainChainKey'|{'subChainKey'|'someValue'}";
    Assert.assertEquals(resultingString, chainToString);
  }

  /**
   * Tests the placing pattern for a chain element with multiple subChains.
   */
  @Test
  public void toStringDoesCorrectlyPlaceLettersForMultipleChainElements() {
    Chain test = new Chain("mainChainFirst", "mainChainValue1");
    test.insert("mainChainSecond", "mainChainValue2");

    Chain chainToChange = new Chain("outerKey1", test);
    chainToChange.insert("outerKey2", "outerValue2");

    String chainToString = chainToChange.toString();
    String resultingString = "'outerKey1'|{'mainChainFirst'|'mainChainValue1',"
        + "'mainChainSecond'|'mainChainValue2'},'outerKey2'|'outerValue2'";
    Assert.assertEquals(resultingString, chainToString);
  }

  /**
   * Tests the overridden toString method for a single chain.
   */
  @Test
  public void toStringDoesCorrectlyFormatStringForPackage() {
    Chain head = new Chain("name", "setName");
    head.insert("id", "33");
    head.insert("priority", "12");

    Chain body = new Chain("oldName", "hidin");
    body.insert("newName", "brklyn");

    Chain chainToChange = new Chain("head", head);
    chainToChange.insert("body", body);

    String chainToString = chainToChange.toString();
    String resultingString = "'head'|{'name'|'setName','id'|'33','priority'|'12'},"
        + "'body'|{'oldName'|'hidin','newName'|'brklyn'}";
    Assert.assertEquals(resultingString, chainToString);
  }

  /**
   * Tests the overridden toString method for a chain element with multiple subChains.
   */
  @Test
  public void toStringDoesCorrectlyFormatStringForPackageWithSecondChainElementGiven() {
    Chain head = new Chain("name", "setName");
    head.insert("id", "33");
    head.insert("priority", "12");

    Chain body = new Chain("oldName", "hidin");
    body.insert("newName", "brklyn");

    Chain chainToChange = new Chain("head", head);
    chainToChange.insert("body", body);

    String chainToString = chainToChange.getNext().toString();
    String resultingString = "'head'|{'name'|'setName','id'|'33','priority'|'12'},"
        + "'body'|{'oldName'|'hidin','newName'|'brklyn'}";
    Assert.assertEquals(resultingString, chainToString);
  }

  /**
   * Tests if the value of an empty chain element can be inserted to it.
   */
  @Test
  public void insertValueToAnExistingKey() {
    Chain head = new Chain("Peter");
    head.insert("Peter", "Paul");
    String resultString = "'Peter'|'Paul'";
    Assert.assertEquals(resultString, head.toString());
  }

  /**
   * Tests if the chain tries to go to the beginning.
   */
  @Test
  public void testFindGoingToStart() {
    Chain baseChain = new Chain("key", "value");
    Chain secondChain = new Chain("key1", "value1");
    Chain thirdChain = new Chain("key2", "value2");
    Chain fourthChain = new Chain("key3", "value3");
    thirdChain.append(fourthChain);
    secondChain.append(thirdChain);
    baseChain.append(secondChain);
    Chain result = fourthChain.find("key2");
    Assert.assertEquals(thirdChain, result);
  }

  /**
   * Tests finding.
   */
  @Test
  public void testFind() {
    Chain baseChain = new Chain("key", "value");
    Chain secondChain = new Chain("key1", "value1");
    Chain thirdChain = new Chain("key2", "value2");
    Chain fourthChain = new Chain("key3", "value3");
    thirdChain.append(fourthChain);
    secondChain.append(thirdChain);
    baseChain.append(secondChain);
    Chain result = baseChain.find("key2");
    Assert.assertEquals(thirdChain, result);
    Assert.assertNull(baseChain.getPrev());
  }

  /**
   * Tests finding.
   */
  @Test
  public void testGettingValue() {
    Chain.registerEscapeString("|", "*|");
    Chain baseChain = new Chain("key", "value");
    Chain secondChain = new Chain("key1|");
    final Chain test = new Chain("");
    baseChain.append(secondChain);
    Chain result = baseChain.find("key1|");
    Chain result1 = baseChain.find("key");
    Assert.assertEquals("", result.getValue(true));
    Assert.assertNotNull(result1.getValue(false));
    Assert.assertEquals("key1*|", result.getKey(true));
    Assert.assertEquals("key1|", result.getKey(false));
    Assert.assertNull(test.getKey());
    Assert.assertNull(test.getValue());
  }
}

