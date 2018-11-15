package server.game.logic.map.island;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.paint.Color;
import server.game.GameContainer;
import server.game.logic.entity.building.BuildingManager;
import server.game.logic.map.tile.Tile;

public class Island {

  private GameContainer gameContainer;
  private List<Tile> tiles;
  private BuildingManager buildingManager;
  private Color color;
  private IslandStat islandStat;

  /**
   * Creates an Island with a specific color.
   *
   * @param color specific color.
   */
  public Island(Color color) {
    tiles = new ArrayList<>();
    this.color = color;
    islandStat = IslandStat.getRandomStat();
    buildingManager = new BuildingManager();
  }

  /**
   * Creates an Island.
   */
  public Island(GameContainer gameContainer) {
    this.gameContainer = gameContainer;
    this.tiles = new ArrayList<>();
    this.islandStat = IslandStat.getRandomStat();
    this.buildingManager = new BuildingManager();
  }


  public void setColor(Color color) {
    this.color = color;
  }

  public void add(Tile neighbour) {
    tiles.add(neighbour);
  }

  public boolean isIslandTile(Tile t) {
    return tiles.contains(t);
  }

  public void addAll(Island island) {
    tiles.addAll(island.getTiles());

  }

  public List<Tile> getTiles() {
    return tiles;
  }

  public Color getColor() {
    return this.color;
  }

  public float getWoodGrowth() {
    return islandStat.getWoodPercentage();
  }

  public float getStoneGrowth() {
    return islandStat.getStonePercentage();
  }

  public BuildingManager getBuildingManager() {
    return buildingManager;
  }

  public ArrayList getBuildings(Class typeClass) {
    return buildingManager.getBuildings(typeClass);
  }
}
