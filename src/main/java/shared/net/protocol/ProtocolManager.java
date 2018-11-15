package shared.net.protocol;

import java.util.HashMap;
import java.util.Map;
import shared.net.protocol.packages.PackageType;
import shared.util.ConsoleLog;

public class ProtocolManager {

  private static ProtocolManager instance;
  private Map<String, PackageExecutable> dictionary;
  private Map<PackageType, ProtocolManagement> packageTypes;

  private ProtocolManager() {
    dictionary = new HashMap<>();
    packageTypes = new HashMap<>();
  }

  /**
   * This method creates an instance if it doesn't exist. Otherwise it returns the existing one.
   *
   * @return ProtocolManager instance.
   */
  public static synchronized ProtocolManager getInstance() {
    if (instance == null) {
      ProtocolManager.instance = new ProtocolManager();
    }
    return ProtocolManager.instance;
  }

  public void registerCaller(String s, PackageExecutable o) {
    dictionary.put(s, o);
  }

  /**
   * This method tries to call the class, which was bound to the package executable name info. (for
   * more info see wiki @kingofjawa.com Protocol)
   *
   * @param p Package which has to be processed.
   */
  public void call(Package p) {
    if (dictionary.containsKey(p.getName())) {
      dictionary.get(p.getName()).run(p);
    } else {
      ConsoleLog
          .debug("Executable not found. (" + p.getName() + ") just letting the package drop.");
    }
  }

  public void registerType(PackageType packageType, ProtocolManagement o) {
    packageTypes.put(packageType, o);
  }

  /**
   * This method tries to call the Type-Manager, which was bound to the package type. (for more info
   * see wiki @kingofjawa.com Protocol)
   *
   * @param s Package-Type which has to be processed.
   * @return The bound type-manager.
   */
  public ProtocolManagement callManager(PackageType s) {
    if (packageTypes.containsKey(s)) {
      return packageTypes.get(s);
    } else {
      ConsoleLog
          .debug("Package-type Manager (" + s + ") not found. just letting the package drop.");
    }
    return null;
  }
}
