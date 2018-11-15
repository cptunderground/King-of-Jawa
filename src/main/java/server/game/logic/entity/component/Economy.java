package server.game.logic.entity.component;

import java.util.ArrayList;
import java.util.List;

public class Economy extends Component {

  private List<Resources> resourceIpm = new ArrayList<>();
  private List<Resources> resourcePpm = new ArrayList<>();
  private List<Resources> upgradePrice = new ArrayList<>();

  /**
   * This method sets level-stats.
   *
   * @param level the level, the stat is attached to.
   * @param currentLevel the current level.
   * @param incPerMin the income per minute the building has.
   * @param pricePerMin the running costs the building has.
   * @param upgradePrice the price to upgrade the base represented in currencies.
   */
  public void setLevelEconomy(Level level, int currentLevel, Resources incPerMin,
      Resources pricePerMin,
      Resources upgradePrice) {

    if (level.maxLevel <= 0 || currentLevel > level.maxLevel || currentLevel <= 0) {
      return;
    }
    currentLevel--;
    resourceIpm.add(currentLevel, incPerMin);
    resourcePpm.add(currentLevel, pricePerMin);
    this.upgradePrice.add(currentLevel, upgradePrice);
  }

  /**
   * This method gets the income per minute.
   *
   * @param currentLevel the current level.
   * @return the income per minute represented in currencies.
   */
  public Resources getIncomePerMinute(int currentLevel) {
    if (resourceIpm.size() >= currentLevel) {
      if (resourceIpm.get(currentLevel - 1) == null) {
        return getNullCurrency();
      }
      return resourceIpm.get(currentLevel - 1);
    }
    return getNullCurrency();
  }

  /**
   * This method gets the price per minute.
   *
   * @param currentLevel the level-component.
   * @return the price per minute represented in currencies.
   */
  public Resources getPricePerMinute(int currentLevel) {
    if (resourcePpm.size() >= currentLevel) {
      if (resourcePpm.get(currentLevel - 1) == null) {
        return getNullCurrency();
      }
      return resourcePpm.get(currentLevel - 1);
    }
    return getNullCurrency();
  }

  /**
   * This method gets the upgrade-price.
   *
   * @param currentLevel the level-component.
   * @return the upgrade price represented in currencies.
   */
  public Resources getUpgradePrice(int currentLevel) {
    if (upgradePrice.size() >= currentLevel) {
      if (upgradePrice.get(currentLevel - 1) == null) {
        return getNullCurrency();
      }
      return upgradePrice.get(currentLevel - 1);
    }
    return getNullCurrency();
  }

  public Resources getNullCurrency() {
    Resources resources = new Resources();
    return resources;
  }


}
