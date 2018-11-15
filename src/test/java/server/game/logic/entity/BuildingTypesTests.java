package server.game.logic.entity;

import java.net.Socket;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mock;
import server.game.GameContainer;
import server.game.lobby.Lobby;
import server.game.logic.entity.building.resource.InhabitantFarm;
import server.game.logic.entity.building.resource.StoneFarm;
import server.game.logic.entity.building.resource.WoodFarm;
import server.game.logic.entity.player.Player;
import server.game.logic.map.island.Island;
import server.game.logic.map.tile.Tile;
import server.user.User;
import shared.game.building.BuildingType;

public class BuildingTypesTests {

  @Mock
  Player player;

  private Socket socket = new Socket();
  private User user = new User(socket, "user", "userOld");
  private Lobby lobby = new Lobby(user);
  private GameContainer gameContainer = new GameContainer(lobby, "basic");
  private Island island = gameContainer.getIsland(0);
  private Tile tile = island.getTiles().get(2);
  private int posX = tile.getX();
  private int posY = tile.getY();

  /**
   * Tests if the levels of a woodFarm are set correctly.
   */
  @Test
  public void woodFarmTest() {
    WoodFarm woodFarm = new WoodFarm(gameContainer, BuildingType.WOOD, player, posX, posY);
    woodFarm.setLevel(1);
    Assert.assertEquals(1, woodFarm.getCurrentLevel());

    woodFarm.setLevel(4);
    Assert.assertEquals(4, woodFarm.getCurrentLevel());

    woodFarm.setLevel(7);
    Assert.assertEquals(7, woodFarm.getCurrentLevel());
  }

  /**
   * Tests if the level of a stoneFarm is set correctly.
   */
  @Test
  public void stoneFarmTest() {
    StoneFarm stoneFarm = new StoneFarm(gameContainer, BuildingType.STONE, player, posX, posY);
    stoneFarm.setLevel(2);
    Assert.assertEquals(2, stoneFarm.getCurrentLevel());

    stoneFarm.setLevel(5);
    Assert.assertEquals(5, stoneFarm.getCurrentLevel());

    stoneFarm.setLevel(7);
    Assert.assertEquals(7, stoneFarm.getCurrentLevel());

  }

  /**
   * Tests if the level of an inhabitantFarm is set correctly.
   */
  @Test
  public void inhabitantFarmTest() {
    InhabitantFarm inhabitantFarm = new InhabitantFarm(gameContainer, BuildingType.INHABITANT, player,
        posX, posY);
    inhabitantFarm.setLevel(2);
    Assert.assertEquals(2, inhabitantFarm.getCurrentLevel());

    inhabitantFarm.setLevel(3);
    Assert.assertEquals(3, inhabitantFarm.getCurrentLevel());

    inhabitantFarm.setLevel(6);
    Assert.assertEquals(6, inhabitantFarm.getCurrentLevel());
  }

}
