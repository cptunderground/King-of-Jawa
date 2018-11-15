package server.net.session;

import server.user.UserManager;
import shared.util.ConsoleLog;

public class PingThread extends Thread {

  private Session session;
  private int timeoutCounter;
  private boolean updated;
  private boolean running;

  PingThread(Session session) {
    timeoutCounter = 0;
    this.session = session;
  }

  private boolean getUpdate() {
    return updated;
  }

  void setUpdate(boolean b) {
    this.updated = b;
  }

  void resetTimeout() {
    timeoutCounter = 0;
  }

  /**
   * Starts the ping thread.
   */
  public void start() {
    this.running = true;
    this.updated = true;
    new Thread(this).start();
  }

  @Override
  public void run() {
    while (running) {
      if (getUpdate()) {
        timeoutCounter = 0;
      } else {
        if (timeoutCounter >= 5) {
          if (session != null && session.getPing() != -1) {
            ConsoleLog.warning(session.getUser().getName() + " lost connection.");
            this.running = false;
            UserManager.getInstance().remove(session.getUser());
          } else {
            timeoutCounter = -1;
          }
        }
        timeoutCounter++;
      }
      Ping.getInstance().requestPing(session);
      try {
        Thread.sleep(1500);
      } catch (InterruptedException e) {
        ConsoleLog.debug(e.getMessage());
      }
    }
  }


  public void stopPinging() {
    running = false;
  }
}
