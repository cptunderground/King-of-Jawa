package shared.net.protocol.packages;

import java.net.Socket;
import shared.net.protocol.Package;

public class ShaqPackage extends Package {

  public ShaqPackage(Socket s, String callName) {
    super(callName, PackageType.SHAQ, s);
  }

  public ShaqPackage(Package p) {
    super(p);
  }

  /**
   * Sets the sound.
   *
   * @param name The sound to be set.
   */
  public void setSound(String name) {
    setData("sound", name);
  }

  public String getSound() {
    return getData("sound");
  }

}
