package client.user;

import java.util.UUID;
import shared.net.protocol.Package;
import shared.net.protocol.PackageExecutable;
import shared.net.protocol.packages.UserPackage;
import shared.util.ConsoleLog;

public class UserHandle implements PackageExecutable {

  private static UserHandle instance;

  private UserHandle() {
  }

  /**
   * This method creates an instance if it doesn't exist. Otherwise it returns the existing one.
   *
   * @return UserHandle instance.
   */
  public static UserHandle getInstance() {
    if (UserHandle.instance == null) {
      UserHandle.instance = new UserHandle();
    }
    return UserHandle.instance;
  }

  @Override
  public <T extends Package> void run(T pkg) {
    UserPackage userPackage = pkg.cast();

    User user = UserManager.getInstance().getUser(userPackage.getUUid());
    String oldName = userPackage.getOldName();
    String name = userPackage.getUsername();
    String state = userPackage.getUserState();
    UUID uuid = userPackage.getUUid();
    boolean isLocal = userPackage.isLocal();

    if (userPackage.compareCaller("setName")) {
      if (oldName != null && name != null) {
        setName(user, oldName, name);
      }
    } else if (userPackage.compareCaller("addPlayer")) {
      if (user == null) {
        if (oldName != null && name != null) {
          ConsoleLog.debug("Client-state: " + state);
          addUser(name, uuid, isLocal, state);
        }
      }
    } else if (userPackage.compareCaller("removePlayer")) {
      UserManager.getInstance().removePlayer(uuid);
    }
  }

  public void setName(User user, String oldName, String newName) {
    ConsoleLog.debug(oldName + " -> " + newName);
    user.setName(oldName, newName);
  }

  /**
   * This method adds a synchronized user from the server to the clients user listing.
   *  @param name    The players name.
   * @param uuid    Its id.
   * @param isLocal A boolean containing whether the added user is the local user.
   * @param state The state of the user if he is in a lobby.
   */
  public void addUser(String name, UUID uuid, boolean isLocal, String state) {
    ConsoleLog.debug("user " + name + " was added with id " + uuid);
    User p = new User(uuid, name, isLocal);
    UserManager.getInstance().addPlayer(p);
    p.setState(state);

  }

}
