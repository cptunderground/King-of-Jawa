package server.game.logic.map.island;

import java.util.ArrayList;
import java.util.List;
import server.game.GameContainer;
import server.game.logic.map.tile.Tile;
import shared.game.map.tile.Type;
import shared.util.ConsoleLog;


public class IslandManager {

  private List<Island> islands;
  private GameContainer gameContainer;
  private int size;

  /**
   * Creates a IslandManager object for a specific gameContainer.
   *
   * @param gameContainer the gameContainer where the manager should operate.
   * @param tiles the tile array to be managed.
   */
  public IslandManager(GameContainer gameContainer, Tile[][] tiles) {
    this.gameContainer = gameContainer;

    if (tiles.length != tiles[0].length) {
      ConsoleLog.warning("Invalid map!");
    }
    islands = new ArrayList<>();
    this.size = tiles.length;
    generateIslands(tiles);

  }


  /**
   * This method generates islands by looking for landmasses, which are connected.
   */
  public void generateIslands(Tile[][] cache) {
    for (Tile[] tileRow : cache) {
      for (Tile tile : tileRow) {
        if (tile != null) {
          if (tile.getType() != Type.WATER) {

            Island island = getIslandTiles(tile, cache);
            if (island != null) {
              islands.add(island);
            }
          }
        }
      }
    }
  }

  private Island getIslandTiles(Tile tile, Tile[][] cache) {
    Island island = new Island(gameContainer);
    Tile[] neighbours = getNeighbours(tile, cache);

    for (int i = 0; i < 9; i++) {
      if (neighbours[i] != null) {
        if (neighbours[i].getType() != Type.WATER) {
          island.add(neighbours[i]);
          island.addAll(getIslandTiles(neighbours[i], cache));
        }
      }
    }
    return island;
  }

  /**
   * This method looks for the neighbours of a tile, sets them to null in the source-set.
   *
   * @param center The center tile.
   * @param tile The source-tileset.
   * @return Tile_OLD-Array with the existing-neighbours.
   */
  public Tile[] getNeighbours(Tile center, Tile[][] tile) {
    Tile[] ret = new Tile[9];
    int[][] ways = {{-1, 1}, {0, 1}, {1, 1}, {1, 0}, {-1, 0}, {1, -1}, {0, -1}, {-1, -1}, {0, 0}};

    for (int i = 0; i < 9; i++) {
      int x = center.getX() + ways[i][0];
      int y = center.getY() + ways[i][1];
      Tile t = null;
      if (x >= 0 && x < size && y >= 0 && y < size) {
        if (tile[x][y] != null) {
          t = tile[x][y];
          tile[x][y] = null;
        }
      }
      ret[i] = t;
    }
    return ret;
  }

  /**
   * This method gets an island by a given id.
   *
   * @param id This is the given islandId.
   * @return Island, which has the given id.
   */
  public Island getIsland(int id) {
    if (islands.size() > id) {
      return islands.get(id);
    }
    return null;
  }


  public List<Island> getIslands() {
    return islands;
  }

  /**
   * Gets a an island from a tile by iterating over all islands. Returns the island which contains
   * this tile.
   *
   * @param tile the tile which is on an island.
   * @return the island which contains this tile.
   */
  public Island getIslandByTile(Tile tile) {
    for (Island island : islands) {
      if (island.isIslandTile(tile)) {
        return island;
      }
    }
    return null;
  }

  public void add(Island island) {
    islands.add(island);
  }
}
