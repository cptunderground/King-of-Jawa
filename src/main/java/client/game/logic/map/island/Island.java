package client.game.logic.map.island;

import client.game.logic.map.tile.Tile;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.paint.Color;


public class Island {

  private List<Tile> tiles;
  private Color color;

  /**
   * Creates an Island.
   */
  public Island() {
    tiles = new ArrayList<>();
    this.color = Color.GREY;
    // TODO: ADD REQUEST ISLANDSTAT
    // islandStat = eqasd;
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
}
