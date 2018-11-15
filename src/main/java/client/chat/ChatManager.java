package client.chat;

import client.Client;
import client.command.CommandManager;
import client.ui.WindowManager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import shared.chat.MessageType;

import shared.net.protocol.PackageManager;
import shared.net.protocol.ProtocolManagement;
import shared.net.protocol.ProtocolManager;
import shared.net.protocol.packages.ChatPackage;

public class ChatManager extends ProtocolManagement {

  private static ChatManager instance;
  private List<ChatRoom> chatRooms;


  private ChatManager() {
    chatRooms = new ArrayList<>();
    ProtocolManager.getInstance().registerCaller("sendMessage", Chat.getInstance());
    ProtocolManager.getInstance().registerCaller("directMessage", Chat.getInstance());
    ProtocolManager.getInstance().registerCaller("roomMessage", Chat.getInstance());
    ProtocolManager.getInstance().registerCaller("privateMessage", Chat.getInstance());
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

  public void add(ChatRoom chatRoom) {
    chatRooms.add(chatRoom);
  }

  /**
   * This method finds a room based on its name.
   *
   * @param roomName The room name, which is looked for.
   * @return the room object or null.
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
   * Handles messages given by client and sends it to the server. If message starts with a / then it
   * is been sent to the CommandManager.
   */
  void sendRoomMessage(ChatRoom room, String msg) {
    ChatPackage chatPackage = new ChatPackage(Client.getInstance().getSocket(), MessageType.P_ROOM);
    UUID id = Client.getInstance().getLocalUser().getUUid();
    chatPackage.setRoomPlayerMessage(id, room.getName(), msg);
    if (msg.startsWith("/")) {
      CommandManager.getInstance().handle(chatPackage);
    }
    PackageManager.getInstance().add(chatPackage);
  }

  /**
   * Displays text in user Main GUI.
   */
  public void outputMessage(String message) {
    WindowManager.getMainController().sendMessage(message);

  }

  /**
   * Displays text in Lobby GUI.
   *
   * @param message Message
   */
  public void outputMessagerLobby(String message) {
    WindowManager.getLobbyController().sendLobbyMessage(message);
  }

  /**
   * Removes a whole chatRoom.
   */
  public void remove(ChatRoom chatRoom) {
    if (chatRooms.contains(chatRoom)) {
      chatRooms.remove(chatRoom);
    }
  }
}
