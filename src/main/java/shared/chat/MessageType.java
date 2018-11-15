package shared.chat;

import shared.util.ConsoleLog;

public enum MessageType {
  PRIVATE, ROOM, P_ROOM, DIRECT;

  /**
   * This method gets a call-name based on a message-type.
   */
  public String getCallName() {
    switch (this) {
      case ROOM:
        return "roomMessage";
      case P_ROOM:
        return "sendMessage";
      case DIRECT:
        return "directMessage";
      case PRIVATE:
        return "privateMessage";
      default:
        ConsoleLog.warning("NO MESSAGETYPE FOUND BLYAT");
    }
    return null;
  }
}
