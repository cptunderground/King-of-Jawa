package server;

import java.net.Socket;
import java.util.UUID;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import server.chat.ChatManager;
import server.chat.ChatRoom;
import server.user.User;
import server.user.UserManager;

public class UserTest {

  Socket userSocket = new Socket();
  User user = new User(userSocket, "test", null);
  UUID uuid = user.getUUid();

  /**
   * Checks that the uuid is not changed by changing the username. Side effect: ich the uuid does
   * not change the user changes name and does not initialise a new user object.
   */
  @Test
  public void testUuid() {
    user.setName("diffrent");
    Assert.assertEquals(uuid, user.getUUid());
  }

  /**
   * Tests all searching methods in the UserManager.
   */
  @Test
  public void testUserManagerFind() {
    ChatManager.getInstance().add(new ChatRoom("global"));
    UserManager.getInstance().add(user);
    Assert.assertEquals(user, UserManager.getInstance().findByName("test"));
    Assert.assertEquals(user, UserManager.getInstance().getUser(userSocket));
    Assert.assertEquals(user, UserManager.getInstance().getUser(uuid));
  }

  /**
   * Tests the validation method in the UserManager. If a name exists the method should append a 1
   * to it, if this exists it appends a 2 and so on.
   */
  @Test
  public void testValidateName() {

    final User user = new User(new Socket(), "user", null);
    final User user1 = new User(new Socket(), "user1", null);
    final User user2 = new User(new Socket(), "user2", null);
    final User user3 = new User(new Socket(), "user3", null);

    ChatManager.getInstance().add(new ChatRoom("global"));

    UserManager.getInstance().add(user);
    UserManager.getInstance().add(user1);
    Assert.assertEquals("user2", UserManager.getInstance().validateName("user"));
    UserManager.getInstance().add(user2);
    UserManager.getInstance().add(user3);
    Assert.assertEquals("user4", UserManager.getInstance().validateName("user"));

  }


}
