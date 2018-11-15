package client.game.logic.map;

import client.game.logic.map.island.Island;
import client.game.logic.map.tile.Tile;
import client.game.render.hud.Minimap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.image.Image;

import shared.game.map.tile.Type;
import shared.util.Chain;
import shared.util.ConsoleLog;
import shared.util.Serialization;


public class MapManager {

  private static Map map;
  private static List<String> availableMaps;
  private static Tile[][] tiles;

  /**
   * Creates a List of available maps.
   */
  public static void init() {
    availableMaps = new ArrayList<>();

    availableMaps.add("basic");
    availableMaps.add("firstMap");
  }

  /**
   * Returns a specific map with its name.
   *
   * @param name Name of the Map.
   * @return Map that was chosen.
   */
  public static Map loadMap(String name) {
    InputStream stream =
        MapManager.class.getResourceAsStream("/map/" + name + ".koj");
    BufferedReader bufferedReader =
        new BufferedReader(new InputStreamReader(stream, Charset.forName("UTF-8")));
    String header = "";
    try {
      String line;
      if ((line = bufferedReader.readLine()) != null) {
        header = line;

      }

      Serialization serialization = new Serialization();
      serialization.unpack(header);

      String author = ((Chain) serialization.find("head")
          .getValue()).find("author").getValue().toString();
      String stringSize = ((Chain) serialization.find("head")
          .getValue()).find("size").getValue().toString();
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
      MapManager.map = new Map(author, mapData, size);
      return MapManager.map;
    } catch (IOException e) {
      ConsoleLog.debug(e.getMessage());
    }
    return null;
  }

  /**
   * Returns an image with the map as a preview.
   *
   * @param name Name of the map.
   * @return Image of the specific map.
   */
  public static Image loadMapPreview(String name) {
    InputStream stream =
        MapManager.class.getResourceAsStream("/map/" + name + ".koj");
    BufferedReader bufferedReader =
        new BufferedReader(new InputStreamReader(stream, Charset.forName("UTF-8")));
    String header = "";
    try {
      String line;
      if ((line = bufferedReader.readLine()) != null) {
        header = line;

      }

      Serialization serialization = new Serialization();
      serialization.unpack(header);

      String author = ((Chain) serialization.find("head")
          .getValue()).find("author").getValue().toString();
      String stringSize = ((Chain) serialization.find("head")
          .getValue()).find("size").getValue().toString();
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

      Minimap minimap = new Minimap(200, mapData);
      return minimap.getMapImage();
    } catch (IOException e) {
      ConsoleLog.debug(e.getMessage());
    }
    return null;
  }

  private static void add(String name) {
    availableMaps.add(name);
  }

  public static List<String> getAvailableMaps() {
    return availableMaps;
  }


  public static Map getLoadedMap() {
    return map;
  }

  public static List<Island> getIslands() {
    return map.geIslands();
  }

  public static Tile[][] getTiles() {
    return map.getTiles();
  }





}
