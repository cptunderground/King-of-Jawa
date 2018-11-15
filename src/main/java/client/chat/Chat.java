package client.chat;

import client.Client;
import client.user.User;
import client.user.UserManager;

import java.util.UUID;

import shared.net.protocol.Package;
import shared.net.protocol.PackageExecutable;
import shared.net.protocol.packages.ChatPackage;
import shared.util.ConsoleLog;



/**
 * This class handles incoming packages, whose callName has been attached to this class. Also this
 * class is a singleton, which means it only can instanciate one object.
 */
public class Chat implements PackageExecutable {

  private static Chat instance;

  /**
   * This method handles incoming packages, whose type has been attached to this singleton.
   *
   * @param pkg Package, which was received.
   */
  public <T extends Package> void run(T pkg) {
    ChatPackage chatPackage = pkg.cast();

    if (chatPackage.compareCaller("sendMessage")) {
      playerRoomMessage(chatPackage);
    } else if (chatPackage.compareCaller("directMessage")) {
      directMessage(chatPackage);
    } else if (chatPackage.compareCaller("roomMessage")) {
      roomMessage(chatPackage);
    } else if (chatPackage.compareCaller("privateMessage")) {
      privateMessage(chatPackage);
    }
  }

  /**
   * Empty private constructor.
   */
  private Chat() {
  }

  /**
   * This static method creates an instance of this class, if no instance exists. otherwise it only
   * returns the instance.
   *
   * @return The Chat instance.
   */
  public static synchronized Chat getInstance() {
    if (Chat.instance == null) {
      Chat.instance = new Chat();
    }
    return Chat.instance;
  }

  /**
   * Handles a package with the callName "sendMessage".
   */
  private void playerRoomMessage(ChatPackage p) {
    String message = p.getMessage();
    String roomName = p.getRoom();
    UUID userId = p.getSender();
    User user = UserManager.getInstance().getUser(userId);
    ChatRoom room = ChatManager.getInstance().getRoom(roomName);
    if (!message.equals("")) {
      if (room != null) {
        if (user != null) {
          String msgToOutput = user.getName() + ": " + message;
          if (room.getName().equals("global")) {
            room.outputMessage(msgToOutput);
          } else {
            room.outputMessagerLobby(msgToOutput);
          }
        }

      }
    }
  }

  /**
   * Handles a package with the callName "directMessage".
   */
  private void directMessage(ChatPackage p) {
    String message = p.getMessage();
    if (message != null) {
      if (!message.equals("")) {
        ChatManager.getInstance().outputMessage(message);
      }
    }
  }

  /**
   * Handles a package with the callName "roomMessage".
   */
  private void roomMessage(ChatPackage p) {
    String message = p.getMessage();
    String roomName = p.getRoom();
    ChatRoom room = ChatManager.getInstance().getRoom(roomName);

    if (message != null) {
      if (!message.equals("")) {
        if (room != null) {
          String msgToOutput = message;
          if (room.getName().equals("global")) {
            room.outputMessage(msgToOutput);
          } else {
            room.outputMessagerLobby(msgToOutput);
          }
        }
      }
    }
  }

  /**
   * Handles a package with the callName "privatMessage". Is only seen by receiver and the
   * sender(whisper).
   */
  private void privateMessage(ChatPackage p) {
    String message = p.getMessage();
    UUID sourceId = p.getSender();
    UUID recipientId = p.getRecipient();
    User source = UserManager.getInstance().getUser(sourceId);
    User recipient = UserManager.getInstance().getUser(recipientId);

    if (message != null) {
      if (!message.equals("")) {
        if (source != null) {
          if (recipient != null) {
            String prefix = "";
            if (source == Client.getInstance().getLocalUser()) {
              prefix = "[PM] (You -> " + recipient.getName() + ")";
            } else if (recipient == Client.getInstance().getLocalUser()) {
              prefix = "[PM] (" + source.getName() + " -> You)";
            } else {
              ConsoleLog
                      .warning("Chat: The localUser is neither the source nor the recipient");
              return;
            }
            String msgToOutput = prefix + " " + message;
            ChatManager.getInstance().outputMessage(msgToOutput);
          }
        }
      }
    }
  }

  public boolean validate(ChatPackage p, String dataKey) {
    return (p.getData(dataKey) != null);
  }

}