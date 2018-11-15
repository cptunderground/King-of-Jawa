package server.game.logic.map.tile;

import client.game.logic.map.MapManager;
import client.game.logic.map.TileTextureTypes;
import server.game.GameContainer;
import shared.game.map.tile.Type;
import shared.util.Point;

public class TileManager {

  private GameContainer gameContainer;
  private int size;
  private Tile[][] tiles;

  /**
   * Creates a TileManager object for a specific gameContainer with the tile size and the map to be
   * managed.
   *
   * @param gameContainer the gameContainer where the manager should operate.
   * @param size the tile size.
   * @param typeMap the map to be managed.
   */
  public TileManager(GameContainer gameContainer, int size, int[][] typeMap) {
    this.gameContainer = gameContainer;
    this.size = size;
    tiles = new Tile[size][size];
    generateTiles(typeMap);
  }

  private void generateTiles(int[][] typeMap) {
    for (int x = 0; x < size; x++) {
      for (int y = 0; y < size; y++) {
        Type type = Type.getTypeFromNumber(typeMap[x][y]);
        tiles[x][y] = new Tile(x, y, type);
      }
    }
  }



  /**
   * This method gets a tile from a given point. If the tile doesn't exist, it returns a
   * water-placeholder.
   *
   * @param x The tiles x.
   * @param y The tiles y.
   */
  public Tile get(int x, int y) {
    Point pointToFind = new Point(x, y);
    return get(pointToFind);

  }

  /**
   * This method gets a tile from a given point. If the tile doesn't exist, it returns a
   * water-placeholder.
   *
   * @param point The tiles point.
   */
  public Tile get(Point point) {
    int x = point.getX();
    int y = point.getY();
    if (x >= 0 && x < size && y >= 0 && y < size) {
      return tiles[point.getX()][point.getY()];
    }
    return null;
  }

  /**
   * This method gets a copy of the tile-array.
   *
   * @return Cloned tile-array.
   */
  public Tile[][] getTiles() {
    Tile[][] ret = new Tile[size][size];
    for (int i = 0; i < tiles.length; i++) {
      ret[i] = tiles[i].clone();
    }
    return ret;
  }

  private static int[] getTileInt(client.game.logic.map.tile.Tile[] tile) {
    int[] ret = new int[tile.length];

    for (int i = 0; i < tile.length; i++) {
      if (tile[i].getType().equals(Type.GRASS)) {
        ret[i] = 1;
      } else {
        ret[i] = 0;
      }
    }

    return ret;
  }

}


