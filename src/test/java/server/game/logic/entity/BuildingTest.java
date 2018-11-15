package server.game.logic.entity;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import server.game.GameContainer;
import server.game.logic.entity.building.Building;
import server.game.logic.entity.player.Player;
import shared.game.building.BuildingType;

public class BuildingTest {

  @Mock
  Player player;

  @Mock
  GameContainer gameContainer;


  @Rule
  public MockitoRule mockitoRule = MockitoJUnit.rule();

  /**
   * Tests if it is possible to set and get the level of a building.
   */
  @Test
  public void setLevelForBuildingTest() {
    Building b = new Building(gameContainer, BuildingType.NONE, player, 1, 1);
    Building g = new Building(gameContainer, BuildingType.NONE, player, 1, 1);
    b.setLevel(1);
    g.setLevel(2);
    Assert.assertEquals(1, b.getCurrentLevel());
    Assert.assertEquals(2, g.getCurrentLevel());
  }


  /**
   * Tests if it is possible to set a x and y position.
   */
  @Test
  public void setPositionForBuildingTest() {
    Building b = new Building(gameContainer, BuildingType.NONE, player, 1, 1);
    Building g = new Building(gameContainer, BuildingType.NONE, player, 2, 3);

    Assert.assertEquals(1, b.getPosX());
    Assert.assertEquals(1, b.getPosY());
    Assert.assertEquals(2, g.getPosX());
    Assert.assertEquals(3, g.getPosY());
  }


}
