package shared.game.map.tile;

public interface Tile {

  int getX();

  int getY();

  Type getType();

  boolean isOccupied();

}
