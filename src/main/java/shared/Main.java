package shared;

import client.Client;
import client.ui.WindowManager;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.application.Application;
import org.apache.log4j.BasicConfigurator;
import server.Server;
import shared.util.ConsoleLog;
import shared.util.TeePrintStream;

public class Main {

  private static boolean isServer = false;
  public static final String version = "0.0.4.6.2";

  /**
   * Starts the server if args is "server" odr the client if args are "client" for localhost or
   * "client" "IP" for hosting in a network.
   *
   * @param args arguemnt given in the cmd prompt by starting the .jar file.
   */
  public static void main(String[] args) {
    BasicConfigurator.configure();


    DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
    Date date = new Date();
    File file = new File("koj-errorlog-" + dateFormat1.format(date) + ".txt");
    FileOutputStream fos = null;
    try {
      fos = new FileOutputStream(file, true);
    } catch (FileNotFoundException e) {
      ConsoleLog.warning(e.getMessage());
    }
    TeePrintStream tee = new TeePrintStream(fos, System.err);
    System.setErr(tee);
    //Command line parameters are parsed correctly:
    //(client <hostadress>:<port> [<username>] | server <port>)

    if (!(args.length == 1 || args.length == 2 || args.length == 3 || args.length == 0)) {
      System.out.println("Wrong amount of arguments passed, please invoke with \n"
          + "java -jar \"King of Jawa-0.0.1.jar\" server <PORT> for starting a Server OR \n"
          + "java -jar \"King of Jawa-0.0.1.jar\" client <IP/HOST>:<PORT> <USERNAME> for starting"
          + " a User.");
      System.exit(-1);
    }
    if (args.length == 0) {
      Client.getInstance().setHost("hidin.de");
      Client.getInstance().setPort("22314");
      Application.launch(WindowManager.class, args);
      return;
    }
    if (args[0].toLowerCase().equals("client") && args.length == 3) {
      String[] input = args[1].split(":");
      Client.getInstance().setHost(input[0]);
      Client.getInstance().setPort(input[1]);
      Client.getInstance().setName(args[2]);
      Application.launch(WindowManager.class, args);

    } else if (args[0].toLowerCase().equals("client") && args.length == 2) {
      String[] input = args[1].split(":");
      Client.getInstance().setHost(input[0]);
      Client.getInstance().setPort(input[1]);
      Application.launch(WindowManager.class, args);

    } else if (args[0].toLowerCase().equals("server") && args.length == 2) {
      System.out.println("Welcome to King of Jawa");
      isServer = true;
      Server.getInstance().setPort(args[1]);
      Server.getInstance().start();

    } else if (args[0].toLowerCase().equals("server")) {
      System.out.println("Welcome to King of Jawa");
      isServer = true;
      Server.getInstance().start();
    } else {
      ConsoleLog.warning("Wrong amount of arguments passed, please invoke with \n"
          + "java -jar \"King of Jawa-0.0.1.jar\" server <PORT> for starting a Server OR \n"
          + "java -jar \"King of Jawa-0.0.1.jar\" client <IP/HOST>:<PORT> <USERNAME> for starting"
          + " a User.");
      System.exit(-1);
    }
  }

  public static boolean isServer() {
    return isServer;
  }

}
