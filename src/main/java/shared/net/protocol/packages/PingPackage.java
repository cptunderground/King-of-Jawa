package shared.net.protocol.packages;

import java.net.Socket;

import shared.net.protocol.Package;
import shared.util.Serialization;

public class PingPackage extends Package {

  public PingPackage(Socket s, String callName) {
    super(callName, PackageType.PING, s);
  }

  public void setSentTime(long sentTime) {
    setData("sentTime", sentTime + "");
  }

  public void setRecTime(long sentTime) {
    setData("recTime", sentTime + "");
  }

  /**
   * Gets the time when the package was sent from the package and parses it to a long.
   *
   * @return time as long.
   */
  public long getSentTime() {
    if (getData("sentTime") == null) {
      return -1;
    }
    return Long.parseLong(getData("sentTime"));
  }

  /**
   * Gets the time from the requesting package.
   *
   * @return time as long.
   */
  public long getRecTime() {
    if (getData("recTime") == null) {
      return -1;
    }
    return Long.parseLong(getData("recTime"));
  }

  public PingPackage(Package p) {
    super(p);
  }
}
