package shared.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.Charset;

import org.apache.commons.lang.StringEscapeUtils;
import server.user.UserManager;
import shared.Main;
import shared.net.protocol.Package;
import shared.net.protocol.PackageManager;
import shared.net.protocol.ProtocolManagement;
import shared.net.protocol.ProtocolManager;
import shared.net.protocol.packages.PackageType;
import shared.net.protocol.packages.PingPackage;
import shared.util.ConsoleLog;

public class NetworkThread extends Thread {

  private Socket socket;
  private boolean isServer;

  public NetworkThread(Socket socket, boolean isServer) {
    this.isServer = isServer;
    this.socket = socket;
  }

  @Override
  public void start() {
    new Thread(this).start();
  }

  @Override
  public void run() {
    try {
      if (isServer) {
        String message;
        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(socket.getInputStream(), Charset.forName("UTF-8")));
        while ((message = bufferedReader.readLine()) != null) {

          Package p = new Package(StringEscapeUtils.unescapeJava(message), socket);
          if (p.validate()) {
            ProtocolManagement management = ProtocolManager.getInstance().callManager(p.getType());
            if (management != null) {
              ConsoleLog.debug("Package received: " + message);
              management.receivePackage(p);
            }
          } else {
            ConsoleLog.debug("Package: " + message + " | has been dropped.");
          }
        }
      } else {
        PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
        while (true) {

          if (!PackageManager.getInstance().isEmpty(socket)) {
            Package p = PackageManager.getInstance().getNextPackage(socket);
            if (p != null) {
              String readerInput = "";
              if (p.getType().equals(PackageType.PING)) {
                PingPackage pingPackage = p.cast();
                if (pingPackage.compareCaller("ping")) {
                  pingPackage.setSentTime(System.currentTimeMillis());
                }
                readerInput = pingPackage.toString();
              } else {
                readerInput = p.toString();
              }
              ConsoleLog.debug("Package sent: " + readerInput);
              printWriter.println(StringEscapeUtils.escapeJava(readerInput));
            }
          }
          try {
            Thread.sleep(1);
          } catch (InterruptedException e) {
            ConsoleLog.debug(e.getMessage());
          }
        }
      }

    } catch (IOException e) {
      if (Main.isServer()) {
        UserManager.getInstance().remove(UserManager.getInstance().getUser(socket));
      }
      ConsoleLog.debug("Oops: " + e.getMessage());
    }
  }
}
