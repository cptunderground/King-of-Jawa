package client.net.connection;

import client.Client;
import shared.util.ConsoleLog;

public class PingThread extends Thread {

  private static PingThread instance;
  private int timeoutCounter;

  private PingThread() {
    timeoutCounter = 0;
  }

  /**
   * This method creates an instance if it doesn't exist. Otherwise it returns the existing one.
   *
   * @return PingThread instance.
   */
  public static PingThread getInstance() {
    if (PingThread.instance == null) {
      PingThread.instance = new PingThread();
    }
    return PingThread.instance;
  }

  public void start() {
    new Thread(this).start();
  }

  @Override
  public void run() {
    while (true) {
      if (Ping.getInstance().isUpdated()) {
        timeoutCounter = 0;
      } else {
        if (timeoutCounter >= 5) {
          if (Client.getInstance().isInitiated() && Client.getInstance().getLocalUser() != null
              && Client.getInstance().getLocalUser().getPing() != -1) {

            ConsoleLog.warning("Connection lost !");
            System.exit(1);
          } else {
            timeoutCounter = -1;
          }
        }
        timeoutCounter++;
      }
      Ping.getInstance().ping();
      try {
        Thread.sleep(1500);
      } catch (InterruptedException e) {
        ConsoleLog.debug(e.getMessage());
      }
    }
  }

  public void resetTimeOut() {
    timeoutCounter = 0;
  }
}
