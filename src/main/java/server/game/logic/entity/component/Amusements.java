package server.game.logic.entity.component;

public class Amusements {

  public int brothel;
  public int church;
  public int court;
  public int alchemist;
  public int tavern;
  public int range;
  public int smith;
  public int smallTower;
  public int stable;
  public int park;


  /**
   * adds an AmusementObject to this AmusementObject.
   *
   * @param amusements amusement to add.
   */
  public void addAmusements(Amusements amusements) {
    brothel += amusements.brothel;
    church += amusements.church;
    court += amusements.court;
    alchemist += amusements.alchemist;
    tavern += amusements.tavern;
    range += amusements.range;
    stable += amusements.stable;
    park += amusements.park;
    smallTower += amusements.smallTower;
    smith += amusements.smith;
  }

}
