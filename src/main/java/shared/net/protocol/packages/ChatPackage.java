package shared.net.protocol.packages;

import java.net.Socket;
import java.util.UUID;

import shared.chat.MessageType;
import shared.net.protocol.Package;
import shared.util.ConsoleLog;
import shared.util.Serialization;

public class ChatPackage extends Package {

  private MessageType messageType;

  /**
   * This is the constructor of a ChatPackage. It contains information, which is needed for a chat
   * message.
   *
   * @param s The socket, the message has to be sent to.
   * @param callName the CallExecution we want to execute.
   * @param messageType the message type.
   */
  public ChatPackage(Socket s, String callName, MessageType messageType) {
    super(callName, PackageType.CHAT, s);

    this.messageType = messageType;
  }

  /**
   * This is the constructor of a ChatPackage. It contains information, which is needed for a chat
   * message.
   *
   * @param s The socket, the message has to be sent to.
   * @param messageType the message type.
   */
  public ChatPackage(Socket s, MessageType messageType) {
    super(messageType.getCallName(), PackageType.CHAT, s);
    if (messageType.getCallName() == null) {
      ConsoleLog.warning("INVALID MESSAGETYPE");

    }
    this.messageType = messageType;
  }

  public ChatPackage(Package p) {
    super(p);
  }

  private void setMessage(String message) {
    setData("message", message);
  }

  /**
   * This method sets the messages sender.
   *
   * @param uuid The clients id who is the sender.
   */
  private void setSender(UUID uuid) {
    setData("sUUID", uuid.toString());
  }

  /**
   * This method sets the messages recipient.
   *
   * @param uuid the recipients id.
   */
  private void setRecipient(UUID uuid) {
    setData("rUUID", uuid.toString());
  }

  private void setRoom(String room) {
    setData("room", room);
  }

  /**
   * This method sets all important data for a private message such as the source recipient and the
   * message itself.
   *
   * @param sourceId The sourceId, which will determine the source-player.
   * @param recipientId The recipients id, which will determine the recipient-player.
   * @param message The message.
   */
  public void setPrivateMessage(UUID sourceId, UUID recipientId, String message) {
    setSender(sourceId);
    setRecipient(recipientId);
    setMessage(message);
  }

  public void setDirectMessage(String message) {
    setMessage(message);
  }

  /**
   * This method sets all important data for a room message, which was sent by a player, such as the
   * source recipient and the message itself.
   *
   * @param sourceId The sourceId, which will determine the source-player.
   * @param room The room name the message will be sent to.
   * @param message The message.
   */
  public void setRoomPlayerMessage(UUID sourceId, String room, String message) {
    setSender(sourceId);
    setRoom(room);
    setMessage(message);
  }

  public void setRoomMessage(String room, String message) {
    setRoom(room);
    setMessage(message);
  }

  /**
   * Gets the message from the package as String.
   *
   * @return the message as String..
   */
  public String getMessage() {
    if (getData("message") == null) {
      return null;
    }
    return getData("message");
  }

  /**
   * Gets the senders UUID from the package as String and parses it to an UUID.
   *
   * @return the senders UUID as UUID.
   */
  public UUID getSender() {
    if (getData("sUUID") == null) {
      return null;
    }
    return UUID.fromString(getData("sUUID"));
  }

  /**
   * Gets the recipient UUID from the package as String and parses it to an UUID.
   *
   * @return the recipient UUID as UUID.
   */
  public UUID getRecipient() {
    if (getData("rUUID") == null) {
      return null;
    }
    return UUID.fromString(getData("rUUID"));
  }

  /**
   * Gets the room from the package as String.
   *
   * @return the room as String.
   */
  public String getRoom() {
    if (getData("room") == null) {
      return null;
    }
    return getData("room");
  }
}
