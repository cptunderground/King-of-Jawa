package server.chat;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import server.user.User;
import server.user.UserManager;

public class ChatRoom {

  private String name;
  private List<User> users;
  private int id;

  public ChatRoom(String name) {
    this.name = name;
    this.users = new ArrayList<>();
  }

  /**
   * This method sends a message from a client (here defined through his id) to everyone else in the
   * chatroom.
   *
   * @param id user id.
   * @param message Message-content.
   */
  public void sendClientMessage(String id, String message) {
    User source = UserManager.getInstance().getUser(UUID.fromString(id));
    ChatManager.getInstance().sendRoomMessage(source, this, message);
  }

  /**
   * This method sends a message to every client in the room. The message can have a prefix.
   *
   * @param message Message content.
   * @param prefix Prefix of the message to be sent.
   */
  public void send(String message, String prefix) {
    ChatManager.getInstance().sendRoomMessage(this, " (" + prefix + ") " + message);
  }

  public void addPlayer(User c) {
    users.add(c);
  }

  /**
   * Removes a client from the chat-room if it exists.
   *
   * @param c user which has to be removed.
   */
  public void removePlayer(User c) {
    if (users.contains(c)) {
      users.remove(c);
    }
  }

  public String getName() {
    return name;
  }

  public ArrayList<User> getUsers() {
    return new ArrayList<>(users);
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getId() {
    return id;
  }
}
