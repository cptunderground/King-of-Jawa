package shared.net.protocol;

import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PackageManager {

  private static PackageManager instance;
  private Map<Socket, ArrayList<Package>> packages;

  private PackageManager() {
    packages = new HashMap<>();
  }

  /**
   * This method creates an instance if it doesn't exist. Otherwise it returns the existing one.
   *
   * @return PackageManager instance.
   */
  public static synchronized PackageManager getInstance() {
    if (PackageManager.instance == null) {
      PackageManager.instance = new PackageManager();
    }
    return PackageManager.instance;
  }

  /**
   * This method adds a package which has to be sent through the socket connection.
   *
   * @param p Package which has to be added.
   */
  public void add(Package p) {
    synchronized (this) {
      ArrayList<Package> list = packages.get(p.getSocket());
      if (list == null) {
        list = new ArrayList<>();
        packages.put(p.getSocket(), list);
      }
      list.add(p);
    }
  }

  /**
   * This method handles every package which has to be sent through the sockets.
   *
   * @param s The client-socket, the package is bound to.
   * @return The package with the highest priority.
   */
  public synchronized <T extends Package> T getNextPackage(Socket s) {
    List<Package> list = packages.get(s);
    Package ret = null;

    if (list != null) {
      ret = list.get(0);
      if (ret != null) {
        list.remove(ret);
      }
    }
    return ret.cast();
  }

  /**
   * This method checks whether the list contains atleast 1 package with the given socket or not.
   *
   * @param s The socket.
   * @return Boolean which determines if there is one package found or not.
   */
  public synchronized boolean isEmpty(Socket s) {
    List<Package> list = packages.get(s);
    if (list == null) {
      return true;
    }
    return list.isEmpty();
  }

  /**
   * This method removes a socket from the hashmap.
   *
   * @param s The socket to be removed.
   */
  public synchronized void removeSocket(Socket s) {
    if (packages.get(s) != null) {
      packages.remove(s);
    }
  }
}