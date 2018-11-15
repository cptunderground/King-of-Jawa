package shared.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import shared.Main;

public class ConsoleLog {

  public static final boolean debugMode = false;
  private static final Logger log = LogManager.getRootLogger();


  /**
   * This method logs an info message with log4j.
   *
   * @param message The message which has to be logged.
   */
  public static void info(String message) {
    String side = (Main.isServer()) ? "[SERVER] " : "[CLIENT] ";
    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
    Date date = new Date();
    File f = new File("koj-errorlog-" + dateFormat1.format(date) + ".txt");
    try (FileWriter fw = new FileWriter(f, true)) {
      BufferedWriter bw = new BufferedWriter(fw);
      long threadId = Thread.currentThread().getId();
      bw.write(
          "[Thread: " + threadId + "][INFO] " + side + dateFormat.format(date) + " - "
              + message);
      bw.newLine();
      bw.flush();
      fw.close();
      bw.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    log.info(dateFormat.format(date) + " - " + message);
    if (!Main.isServer()) {
      // if (WindowManager.getMainController() != null) {
      //   WindowManager.getMainController()
      //     .sendMessage(dateFormat.format(date) + " [INFO] " + message);
      //}
    }
  }

  public static void server(String message) {
    info(message);
  }

  public static void client(String message) {
    info(message);
  }

  /**
   * This method logs a warning message with log4j.
   *
   * @param message The message which has to be logged.
   */
  public static void warning(String message) {
    String side = (Main.isServer()) ? "[SERVER] " : "[CLIENT] ";
    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
    Date date = new Date();
    File f = new File("koj-errorlog-" + dateFormat1.format(date) + ".txt");
    try (FileWriter fw = new FileWriter(f, true)) {
      BufferedWriter bw = new BufferedWriter(fw);
      long threadId = Thread.currentThread().getId();
      bw.write("[Thread: " + threadId + "][WARNING] " + side + dateFormat.format(date) + " - "
          + message);
      bw.newLine();
      bw.flush();
      fw.close();
      bw.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    log.warn(dateFormat.format(date) + " - " + message);
    if (!Main.isServer()) {
      //if (WindowManager.getMainController() != null) {
      // WindowManager.getMainController()
      //   .sendMessage(dateFormat.format(date) + " [WARNING] " + message);
      //  }
    }
  }

  /**
   * This method logs a warning message with log4j.
   *
   * @param message The message which has to be logged.
   */
  public static void debug(String message) {
    String side = (Main.isServer()) ? "[SERVER] " : "[CLIENT] ";
    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
    Date date = new Date();
    File f = new File("koj-errorlog-" + dateFormat1.format(date) + ".txt");
    try (FileWriter fw = new FileWriter(f, true)) {
      BufferedWriter bw = new BufferedWriter(fw);
      long threadId = Thread.currentThread().getId();
      bw.write(
          "[Thread: " + threadId + "][DEBUG] " + side + dateFormat.format(date)
              + " - " + message);
      bw.newLine();
      bw.flush();
      fw.close();
      bw.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    if (debugMode) {
      log.debug(dateFormat.format(date) + " - " + message);
      if (!Main.isServer()) {
        // if (WindowManager.getMainController() != null) {
        //   WindowManager.getMainController()
        //.sendMessage(dateFormat.format(date) + " [DEBUG] " + message);
        // }
      }

    }
  }


}
