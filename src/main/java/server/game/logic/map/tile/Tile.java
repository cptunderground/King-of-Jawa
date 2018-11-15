package server.game.logic.map.tile;

import server.game.logic.entity.building.Building;
import shared.game.map.tile.Type;
import shared.util.Point;

public class Tile implements shared.game.map.tile.Tile {

  private Point point;
  private Type tileType;
  private Building tileBuilding;

  private int ge;
  private int fe;
  private int he;

  private boolean isBlock;
  private Tile parent;

  /**
   * Creates a TileObject.
   *
   * @param x xPosition.
   * @param y yPosition.
   * @param type specific Type od the Tile.
   */
  public Tile(int x, int y, Type type) {
    this.point = new Point(x, y);
    this.tileType = type;
    isBlock = type != Type.WATER;
  }

  public void calculateHeuristic(Tile finalTile) {
    this.he = Math.abs(finalTile.getX() - getX()) + Math.abs(finalTile.getY() - getY());
  }

  /**
   * Sets the TileData.
   *
   * @param currentTile the current Tile.
   * @param cost the costs.
   */
  public void setTileData(Tile currentTile, int cost) {
    int geCost = currentTile.getGe() + cost;
    setParent(currentTile);
    setGe(geCost);
    calculateFinalCost();
  }

  /**
   * checks for a better Path.
   *
   * @param currentTile the current Tile.
   * @param cost the costs.
   * @return returns whether a better Path exists or not.
   */
  public boolean checkBetterPath(Tile currentTile, int cost) {
    int geCost = currentTile.getGe() + cost;
    if (geCost < getGe()) {
      setTileData(currentTile, cost);
      return true;
    }
    return false;
  }

  private void calculateFinalCost() {
    int finalCost = getGe() + getHe();
    setFe(finalCost);
  }

  @Override
  public boolean equals(Object arg0) {
    Tile other = (Tile) arg0;
    return this.getX() == other.getX() && this.getY() == other.getY();
  }

  public int getHe() {
    return he;
  }

  public int getGe() {
    return ge;
  }

  public void setGe(int ge) {
    this.ge = ge;
  }

  public int getFe() {
    return fe;
  }

  public void setFe(int fe) {
    this.fe = fe;
  }

  public Tile getParent() {
    return parent;
  }

  public void setParent(Tile parent) {
    this.parent = parent;
  }

  public boolean isBlock() {
    return isBlock;
  }

  public void setBlock(boolean isBlock) {
    this.isBlock = isBlock;
  }

  public int getX() {
    return point.getX();
  }

  public int getY() {
    return point.getY();
  }

  public Type getType() {
    return tileType;
  }

  public boolean isOccupied() {
    return (tileBuilding != null);
  }

  public void setTileBuilding(Building building) {
    tileBuilding = building;
  }

  @Override
  public String toString() {
    return "Node [row=" + point.getX() + ", col=" + point.getY() + "]";
  }

}
