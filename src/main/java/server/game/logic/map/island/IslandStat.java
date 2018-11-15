package server.game.logic.map.island;

import java.util.Random;

public class IslandStat {
  private float woodPercentage;
  private float stonePercentage;

  public IslandStat(float woodPercentage, float stonePercentage) {
    this.woodPercentage = woodPercentage;
    this.stonePercentage = stonePercentage;
  }

  public float getStonePercentage() {
    return stonePercentage;
  }

  public void setStonePercentage(float stonePercentage) {
    this.stonePercentage = stonePercentage;
  }

  public float getWoodPercentage() {
    return woodPercentage;
  }

  public void setWoodPercentage(float woodPercentage) {
    this.woodPercentage = woodPercentage;
  }


  /**
   * Returns a random IslandStat.
   *
   * @return random IslandStat.
   */
  public static IslandStat getRandomStat() {
    Random rand = new Random();
    float percentage1 = rand.nextFloat();
    float percentage2 = rand.nextFloat();
    return new IslandStat(percentage1, percentage2);
  }
}
