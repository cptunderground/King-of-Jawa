package client.game.logic.map.tile;

import client.game.logic.map.MapManager;
import client.game.logic.map.TileTextureTypes;
import client.game.logic.map.WaterTextureTypes;
import client.game.render.TextureManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import shared.game.map.tile.Type;
import shared.util.Point;

public class TileManager {

  private int size;
  private Tile[][] tiles;

  /**
   * Creates a TileManager object to manage the tiles of the map.
   *
   * @param size the size of the map.
   * @param typeMap the type array of the map.
   */
  public TileManager(int size, int[][] typeMap) {
    this.size = size;
    tiles = new Tile[size][size];
    generateTiles(typeMap);
  }

  private void generateTiles(int[][] typeMap) {
    for (int x = 0; x < size; x++) {
      for (int y = 0; y < size; y++) {
        Type type = Type.getTypeFromNumber(typeMap[x][y]);
        tiles[x][y] = new Tile(x, y, type);
        if (x > 0 && y > 0 && x < size - 1 && y < size - 1) {
          int[][] henTile = getNeighbours(x, y, typeMap);
          if (type == Type.GRASS) {
            tiles[x][y].setTextureId(TileTextureTypes.compareTo(henTile));
          } else if (type == Type.WATER) {
            tiles[x][y].setTextureId(WaterTextureTypes.compareTo(henTile));
          }
        }
      }
    }
  }

  /**
   * Returns the neighbours of the tile in the tileMap as an two-dimensional array.
   *
   * @param tileX xPosition.
   * @param tileY yPosition.
   * @param typeMap tileMap.
   * @return returns two-dimensional array.
   */
  public static int[][] getNeighbours(int tileX, int tileY, int[][] typeMap) {
    int[][] ret = new int[9][3];
    int[][] ways = {{-1, 1}, {0, 1}, {1, 1}, {1, 0}, {-1, 0}, {1, -1}, {0, -1}, {-1, -1}, {0, 0}};

    for (int i = 0; i < 9; i++) {
      int x = tileX + ways[i][0];
      int y = tileY + ways[i][1];
      if (x >= 0 && x < typeMap.length && y >= 0 && y < typeMap.length) {
        int temp = 0;
        if (typeMap[x][y] != 0) {
          temp = 1;
        }
        ret[i][0] = x;
        ret[i][1] = y;
        ret[i][2] = temp;
      }
    }
    return ret;
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
    Tile ret = get(pointToFind);
    if (ret == null) {
      return new Tile(x, y, Type.WATER);
    }
    return ret;

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

  /**
   * This method gets all the tiles inside a given camera-area, by checking a calculated set of
   * tiles.
   *
   * @param realX The cameras x-position.
   * @param realY The cameras y-position.
   * @param realWidth The cameras width.
   * @param realHeight The cameras height
   * @return A sorted tile-array, which has to be drawn.
   */
  public Tile[][] getTilesInCameraArea(int realX, int realY, int realWidth, int realHeight) {
    int tileSize = TextureManager.getTileSize();
    int x = realX - 2 * tileSize;
    int y = realY - tileSize;
    int width = realWidth + 4 * tileSize;
    int height = realHeight + tileSize;

    Tile parent = get(toCartesianX(x, y) / tileSize, toCartesianY(x, y) / tileSize);
    Tile topEnd = get(toCartesianX(x + width, y) / tileSize,
        toCartesianY(x + width, y) / tileSize);
    Tile bottomEnd = get(toCartesianX(x, y + height) / tileSize,
        toCartesianY(x, y + height) / tileSize);

    parent = get(parent.getX() - 1, parent.getY());
    topEnd = get(topEnd.getX(), topEnd.getY() - 1);
    bottomEnd = get(bottomEnd.getX(), bottomEnd.getY() + 1);

    Tile walker = parent;
    int horizontal = topEnd.getX() - parent.getX() + 2;
    int vertical = 2 * (bottomEnd.getY() - parent.getY()) + 1;

    Tile[][] tilesInCamera = new Tile[horizontal + 1][vertical + 3];

    for (int k = 0; k < vertical + 3; k = k + 2) {
      int j = 0;
      do {
        tilesInCamera[j][k] = get(walker.getX(), walker.getY());
        tilesInCamera[j][k + 1] = get(walker.getX() + 1, walker.getY());
        walker = get(walker.getX() + 1, walker.getY() - 1);
        j++;
      } while (walker.getX() <= topEnd.getX());
      walker = get(parent.getX() + (k / 2), parent.getY() + (k / 2));
      topEnd = get(topEnd.getX() + 1, topEnd.getY() - 1);
    }
    //save walker x + 1 and walker x + 1 y -1
    //set walker to x+1 y-1
    return sortTiles(tilesInCamera);

  }

  private int toCartesianY(int x, int y) {
    return TextureManager.toCartesianY(x, y);
  }

  private int toCartesianX(int x, int y) {
    return TextureManager.toCartesianX(x, y);
  }

  private Tile[] flatten(Tile[][] data) {
    List<Tile> list = new ArrayList<Tile>();

    for (int i = 0; i < data.length; i++) {
      for (int j = 0; j < data[i].length; j++) {
        if (data[i][j] != null) {
          list.add(data[i][j]);
        }
      }
    }

    return list.toArray(new Tile[0]);
  }

  private Tile[][] sortTilesPerY(Tile[][] tiles) {
    Tile[] testTiles = flatten(tiles);
    List<Tile[]> testList = new ArrayList<>();

    for (int i = 0; i < testTiles.length; i++) {
      int smallestY = Integer.MAX_VALUE;
      List<Tile> row = new ArrayList<>();
      for (int j = 0; j < testTiles.length; j++) {
        if (testTiles[j] != null) {
          if (testTiles[j].getY() < smallestY) {
            smallestY = testTiles[j].getY();
          }
        }
      }
      for (int j = 0; j < testTiles.length; j++) {
        if (testTiles[j] != null) {
          if (smallestY == testTiles[j].getY()) {
            row.add(testTiles[j]);
            testTiles[j] = null;
          }
        }
      }
      if (!row.isEmpty()) {
        testList.add(row.toArray(new Tile[0]));
      }

    }
    Tile[][] test = testList.toArray(new Tile[1][1]);
    return test;
  }

  private Tile[][] sortTilesPerX(Tile[][] tiles) {
    for (int i = 0; i < tiles.length; i++) {
      Arrays.sort(tiles[i],
          (Comparator<Tile>) (o1, o2) -> (o1.getX() > o2.getX() ? o1.getX() : o2.getX()));
    }
    return tiles;
  }

  private Tile[][] sortTiles(Tile[][] tiles) {
    Tile[][] ret = sortTilesPerY(tiles);
    ret = sortTilesPerX(ret);
    return ret;
  }
}
