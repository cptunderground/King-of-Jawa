package server.chat;

import java.util.ArrayList;
import java.util.List;

import server.user.User;
import shared.chat.MessageType;
import shared.net.protocol.ProtocolManagement;
import shared.net.protocol.ProtocolManager;
import shared.net.protocol.packages.ChatPackage;
import shared.util.ConsoleLog;

public class ChatManager extends ProtocolManagement {

  private static ChatManager instance;
  private List<ChatRoom> chatRooms;
  private static int ChatRoomId = 0;


  private ChatManager() {
    chatRooms = new ArrayList<>();
    ProtocolManager.getInstance().registerCaller("sendMessage", Chat.getInstance());
  }

  /**
   * This method creates an instance if it doesn't exist. Otherwise it returns the existing one.
   *
   * @return ChatManager instance.
   */
  public static synchronized ChatManager getInstance() {
    if (ChatManager.instance == null) {
      ChatManager.instance = new ChatManager();
    }
    return ChatManager.instance;
  }

  /**
   * Adds a new chat-room to the managers list, to
   * maintain every chat-room easily.
   *
   * @param chatRoom The new chat-room.
   */
  public void add(ChatRoom chatRoom) {
    chatRoom.setId(ChatRoomId);
    ChatRoomId++;
    chatRooms.add(chatRoom);
  }

  /**
   * Finds the chat-room if it exists and returns it.
   *
   * @param roomName The chat-rooms name, which has to be found.
   * @return The found chat-room or null.
   */
  public ChatRoom getRoom(String roomName) {
    for (ChatRoom chatRoom : chatRooms) {
      if (chatRoom.getName().equals(roomName)) {
        return chatRoom;
      }
    }
    return null;
  }

  /**
   * Returns a ChatRoom with a specific id.
   *
   * @param id specific id.
   * @return ChatRoom with specific id.
   */
  public ChatRoom getRoom(int id) {
    for (ChatRoom chatRoom : chatRooms) {
      if (chatRoom.getId() == id) {
        return chatRoom;
      }
    }
    return null;
  }

  /**
   * Sends a direct message to a specific User.
   *
   * @param u   User, where the message wants to be sent.
   * @param msg specific message.
   */
  public void sendDirectMessage(User u, String msg) {
    ChatPackage chatPackage = new ChatPackage(u.getSession().getSocket(), MessageType.DIRECT);
    chatPackage.setDirectMessage(msg);
    u.sendPackage(chatPackage);
  }

  /**
   * Sends a private message to a specific User.
   *
   * @param source    User, where the message comes from.
   * @param recipient User, where the message wants to be sent.
   * @param msg       specific message.
   */
  public void sendPrivateMessage(User source, User recipient, String msg) {
    ChatPackage chatPackage = new ChatPackage(recipient.getSession().getSocket(),
        MessageType.PRIVATE);
    chatPackage.setPrivateMessage(source.getUUid(), recipient.getUUid(), msg);
    recipient.sendPackage(chatPackage);
    chatPackage = new ChatPackage(source.getSession().getSocket(), MessageType.PRIVATE);
    chatPackage.setPrivateMessage(source.getUUid(), recipient.getUUid(), msg);
    source.sendPackage(chatPackage);
  }

  /**
   * Sends a message to a ChatRoom.
   *
   * @param room specific ChatRoom.
   * @param msg  specific message.
   */
  public void sendRoomMessage(ChatRoom room, String msg) {
    List<User> users = room.getUsers();
    ConsoleLog.debug(users.size() + "");
    for (User c : users) {
      ChatPackage chatPackage = new ChatPackage(c.getSession().getSocket(), MessageType.ROOM);
      chatPackage.setRoomMessage(room.getName(), msg);
      c.sendPackage(chatPackage);
    }
  }

  /**
   * Sends a message to a ChatRoom from a specific User.
   *
   * @param source User, where the message comes from.
   * @param room   specific ChatRoom.
   * @param msg    specific message.
   */
  public void sendRoomMessage(User source, ChatRoom room, String msg) {
    List<User> users = room.getUsers();
    for (User c : users) {
      ChatPackage chatPackage = new ChatPackage(c.getSession().getSocket(), MessageType.P_ROOM);
      chatPackage.setRoomPlayerMessage(source.getUUid(), room.getName(), msg);
      c.sendPackage(chatPackage);
    }
  }

  /**
   * Removes a specific ChatRoom if it exists.
   *
   * @param chatRoom specific ChatRoom.
   */
  public void remove(ChatRoom chatRoom) {
    if (chatRooms.contains(chatRoom)) {
      chatRooms.remove(chatRoom);
    }
  }
}
