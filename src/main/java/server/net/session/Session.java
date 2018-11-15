package server.net.session;

import java.net.Socket;
import server.user.User;
import shared.net.protocol.Package;
import shared.net.protocol.PackageManager;

public class Session extends shared.net.session.Session {

  private Socket socket;
  private long ping;
  private PingThread pingThread;
  private User user;

  /**
   * Creates a session object which handles the connection for a specific user.
   *
   * @param user the user for the session.
   * @param socket his socket.
   */
  public Session(User user, Socket socket) {
    SessionManager.add(socket, this);
    this.user = user;
    this.socket = socket;
    this.pingThread = new PingThread(this);
    this.pingThread.start();
  }

  @Override
  public Socket getSocket() {
    return this.socket;
  }

  @Override
  public long getPing() {
    return this.ping;
  }

  @Override
  public User getUser() {
    return this.user;
  }

  public void setAlive() {
    pingThread.setUpdate(true);
  }

  public void sendPackage(Package pckg) {
    PackageManager.getInstance().add(pckg);
  }


  public void setDead() {
    pingThread.setUpdate(false);
  }

  public void resetTimeout() {
    pingThread.resetTimeout();
  }

  public void setPing(long ping) {
    this.ping = ping;
  }

  public String getIp() {
    return socket.getInetAddress().toString();
  }

  public void handleRemoval() {
    pingThread.stopPinging();
    SessionManager.remove(socket);
  }
}
