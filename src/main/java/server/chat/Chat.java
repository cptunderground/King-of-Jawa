package server.chat;

import server.command.CommandManager;
import server.user.UserManager;
import shared.net.protocol.Package;
import shared.net.protocol.PackageExecutable;
import shared.net.protocol.packages.ChatPackage;
import shared.util.ConsoleLog;

public class Chat implements PackageExecutable {

  private static Chat instance;

  private Chat() {

  }

  /**
   * This method creates an instance if it doesn't exist. Otherwise it returns the existing one.
   *
   * @return Chat instance.
   */
  public static synchronized Chat getInstance() {
    if (Chat.instance == null) {
      Chat.instance = new Chat();
    }
    return Chat.instance;
  }

  /**
   * This method handles the incoming chat-package. It validates it and if the message is found to
   * be a command it will be handled by the CommandManager. Otherwise it will be sent to the other
   * clients.
   *
   * @param p The incoming chat-package.
   */
  public void run(Package p) {
    ChatPackage chatPackage = p.cast();
    if (chatPackage.compareCaller("sendMessage")) {
      if (chatPackage.getRoom() != null) {
        if (chatPackage.getSender() != null) {
          if (chatPackage.getMessage() != null) {
            if (chatPackage.getMessage().startsWith("/")) {
              CommandManager.getInstance().handle(chatPackage);
            } else {
              ChatManager.getInstance().getRoom(chatPackage.getRoom())
                  .sendClientMessage(chatPackage.getSender().toString(),
                      chatPackage.getMessage());
              ConsoleLog.client(
                  "(" + chatPackage.getRoom().toUpperCase() + ")" + UserManager.getInstance()
                      .getUser(chatPackage.getSender()).getName()
                      + ": " + chatPackage.getMessage());
            }
          }
        }
      }
    }
  }

}
