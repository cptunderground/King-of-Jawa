package shared.net.protocol.packages;

import java.net.Socket;
import java.util.UUID;

import shared.net.protocol.Package;
import shared.util.Serialization;


public class UserPackage extends Package {

  public UserPackage(Socket s, String callName) {
    super(callName, PackageType.USER, s);
  }

  public UserPackage(Package p) {
    super(p);
  }

  public void setUsername(String oldName, String name) {
    setData("oldName", oldName);
    setData("newName", name);
  }

  public void setUUid(UUID uuid) {
    setData("uuid", uuid.toString());
  }

  public void setLocal(boolean b) {
    setData("isLocal", b + "");
  }

  /**
   * Checks if the user is the local user on the machine.
   *
   * @return a boolean whether it is or not.
   */
  public boolean isLocal() {
    if (getData("isLocal") == null) {
      return false;
    }
    return getData("isLocal").equals("true");
  }

  /**
   * Gets the current username from the package.
   *
   * @return the current name as String.
   */
  public String getUsername() {
    if (getData("newName") == null) {
      return null;
    }
    return getData("newName");
  }

  /**
   * Gets the users old name from the package.
   *
   * @return the old name as String.
   */
  public String getOldName() {
    if (getData("oldName") == null) {
      return null;
    }
    return getData("oldName");
  }

  /**
   * Converts the UUID String to the UUID and gives it back.
   *
   * @return an UUID.
   */
  public UUID getUUid() {
    if (getData("uuid") == null) {
      return null;
    }
    return UUID.fromString(getData("uuid"));
  }

  /**
   * This method gets the user state (lobbywise).
   * @return the state.
   */
  public String getUserState() {
    if (getData("userState") == null) {
      return null;
    }
    return getData("userState");
  }

  public void setUserState(String userState) {
    setData("userState", userState);
  }
}
