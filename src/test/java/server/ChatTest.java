package server;

import java.net.Socket;
import java.util.UUID;
import org.junit.Assert;

import org.junit.Test;
import org.mockito.Mock;

import shared.chat.MessageType;
import shared.net.protocol.packages.ChatPackage;
import shared.user.User;

public class ChatTest {

  @Mock
  MessageType messageType;

  private String callName = "";
  private Socket socket = new Socket();
  private String room = "room";

  User user1 = new User() {
    @Override
    public String getName() {
      return "user1";
    }

    @Override
    public UUID getUUid() {
      return UUID.randomUUID();
    }

    @Override
    public int getId() {
      return 0;
    }

    @Override
    public int getLobbyId() {
      return 2;
    }
  };

  User user2 = new User() {
    @Override
    public String getName() {
      return "user2";
    }

    @Override
    public UUID getUUid() {
      return UUID.randomUUID();
    }

    @Override
    public int getId() {
      return 1;
    }

    @Override
    public int getLobbyId() {
      return 2;
    }
  };

  /**
   * This test tests if a chat roomMessage is sent correctly.
   */
  @Test
  public void roomMessageTest() {
    ChatPackage messageChat = new ChatPackage(socket, callName, messageType);
    messageChat.setRoomMessage(room, "this is a testmessage");

    Assert.assertEquals("this is a testmessage", messageChat.getMessage());
    Assert.assertEquals(room, messageChat.getRoom());
  }

  /**
   * Test if it is possible to send a private message to another user.
   */
  @Test
  public void privateMessageTest() {
    Socket s = new Socket();

    ChatPackage messageChat = new ChatPackage(s, callName, messageType);
    UUID user1Uuid = user1.getUUid();
    UUID user2Uuid = user2.getUUid();
    messageChat.setPrivateMessage(user1Uuid, user2Uuid, "this is a test message");

    Assert.assertEquals("this is a test message", messageChat.getMessage());
    Assert.assertEquals(user2Uuid, messageChat.getRecipient());
    Assert.assertEquals(user1Uuid, messageChat.getSender());
  }

  /**
   * Tests if room play message is sent to the right room whit the given message.
   */
  @Test
  public void roomPlayerMessage() {
    ChatPackage messageChat = new ChatPackage(socket, callName, messageType);
    UUID userUuid = user1.getUUid();
    messageChat.setRoomPlayerMessage(userUuid, room, "this is a roomPlayerMessage");

    Assert.assertEquals("this is a roomPlayerMessage", messageChat.getMessage());
    Assert.assertEquals("room", messageChat.getRoom());
    Assert.assertEquals(userUuid, messageChat.getSender());
  }

  /**
   * This test, tests if a message is correctly set with the method setDirectMessage.
   */
  @Test
  public void setMessageTest() {
    ChatPackage messageChat = new ChatPackage(socket, callName, messageType);
    messageChat.setDirectMessage("this is a direct Message");

    Assert.assertEquals("this is a direct Message", messageChat.getMessage());
  }

}
