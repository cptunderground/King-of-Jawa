package shared.net.protocol;

import java.net.Socket;
import org.junit.Assert;
import org.junit.Test;
import server.game.lobby.Lobby;
import server.game.lobby.State;
import server.user.User;
import server.user.UserState;
import shared.net.protocol.packages.LobbyPackage;

public class LobbyPackageTest {

  private Socket socket = new Socket();
  private String callName = "callName";
  private LobbyPackage lobbyPackage = new LobbyPackage(socket, callName);
  private User user1 = new User(socket, "user1", "user1Old");
  private Lobby lobby = new Lobby(user1);

  /**
   * This tests checks if the lobbyId and the lobby data is set correctly. If it is set correctly
   * the owner and the
   */
  @Test
  public void lobbyPackageTest() {
    lobbyPackage.setLobbyId(2);
    Assert.assertEquals(2, lobbyPackage.getLobbyId());

    lobbyPackage.setLobbyData(lobby);
    Assert.assertEquals(user1, lobby.getOwner());
    Assert.assertEquals("user1s lobby", lobbyPackage.getLobbyName());

    lobby.setState(State.WAITING);
    Assert.assertEquals(State.WAITING, lobbyPackage.getLobbyState());
  }

  /**
   * This test, tests if the method getJoinedUserId and getLeftUser works correctly.
   */
  @Test
  public void joinedAndLeftUserTest() {
    lobbyPackage.setJoinedUser(user1);
    Assert.assertEquals(user1.getUUid(), lobbyPackage.getJoinedUserId());

    lobbyPackage.setLeftUser(user1);
    Assert.assertEquals(user1.getUUid(), lobbyPackage.getLeftUser());
  }

  /**
   * This test, tests if the UserState is set correctly.
   */
  @Test
  public void userStateTest() {
    lobbyPackage.setUserState(UserState.READY);
    Assert.assertEquals(UserState.READY, lobbyPackage.getUserState());
    lobbyPackage.setUserState(UserState.WAITING);
    Assert.assertEquals(UserState.WAITING, lobbyPackage.getUserState());
  }

}
