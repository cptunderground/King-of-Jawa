package server.game.logic.entity;

import java.net.Socket;
import javafx.scene.paint.Color;
import org.junit.Assert;
import org.junit.Test;

import server.game.GameContainer;
import server.game.lobby.Lobby;
import server.game.logic.entity.player.Player;
import server.user.User;

public class PlayerTest {

  private Socket socket = new Socket();
  private User user = new User(socket, "user", "userold");
  private Lobby lobby = new Lobby(user);
  private GameContainer gameContainer = new GameContainer(lobby, "basic");
  private Player player = new Player(gameContainer, user, Color.GREEN);


  /**
   * Tests if the limitations are set correctly and if it returns the right user.
   */
  @Test
  public void playerTest() {
    Assert.assertEquals(500, player.getWallet().limitation.coin);
    Assert.assertEquals(10, player.getWallet().limitation.stone);
    Assert.assertEquals(15, player.getWallet().limitation.wood);

    Assert.assertEquals(user, player.getUser());

    player.setInhabitants(20);

    Assert.assertEquals(20, player.getInhabitants());
    Assert.assertEquals(Color.GREEN, player.getColor());
  }

}
