package server.game.logic.map;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import server.game.GameContainer;
import server.game.logic.map.island.Island;
import server.game.logic.map.tile.Tile;
import server.game.logic.map.tile.TileManager;
import shared.util.Chain;
import shared.util.ConsoleLog;
import shared.util.Serialization;

public class MapManager {

  private Map map;
  private GameContainer gameContainer;

  public MapManager(GameContainer gameContainer, String mapName) {
    this.gameContainer = gameContainer;
    loadMap(mapName);
  }

  /**
   * Loads a Map with a specific name.
   *
   * @param name specific name of the map.
   */
  public void loadMap(String name) {

    InputStream stream = getClass().getResourceAsStream("/map/" + name + ".koj");
    BufferedReader bufferedReader = new BufferedReader(
        new InputStreamReader(stream, Charset.forName("UTF-8")));
    String header = "";
    try {
      String line;
      if ((line = bufferedReader.readLine()) != null) {
        header = line;
      }

      Serialization serialization = new Serialization();
      serialization.unpack(header);

      String author = ((Chain) serialization.find("head").getValue()).find("author").getValue()
          .toString();
      String stringSize = ((Chain) serialization.find("head").getValue()).find("size").getValue()
          .toString();
      int size = Integer.parseInt(stringSize);

      int[][] mapData = new int[size][size];
      int j = 0;

      while ((line = bufferedReader.readLine()) != null) {
        String[] tempRow = line.split("\\s+");
        for (int i = 0; i < tempRow.length; i++) {
          mapData[i][j] = Integer.parseInt(tempRow[i]);
        }
        j++;
      }
      this.map = new Map(gameContainer, author, mapData, size);
    } catch (IOException e) {
      ConsoleLog.debug(e.getMessage());
    }
  }

  /**
   * Generates a preset of a map with a specific size.
   *
   * @param size specific size.
   */
  public static void generatePreset(int size) {
    try {
      String path = System.getProperty("user.dir");
      PrintWriter writer = new PrintWriter(
          path + "\\src\\main\\resources\\map\\preset" + size + ".koj", "UTF-8");
      writer.println("'head'|{'author'|'SERVER','size'|'" + size + "'}");
      for (int i = 0; i < size; i++) {
        StringBuilder s = new StringBuilder(0 + "");
        for (int j = 1; j < size; j++) {
          s.append(" " + 0);
        }
        writer.println(s);
      }
      writer.close();
    } catch (UnsupportedEncodingException | FileNotFoundException e) {
      ConsoleLog.debug(e.getMessage());
    }
  }

  public Map getMap() {
    return map;
  }

  public Tile getTile(int posX, int posY) {
    return map.getTile(posX, posY);
  }

  public Island getIsland(int i) {
    return map.getIsland(i);
  }

  public Island getIslandByTile(Tile tile) {
    return map.getIslandByTile(tile);
  }

  public TileManager getTileManager() {
    return map.getTileManager();
  }

  public int getIslandId(Island island) {
    return map.getIslandManager().getIslands().indexOf(island);
  }
}
