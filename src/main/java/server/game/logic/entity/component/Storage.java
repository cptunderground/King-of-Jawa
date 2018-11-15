package server.game.logic.entity.component;

import java.util.ArrayList;
import java.util.List;

public class Storage extends Component {

  private List<Resources> walletLimitations = new ArrayList<>();
  private List<Resources> buildingLimitations = new ArrayList<>();
  private List<Amusements> amusementLimitations = new ArrayList<>();

  /**
   * This method sets level-stats for a base.
   *
   * @param level the level-component, the stat is attached to.
   * @param currentLevel the current level of the building.
   * @param walletLimitation the inventory storage represented in resources.
   * @param buildingLimitation the building-limitations represented in resources.
   */
  public void setLevelStorage(Level level, int currentLevel, Resources walletLimitation,
      Resources buildingLimitation, Amusements amusementLimitation) {
    if (level.maxLevel <= 0 || currentLevel > level.maxLevel || currentLevel <= 0) {
      return;
    }
    currentLevel--;
    this.walletLimitations.add(currentLevel, walletLimitation);
    this.buildingLimitations.add(currentLevel, buildingLimitation);
    this.amusementLimitations.add(currentLevel, amusementLimitation);
  }

  /**
   * This method gets the building limitation based on a level.
   *
   * @param currentLevel the current level of the building.
   * @return the limitation as currencies.
   */
  public Resources getBuildingLimit(int currentLevel) {
    if (buildingLimitations.size() >= currentLevel) {
      return buildingLimitations.get(currentLevel - 1);
    }
    return getNullCurrency();
  }

  /**
   * This method gets the inventory storage based on a base.
   *
   * @param currentLevel the current level.
   * @return the currencies for inventory.
   */
  public Resources getWalletLimitations(int currentLevel) {
    if (walletLimitations.size() >= currentLevel) {
      return walletLimitations.get(currentLevel - 1);
    }
    return getNullCurrency();
  }

  /**
   * Gets the Limitation of the AmusementBuildings for the specific Level of the Base.
   *
   * @param currentLevel Level of the Base.
   * @return the Limitation of the AmusementBuilding.
   */
  public Amusements getAmusementLimitation(int currentLevel) {
    if (amusementLimitations.size() >= currentLevel) {
      return amusementLimitations.get(currentLevel - 1);
    }
    return getNullAmusement();
  }

  public Resources getNullCurrency() {
    Resources resources = new Resources();
    return resources;
  }

  public Amusements getNullAmusement() {
    Amusements amusements = new Amusements();
    return amusements;
  }


}
