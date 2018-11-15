package shared.net.protocol;

import java.net.Socket;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import shared.net.protocol.packages.PackageType;

public class PackageManagerTest {

  private Socket socketToTest = new Socket();
  private Package packageToTest = new Package(
      "testName", PackageType.CONNECTION, socketToTest);
  private Package packageToTest2 = new Package(
      "testName", PackageType.CONNECTION, socketToTest);
  private Package packageToTest3 = new Package(
      "testName", PackageType.CONNECTION, socketToTest);

  /**
   * Initialises all packages before each test.
   */
  @Before
  public void init() {
    packageToTest.setData("oldName", "peter");
    packageToTest.setData("newName", "Paul");
    packageToTest2.setData("oldName", "lan");
    packageToTest2.setData("newName", "lanlord");
    packageToTest3.setData("oldName", "janni");
    packageToTest3.setData("newName", "cpt.underground");
  }

  /**
   * Tests the isEmpty method.
   */
  @Test
  public void testListIsEmpty() {
    Assert.assertTrue(PackageManager.getInstance().isEmpty(socketToTest));
    PackageManager.getInstance().add(packageToTest);
    Assert.assertFalse(PackageManager.getInstance().isEmpty(socketToTest));
  }

  /**
   * Tests if by the removal of a socket, all packages get deleted.
   */
  @Test
  public void testRemoveSocket() {
    PackageManager.getInstance().add(packageToTest);
    Assert.assertFalse(PackageManager.getInstance().isEmpty(socketToTest));
    PackageManager.getInstance().removeSocket(socketToTest);
    Assert.assertTrue(PackageManager.getInstance().isEmpty(socketToTest));
  }

  /**
   * Checks FIFO for PackageManager list.
   */
  @Test
  public void testGetNextPackage() {
    PackageManager.getInstance().add(packageToTest);
    PackageManager.getInstance().add(packageToTest2);
    PackageManager.getInstance().add(packageToTest3);
    Assert.assertEquals(packageToTest.toString(),
        PackageManager.getInstance().getNextPackage(socketToTest).toString());
    Assert.assertNotEquals(packageToTest.toString(),
        PackageManager.getInstance().getNextPackage(socketToTest).toString());
  }
}
