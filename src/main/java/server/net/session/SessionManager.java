package server.net.session;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import shared.util.ConsoleLog;

public class SessionManager {

  private static Map<Socket, Session> sessions = new HashMap<>();

  /**
   * Adds a socket to an existing session.
   *
   * @param socket the socket to be added.
   * @param session the session the socket should be added.
   */
  public static void add(Socket socket, Session session) {
    if (!sessions.containsKey(socket)) {
      sessions.put(socket, session);
    } else {
      ConsoleLog.warning("Tried to add session to a user, who already has a session.");
    }
  }

  /**
   * This method gets a session based on a given socket.
   *
   * @param socket the socket.
   * @return the session.
   */
  public static Session get(Socket socket) {
    if (sessions.containsKey(socket)) {
      return sessions.get(socket);
    }
    return null;
  }

  /**
   * Removes the socket from the session.
   *
   * @param socket the socket to be removed.
   */
  public static void remove(Socket socket) {
    if (sessions.containsKey(socket)) {
      sessions.remove(socket);
    }
  }
}
