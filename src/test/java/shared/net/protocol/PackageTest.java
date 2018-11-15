package shared.net.protocol;

import java.net.Socket;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import shared.net.protocol.packages.PackageType;

public class PackageTest {

  @Before
  public void init() {

  }

  /**
   * This tests, if a package was parsed correctly.
   */
  @Test
  public void testPackage() {
    Socket s = new Socket();
    Package peter = new Package("testName", PackageType.CONNECTION, s);
    Package peter2 = new Package("testName", PackageType.CONNECTION, s);
    peter.setData("oldName", "peter");
    peter.setData("newName", "Paul");
    peter2.setData("newName", "Paul");
    peter2.setName("");

    Package pan = new Package(peter.toString(), s);
    Assert.assertEquals("peter", pan.getData("oldName"));
    Package pan2 = new Package(peter2.toString(), s);
    Package pan3 = new Package(null, s);
    Assert.assertNull(pan2.getName());
    Assert.assertNull(pan3.getName());
    System.out.println(pan.getData("oldName"));
    System.out.println(peter);

  }

  /**
   * Tests if the package name is set correctly.
   */
  @Test
  public void testPackageDoesCorrectlySetName() {
    Socket s = new Socket();
    Package testPackage = new Package("testName", PackageType.CONNECTION, s);
    String resultingString = "testName";
    Assert.assertEquals(testPackage.getName(), resultingString);
  }

  /**
   * Tests if the package type is set correctly.
   */
  @Test
  public void testPackageDoesCorrectlySetType() {
    Socket s = new Socket();
    Package testPackage = new Package("testName", PackageType.CONNECTION, s);
    PackageType resultingType = PackageType.CONNECTION;
    Assert.assertEquals(resultingType, testPackage.getType());
  }

  /**
   * Tests if the package sets its data to a non existing header.
   */
  @Test
  public void testPackageDoesCorrectlySetNewDataToNonExistingHeader() {
    Socket s = new Socket();
    Package testPackage = new Package("testName", PackageType.CONNECTION, s);
    testPackage.setData("newData", "newData");
    String resultingString = "newData";
    Assert.assertEquals(testPackage.getData("newData"), resultingString);
  }

  /**
   * Tests if the package sets its data to an existing header.
   */
  @Test
  public void testPackageDoesCorrectlySetNewDataToExistingHeader() {
    Socket s = new Socket();
    Package testPackage = new Package("testName", PackageType.CONNECTION, s);
    testPackage.setData("name", "newName");
    String resultingString = "newName";
    Assert.assertEquals(testPackage.getData("name"), resultingString);
  }

  /**
   * Tests if the validate method is enforced correctly.
   */
  @Test
  public void testPackageCreatedOfOtherPackage() {
    Socket s = new Socket();
    Package testPackage = new Package("testName", PackageType.CONNECTION, s);
    testPackage.setName("");
    Package test = new Package(testPackage);
    Assert.assertNull(test.getName());
  }

  /**
   * Tests if the the caller is found.
   */
  @Test
  public void testCaller() {
    Socket s = new Socket();

    Package testPackage = new Package("testName", PackageType.CONNECTION, s);
    Assert.assertTrue(testPackage.compareCaller("testName"));
  }

  /**
   * Tests if the socket is set correctly.
   */
  @Test
  public void testSetSocket() {
    Socket s = new Socket();
    Socket s1 = new Socket();
    Package testPackage = new Package("testName", PackageType.CONNECTION, s);
    testPackage.setSocket(s1);
    Assert.assertEquals(s1, testPackage.getSocket());
  }

  /**
   * Tests if the type is set correctly.
   */
  @Test
  public void testSetType() {
    Socket s = new Socket();
    Socket s1 = new Socket();
    Package testPackage = new Package("testName", PackageType.CONNECTION, s);
    testPackage.setType(PackageType.CHAT);
    Assert.assertEquals(PackageType.CHAT, testPackage.getType());
  }


  /**
   * Tests if the type is set correctly.
   */
  @Test
  public void testGetId() {
    Socket s = new Socket();
    Socket s1 = new Socket();
    Package testPackage = new Package("testName", PackageType.CONNECTION, s);
    int id = testPackage.getId();
    Assert.assertEquals(Package.getIdCounter() - 1, id);
  }

}
