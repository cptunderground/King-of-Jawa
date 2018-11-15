package client.chat;

import java.util.ArrayList;
import java.util.List;


public class ChatRoom {

  private String name;
  private List<String> clients;

  public ChatRoom(String name) {
    this.name = name;
    this.clients = new ArrayList<>();
  }

  /**
   * This method sends a message to every other client in the same chat-room.
   *
   * @param s The message.
   */
  public void send(String s) {
    ChatManager.getInstance().sendRoomMessage(this, s);
  }

  /**
   * A method which sets the players of a chat-room to synchronize them with server rooms.
   *
   * @param players Players which are present in the chat-room.
   */
  public void setRoomPlayers(String[] players) {
    for (String player : players) {
      //TODO: ADD Clients to arraylist.
    }
  }

  public String getName() {
    return name;
  }

  /**
   * Displays message with chatRoom information as prefix in Global Chat.
   */
  public void outputMessage(String msg) {
    ChatManager.getInstance().outputMessage("[" + name.toUpperCase() + "] " + msg);
  }

  /**
   * Displays message with chatRoom information as prefix in Lobby Chat.
   *
   * @param msg Message
   */
  public void outputMessagerLobby(String msg) {
    ChatManager.getInstance().outputMessagerLobby("[" + name.toUpperCase() + "] " + msg);
  }
  //TODO: Add method for adding one client to room.
  //TODO: Add method for removing one client from room.

}
