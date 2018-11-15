package server.game.logic.map;

import server.game.GameContainer;
import server.game.logic.map.island.Island;
import server.game.logic.map.island.IslandManager;
import server.game.logic.map.tile.Tile;
import server.game.logic.map.tile.TileManager;
import shared.util.ConsoleLog;

public class Map extends shared.game.map.Map {

  private GameContainer gameContainer;
  private TileManager tileManager;
  private IslandManager islandManager;
  private String author;
  private int size;

  /**
   * Creates a Map with specific author, size and mapData.
   *
   * @param author specific author.
   * @param mapData specific mapData.
   * @param size specific size.
   */
  public Map(GameContainer gameContainer, String author, int[][] mapData, int size) {
    if (mapData.length != mapData[0].length) {
      ConsoleLog.warning("Invalid map!");
    }
    this.author = author;
    this.size = size;
    this.gameContainer = gameContainer;
    this.tileManager = new TileManager(gameContainer, size, mapData);
    this.islandManager = new IslandManager(gameContainer, tileManager.getTiles());
  }

  @Override
  public int getSize() {
    return size;
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

  public Island getIslandByTile(Tile tile) {
    return islandManager.getIslandByTile(tile);
  }

  public TileManager getTileManager() {
    return tileManager;
  }

  public IslandManager getIslandManager() {
    return islandManager;
  }
}
