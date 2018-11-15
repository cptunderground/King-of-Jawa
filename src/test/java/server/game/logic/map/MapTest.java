package server.game.logic.map;

import client.game.render.TextureManager;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mock;
import server.game.GameContainer;
import server.game.logic.map.island.Island;
import server.game.logic.map.tile.TileManager;
import shared.game.map.tile.Type;

public class MapTest {

  @Mock
  GameContainer gameContainerMock;

  int[][] testArray = {{0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0},
      {0, 0, 0, 0, 5},};
  Map map = new Map(gameContainerMock, "Hans", testArray, testArray.length);

  /**
   * Tests the initialisation of the TileManager.
   */
  @Test
  public void testGenerateTiles() {
    TileManager tileManager = map.getTileManager();
    for (int i = 0; i < map.getSize(); i++) {
      for (int j = 0; j < map.getSize(); j++) {
        if (i == 4 && j == 4) {
          Assert.assertEquals(Type.HILL, tileManager.get(i, j).getType());
        } else {
          Assert.assertEquals(Type.WATER, tileManager.get(i, j).getType());
        }
      }
    }

  }

  /**
   * Tests for the Island.
   */
  @Test
  public void testIslandTiles() {
    TileManager tileManager = map.getTileManager();
    int islands = map.getIslandManager().getIslands().size();
    Assert.assertEquals(1, islands);
    Island is = map.getIslandManager().getIsland(0);
    int tileCount = is.getTiles().size();
    Assert.assertEquals(1, tileCount);
    Assert.assertEquals(tileManager.get(4, 4), is.getTiles().get(0));
  }

  /**
   * Tests the Author of a map.
   */
  @Test
  public void testMapAuthor() {
    Assert.assertEquals("Hans", map.getAuthor());

  }

  /**
   * Not necessary! Provides that if something on the calculation is changed, it can be found
   * faster.
   */
  @Test
  public void testCalculations() {

    double[] cartXarray = {2, 42, 23, 4, 2, 34, 323};
    double[] cartYarray = {10, 234, 32, 3, 2, 4, 4};

    for (int i = 0; i < cartXarray.length; i++) {
      double isoX = TextureManager.toIsoX(cartXarray[i], cartYarray[i]);
      double isoY = TextureManager.toIsoY(cartXarray[i], cartYarray[i]);
      Assert.assertEquals(cartXarray[i], TextureManager.toCartesianX(isoX, isoY), 0.1);
      Assert.assertEquals(cartYarray[i], TextureManager.toCartesianY(isoX, isoY), 0.1);
    }

    double[] isoXarray = {2, 42, 23, 4, 2, 34, 323};
    double[] isoYarray = {10, 234, 32, 3, 2, 4, 4};

    for (int i = 0; i < isoXarray.length; i++) {
      double cartX = TextureManager.toCartesianX(isoXarray[i], isoYarray[i]);
      double cartY = TextureManager.toCartesianY(isoXarray[i], isoYarray[i]);
      Assert.assertEquals(isoXarray[i], TextureManager.toIsoX(cartX, cartY), 0.1);
      Assert.assertEquals(isoYarray[i], TextureManager.toIsoY(cartX, cartY), 0.1);
    }

  }

  @Test
  public void testWrongMapDimensions() {
    int[][] test = new int[2][3];
    new Map(gameContainerMock, "Hans", test, test.length);
  }

}
