package server;

import java.net.Socket;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import server.game.lobby.Lobby;
import server.game.lobby.LobbyManager;
import server.game.lobby.State;
import server.user.User;
import server.user.UserState;
import server.user.UserType;

public class LobbyTest {

  private String callName = "callName";
  private Socket socket = new Socket();
  private User user1 = new User(socket, "user1", "user1old");
  private User user2 = new User(socket, "user2", "user2old");
  private User user3 = new User(socket, "user3", "user3old");

  private Lobby lobby1 = new Lobby(callName, user1);
  private Lobby lobby2 = new Lobby(user1);


  @Before
  public void initLobby() {
    lobby1 = new Lobby(callName, user1);
    lobby2 = new Lobby(user1);
    Socket socket = new Socket();
    user1 = new User(socket, "user1", "user1old");
    user2 = new User(socket, "user2", "user2old");
    user3 = new User(socket, "user3", "user3old");
  }

  /**
   * This test checks if all users who joined the lobby and the owner of the lobby are in the
   * lobby.
   */
  @Test
  public void createLobbyAndJoinTest() {
    lobby1.join(user2, UserType.PLAYER);
    lobby1.join(user3, UserType.PLAYER);

    ArrayList<User> userList = new ArrayList<>();
    userList.add(user2);
    userList.add(user3);

    Assert.assertEquals("user1", lobby1.getOwner().getName());
    Assert.assertEquals(userList, lobby1.getUsers());
    Assert.assertEquals(State.WAITING, lobby1.getState());
  }

  /**
   * Tests if Gamestate changes to finished after all users left the Lobby.
   */
  @Test
  public void quitLobbyGameStateIfRunningTest() {
    lobby1.join(user2, UserType.PLAYER);
    lobby1.join(user3, UserType.PLAYER);

    lobby1.setState(State.RUNNING);
    lobby1.quit(user3, UserType.PLAYER);
    lobby1.quit(user1, UserType.PLAYER);
    lobby1.quit(user2, UserType.PLAYER);

    Assert.assertEquals(State.FINISHED, lobby1.getState());
  }

  /**
   * Tests if the name of a lobby is set correctly if there is no name given at the time the lobby
   * is created.
   */
  @Test
  public void lobbyOwnerWithoutNameTest() {
    lobby2.join(user2, UserType.PLAYER);
    lobby2.join(user3, UserType.PLAYER);

    Assert.assertEquals("user1s lobby", lobby2.getName());
  }

  /**
   * Tests if userState is set correctly for each user in a lobby.
   */
  @Test
  public void userStateTest() {
    lobby2.setUserState(user2, UserState.READY);
    lobby2.setUserState(user3, UserState.WAITING);

    Assert.assertEquals(UserState.READY, lobby2.getUserState(user2));
    Assert.assertEquals(UserState.WAITING, lobby2.getUserState(user3));
  }

  /**
   * Tests if it is possible to get the map.
   */
  @Test
  public void mapTest() {
    String map = "map";

    lobby2.setCurrentMap(map);

    Assert.assertEquals(map, lobby2.getCurrentMap());

  }

  /**
   * Tests if method isEveryoneReady works correctly, if one user is waiting the method should
   * return false, if everyone is ready it should return true. And it test if waiting users are in
   * the waitingUserList.
   */
  @Test
  public void isEveryoneReadyTest() {
    lobby2.setUserState(user2, UserState.READY);
    lobby2.setUserState(user3, UserState.WAITING);

    ArrayList<User> userList1 = new ArrayList<>();
    userList1.add(user3);

    Assert.assertEquals(userList1, lobby2.getWaitingUsers());
    Assert.assertFalse(lobby2.isEveryoneReady());

    lobby2.setUserState(user1, UserState.READY);
    lobby2.setUserState(user3, UserState.READY);

    Assert.assertTrue(lobby2.isEveryoneReady());
  }

  /**
   * Tests if the different userLists are correct.
   */
  @Test
  public void userListTest() {
    lobby1.join(user1, UserType.PLAYER);
    lobby1.join(user2, UserType.PLAYER);
    lobby1.join(user3, UserType.SPECTATOR);

    ArrayList<User> userList = new ArrayList<>();
    userList.add(user1);
    userList.add(user2);
    userList.add(user3);

    Assert.assertEquals(userList, lobby1.getUsers());

    ArrayList<User> userList1 = new ArrayList<>();
    userList1.add(user3);

    Assert.assertEquals(userList1, lobby1.getSpectators());

  }


}
