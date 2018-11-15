package shared.net.protocol;

public abstract class ProtocolManagement {

  public void receivePackage(Package p) {
    ProtocolManager.getInstance().call(p);
  }
}
