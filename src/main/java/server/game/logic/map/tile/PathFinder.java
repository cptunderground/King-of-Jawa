package server.game.logic.map.tile;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class PathFinder {

  private static int DEFAULT_HV_COST = 10; // Horizontal - Vertical Cost
  private static int DEFAULT_DIAGONAL_COST = 100;
  private int hvCost;
  private int diagonalCost;
  private Tile[][] searchArea;
  private PriorityQueue<Tile> openList;
  private List<Tile> closedList;
  private Tile initialTile;
  private Tile finalTile;

  /**
   * Creates a PathFinder Object.
   *
   * @param tiles Tiles for the PathFinder.
   * @param hvCost horizontal and vertical costs.
   * @param diagonalCost diagonal costs.
   */
  public PathFinder(Tile[][] tiles, int hvCost,
      int diagonalCost) {
    this.hvCost = hvCost;
    this.diagonalCost = diagonalCost;
    this.searchArea = tiles;
    this.openList = new PriorityQueue<Tile>(new Comparator<Tile>() {
      @Override
      public int compare(Tile node0, Tile node1) {
        return node0.getFe() < node1.getFe() ? -1 : node0.getFe() > node1.getFe() ? 1 : 0;
      }
    });
    this.closedList = new ArrayList<Tile>();
  }

  public PathFinder(Tile[][] tiles) {
    this(tiles, DEFAULT_HV_COST, DEFAULT_DIAGONAL_COST);
  }

  /**
   * This method finds the specific path.
   *
   * @param start startTile.
   * @param end endTile.
   * @return a List of Tiles for the specific Path.
   */
  public List<Tile> findPath(Tile start, Tile end) {
    setInitialTile(start);
    setFinalTile(end);
    openList.add(initialTile);
    while (!isEmpty(openList)) {
      Tile currentTile = openList.poll();
      closedList.add(currentTile);
      if (isFinalNode(currentTile)) {
        return getPath(currentTile);
      } else {
        addAdjacentTiles(currentTile);
      }
    }
    return new ArrayList<Tile>();
  }

  private List<Tile> getPath(Tile currentNode) {
    List<Tile> path = new ArrayList<Tile>();
    path.add(currentNode);
    Tile parent;
    while ((parent = currentNode.getParent()) != null) {
      path.add(0, parent);
      currentNode = parent;
    }
    return path;
  }

  private void addAdjacentTiles(Tile currentTile) {
    addAdjacentUpperRow(currentTile);
    addAdjacentMiddleRow(currentTile);
    addAdjacentLowerRow(currentTile);
  }

  private void addAdjacentLowerRow(Tile currentTile) {
    int row = currentTile.getX();
    int col = currentTile.getY();
    int lowerRow = row + 1;
    if (lowerRow < getSearchArea().length) {
      if (col - 1 >= 0) {
        checkTile(currentTile, col - 1, lowerRow,
            getDiagonalCost()); // Comment this line if diagonal movements are not allowed
      }
      if (col + 1 < getSearchArea()[0].length) {
        checkTile(currentTile, col + 1, lowerRow,
            getDiagonalCost()); // Comment this line if diagonal movements are not allowed
      }
      checkTile(currentTile, col, lowerRow, getHvCost());
    }
  }

  private void addAdjacentMiddleRow(Tile currentTile) {
    int row = currentTile.getX();
    int col = currentTile.getY();
    int middleRow = row;
    if (col - 1 >= 0) {
      checkTile(currentTile, col - 1, middleRow, getHvCost());
    }
    if (col + 1 < getSearchArea()[0].length) {
      checkTile(currentTile, col + 1, middleRow, getHvCost());
    }
  }

  private void addAdjacentUpperRow(Tile currentTile) {
    int row = currentTile.getX();
    int col = currentTile.getY();
    int upperRow = row - 1;
    if (upperRow >= 0) {
      if (col - 1 >= 0) {
        checkTile(currentTile, col - 1, upperRow,
            getDiagonalCost()); // Comment this if diagonal movements are not allowed
      }
      if (col + 1 < getSearchArea()[0].length) {
        checkTile(currentTile, col + 1, upperRow,
            getDiagonalCost()); // Comment this if diagonal movements are not allowed
      }
      checkTile(currentTile, col, upperRow, getHvCost());
    }
  }

  private void checkTile(Tile currentTile, int col, int row, int cost) {
    Tile adjacentNode = getSearchArea()[row][col];
    if (!adjacentNode.isBlock() && !getClosedList().contains(adjacentNode)) {
      if (!getOpenList().contains(adjacentNode)) {
        adjacentNode.setTileData(currentTile, cost);
        getOpenList().add(adjacentNode);
      } else {
        boolean changed = adjacentNode.checkBetterPath(currentTile, cost);
        if (changed) {
          // Remove and Add the changed node, so that the PriorityQueue can sort again its
          // contents with the modified "finalCost" value of the modified node
          getOpenList().remove(adjacentNode);
          getOpenList().add(adjacentNode);
        }
      }
    }
  }

  private boolean isEmpty(PriorityQueue<Tile> openList) {
    return openList.size() == 0;
  }

  private boolean isFinalNode(Tile currentTile) {
    return currentTile.equals(finalTile);
  }

  public void setInitialTile(Tile initialTile) {
    this.initialTile = initialTile;
  }

  public void setFinalTile(Tile finalTile) {
    this.finalTile = finalTile;
  }

  public int getHvCost() {
    return hvCost;
  }

  public int getDiagonalCost() {
    return diagonalCost;
  }

  public Tile[][] getSearchArea() {
    return searchArea;
  }

  public void setSearchArea(Tile[][] searchArea) {
    this.searchArea = searchArea;
  }

  public PriorityQueue<Tile> getOpenList() {
    return openList;
  }

  public List<Tile> getClosedList() {
    return closedList;
  }

}
