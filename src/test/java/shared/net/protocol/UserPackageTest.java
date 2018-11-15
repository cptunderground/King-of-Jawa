package shared.net.protocol;

import java.net.Socket;
import java.util.UUID;
import org.junit.Assert;
import org.junit.Test;
import shared.net.protocol.packages.PackageType;
import shared.net.protocol.packages.UserPackage;

public class UserPackageTest {

  private Socket socket = new Socket();
  private String callName = "callName";
  private UserPackage userPackage = new UserPackage(socket, callName);
  private Package usrPackage = new Package("userPackage", PackageType.USER, socket);
  private UserPackage userPackageP = new UserPackage(usrPackage);

  @Test
  public void userPackageTest() {
    userPackage.setUsername("oldName", "newName");
    userPackage.setLocal(true);

    Assert.assertTrue(userPackage.isLocal());
    Assert.assertEquals("oldName", userPackage.getOldName());
    Assert.assertEquals("newName", userPackage.getUsername());

  }

  @Test
  public void userUUidTest() {
    UUID uuid = UUID.randomUUID();
    userPackageP.setUUid(uuid);
    Assert.assertEquals(uuid, userPackageP.getUUid());
  }


}
