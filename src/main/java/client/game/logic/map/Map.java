package client.game.logic.map;

import client.game.logic.map.island.Island;
import client.game.logic.map.island.IslandManager;
import client.game.logic.map.tile.Tile;
import client.game.logic.map.tile.TileManager;
import client.game.render.TextureManager;

import java.util.List;


public class Map extends shared.game.map.Map {

  private TileManager tileManager;
  private IslandManager islandManager;
  private String author;
  private int[][] mapData;
  private int size;

  /**
   * Creates a Map with a given author, mapData and specific size.
   *
   * @param author  Author of the Map.
   * @param mapData MapData of the Map.
   * @param size    Size of the Map.
   */
  public Map(String author, int[][] mapData, int size) {
    TextureManager.initTextures();
    this.mapData = mapData;
    this.tileManager = new TileManager(size, mapData);
    this.islandManager = new IslandManager(tileManager.getTiles());
    this.author = author;
    this.size = size;

  }

  public IslandManager getIslandManager() {
    return islandManager;
  }

  public int getSize() {
    return size;
  }

  public Tile[][] getTilesInCameraArea(int posX, int posY, int width, int height) {
    return tileManager.getTilesInCameraArea(posX, posY, width, height);
  }

  public String getAuthor() {
    return author;
  }

  public Tile getTile(int posX, int posY) {
    return tileManager.get(posX, posY);
  }

  public Island getIsland(int i) {
    return islandManager.getIsland(i);
  }

  public List<Island> geIslands() {
    return islandManager.getIslands();
  }

  public Tile[][] getTiles() {
    return tileManager.getTiles();
  }

  public int[][] getNeighbours(int tileX, int tileY) {
    return TileManager.getNeighbours(tileX, tileY, mapData);
  }
}
