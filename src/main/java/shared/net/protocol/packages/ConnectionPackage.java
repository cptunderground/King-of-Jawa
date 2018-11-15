package shared.net.protocol.packages;

import java.net.Socket;

import shared.net.protocol.Package;
import shared.util.Serialization;

public class ConnectionPackage extends Package {

  public ConnectionPackage(Socket s, String callName) {
    super(callName, PackageType.CONNECTION, s);
  }

  public ConnectionPackage(Package p) {
    super(p);
  }
}
