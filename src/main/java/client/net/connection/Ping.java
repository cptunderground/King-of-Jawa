package client.net.connection;

import client.Client;
import client.ui.WindowManager;
import javafx.application.Platform;
import shared.net.protocol.Package;
import shared.net.protocol.PackageExecutable;
import shared.net.protocol.PackageManager;
import shared.net.protocol.packages.PingPackage;
import shared.util.ConsoleLog;


public class Ping implements PackageExecutable {

  private static Ping instance;
  private boolean updated;

  private Ping() {
    updated = false;
  }

  /**
   * This method creates an instance if it doesn't exist. Otherwise it returns the existing one.
   *
   * @return Ping instance.
   */
  public static synchronized Ping getInstance() {
    if (Ping.instance == null) {
      Ping.instance = new Ping();
    }
    return Ping.instance;
  }

  @Override
  public <T extends Package> void run(T pkg) {
    PingPackage pingPackage = pkg.cast();

    if (pingPackage.compareCaller("ping")) {
      PingPackage pingPackage1 = new PingPackage(pingPackage.getSocket(), "pong");
      pingPackage1.setRecTime(pingPackage.getSentTime());
      PackageManager.getInstance().add(pingPackage1);
      ConsoleLog.debug("Ping received now sending pong");

    } else if (pingPackage.compareCaller("pong")) {
      long endTime = System.currentTimeMillis();
      ConsoleLog.debug("" + Client.getInstance() + "|"
              + Client.getInstance().getLocalUser());
      if (Client.getInstance().getLocalUser() != null) {
        Client.getInstance().getLocalUser().setPing(endTime - pingPackage.getRecTime());
        Platform.runLater(() -> {
          if (WindowManager.getMainController() != null) {
            WindowManager.getMainController().setPing(endTime - pingPackage.getRecTime());
          }
        });
        PingThread.getInstance().resetTimeOut();
        ConsoleLog.debug("Pinging Server took "
                + (endTime - pingPackage.getRecTime()) + " ms");
        updated = true;
      }
    }
  }

  /**
   * This method sends a ping to the server. It also starts the time measurement.
   */
  public void ping() {
    PackageManager.getInstance().add(
            new PingPackage(Client.getInstance().getSocket(), "ping"));
    updated = false;
  }

  public boolean isUpdated() {
    return updated;
  }
}
