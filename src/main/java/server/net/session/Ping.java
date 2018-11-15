package server.net.session;

import java.net.Socket;

import shared.net.protocol.Package;
import shared.net.protocol.PackageExecutable;
import shared.net.protocol.packages.PingPackage;
import shared.util.ConsoleLog;


public class Ping implements PackageExecutable {

  private static Ping instance;

  private Ping() {

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
  public <T extends Package> void run(T p) {
    PingPackage pingPackage = p.cast();
    if (pingPackage.compareCaller("ping")) {
      ping(pingPackage);
    } else if (pingPackage.compareCaller("pong")) {
      pong(pingPackage);
    }
  }

  /**
   * With this method, the server can request a ping for a specific client.
   *
   * @param session The session which has to be pinged.
   */
  void requestPing(Session session) {
    session.sendPackage(new PingPackage(session.getSocket(), "ping"));
    session.setDead();
  }

  /**
   * This method handles an incoming ping-package.
   *
   * @param p Incoming ping-package.
   */
  public void ping(PingPackage p) {
    Socket socket = p.getSocket();
    Session session = SessionManager.get(socket);
    if (session != null) {
      PingPackage pingPackage1 = new PingPackage(socket, "pong");
      pingPackage1.setRecTime(p.getSentTime());
      session.sendPackage(pingPackage1);
      ConsoleLog.debug("Ping received from " + session.getUser().getName() + " now sending pong.");
    } else {
      ConsoleLog.warning("No session found, check network-integrity.");
    }
  }

  /**
   * This method handles the pong (answer from a requested ping).
   *
   * @param p Incoming pong-package.
   */
  private void pong(PingPackage p) {
    long endTime = System.currentTimeMillis();
    Socket socket = p.getSocket();
    Session session = SessionManager.get(socket);

    if (session != null) {
      session.resetTimeout();
      ConsoleLog
          .debug("Pinging " + session.getUser().getName() + " took " + (endTime - p.getRecTime())
              + " ms");
      session.setPing(endTime - p.getRecTime());
      session.setAlive();
    } else {
      ConsoleLog.warning("Tried to ping not existing client.");
    }
  }
}
